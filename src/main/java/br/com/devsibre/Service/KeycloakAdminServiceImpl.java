package br.com.devsibre.Service;

import br.com.devsibre.Domain.Entity.DTO.KeycloakUserDTO;
import br.com.devsibre.error.BusinessException;
import br.com.devsibre.util.Mensagens;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class KeycloakAdminServiceImpl {

    @Value("${keycloak.url}")
    private String keycloakUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.client-id}")
    private String clientId;

    @Value("${keycloak.client-secret}")
    private String clientSecret;

    private final RestTemplate restTemplate = new RestTemplate();

    public void criarUsuario(KeycloakUserDTO user) {
        String token = obterTokenAdmin();

        List<Map<String, Object>> usuarios = listarUsuarios();
        boolean usernameJaExiste = usuarios.stream()
                .anyMatch(u -> u.get("username").equals(user.getUsername()));
        if (usernameJaExiste) {
            throw new BusinessException(Mensagens.Usuario.USUARIO_DUPLICADO);
        }

        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            throw new IllegalArgumentException(Mensagens.Usuario.SEM_ROLE);
        }

        Map<String, Object> userBody = Map.of(
                "username", user.getUsername(),
                "email", user.getEmail(),
                "firstName", user.getFirstName(),
                "lastName", user.getLastName(),
                "enabled", true,
                "credentials", new Object[]{
                        Map.of("type", "password", "value", user.getPassword(), "temporary", false)
                }
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(userBody, headers);
        String url = keycloakUrl + "/admin/realms/" + realm + "/users";

        try {
            restTemplate.postForEntity(url, request, String.class);
        } catch (HttpClientErrorException e) {
            System.err.println("❌ Erro ao criar usuário no Keycloak");
            System.err.println("Status: " + e.getStatusCode());
            System.err.println("Headers: " + e.getResponseHeaders());
            System.err.println("Body: " + e.getResponseBodyAsString());
            if (e.getStatusCode() == HttpStatus.CONFLICT) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, Mensagens.Usuario.EMAIL_DUPLICADO);
            }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Mensagens.Usuario.FALHA_CRIAR + ": " + e.getResponseBodyAsString());
        }
    }

    public List<Map<String, Object>> listarUsuarios() {
        String token = obterTokenAdmin();
        String url = keycloakUrl + "/admin/realms/" + realm + "/users";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Void> request = new HttpEntity<>(headers);

        try {
            ResponseEntity<List> response = restTemplate.exchange(url, HttpMethod.GET, request, List.class);
            return response.getBody();
        } catch (HttpClientErrorException e) {
            throw new RuntimeException(Mensagens.Usuario.FALHA_LISTAR + ": " + e.getResponseBodyAsString(), e);
        } catch (Exception e) {
            throw new RuntimeException(Mensagens.Sistema.ERRO_INESPERADO, e);
        }
    }

    public List<KeycloakUserDTO> listarUsuariosComRoles() {
        String token = obterTokenAdmin();
        List<Map<String, Object>> usuariosMap = listarUsuarios();

        if (usuariosMap == null || usuariosMap.isEmpty()) {
            return List.of();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Void> request = new HttpEntity<>(headers);

        return usuariosMap.stream().map(userMap -> {
            KeycloakUserDTO dto = new KeycloakUserDTO();
            dto.setId((String) userMap.get("id"));
            dto.setUsername((String) userMap.get("username"));
            dto.setEmail((String) userMap.get("email"));
            dto.setFirstName((String) userMap.get("firstName"));
            dto.setLastName((String) userMap.get("lastName"));
            dto.setEnabled((Boolean) userMap.getOrDefault("enabled", true));

            try {
                String urlRoles = keycloakUrl + "/admin/realms/" + realm + "/users/" + dto.getId() + "/role-mappings/realm";
                ResponseEntity<List> rolesResponse = restTemplate.exchange(urlRoles, HttpMethod.GET, request, List.class);

                List<Map<String, Object>> rolesMap = rolesResponse.getBody();
                if (rolesMap != null) {
                    List<String> nomesRoles = rolesMap.stream()
                            .map(r -> (String) r.get("name"))
                            .toList();
                    dto.setRoles(nomesRoles);
                }
            } catch (Exception e) {
                System.err.println("⚠️ Erro ao buscar roles do usuário " + dto.getUsername() + ": " + e.getMessage());
            }

            return dto;
        }).toList();
    }

    public void atualizarUsuario(String id, KeycloakUserDTO user) {
        String token = obterTokenAdmin();
        String url = keycloakUrl + "/admin/realms/" + realm + "/users/" + id;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> userBody = Map.of(
                "email", user.getEmail(),
                "firstName", user.getFirstName(),
                "lastName", user.getLastName(),
                "enabled", user.isEnabled()
        );

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(userBody, headers);

        try {
            restTemplate.exchange(url, HttpMethod.PUT, request, String.class);
            if (user.getPassword() != null && !user.getPassword().isBlank()) {
                atualizarSenha(id, user.getPassword(), token);
            }
        } catch (HttpClientErrorException e) {
            throw new RuntimeException(Mensagens.Usuario.FALHA_ATUALIZAR + ": " + e.getResponseBodyAsString(), e);
        } catch (Exception e) {
            throw new RuntimeException(Mensagens.Sistema.ERRO_INESPERADO, e);
        }
    }

    private void atualizarSenha(String id, String novaSenha, String token) {
        String url = keycloakUrl + "/admin/realms/" + realm + "/users/" + id + "/reset-password";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> passwordBody = Map.of(
                "type", "password",
                "value", novaSenha,
                "temporary", false
        );

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(passwordBody, headers);

        try {
            restTemplate.put(url, request);
            System.out.println(Mensagens.Usuario.SENHA_ATUALIZADA);
        } catch (HttpClientErrorException e) {
            throw new RuntimeException(Mensagens.Usuario.FALHA_SENHA + ": " + e.getResponseBodyAsString(), e);
        }
    }

    public void excluirUsuario(String id) {
        String token = obterTokenAdmin();
        String urlUsuario = keycloakUrl + "/admin/realms/" + realm + "/users/" + id;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<Void> request = new HttpEntity<>(headers);

        try {
            ResponseEntity<Map> userResponse = restTemplate.exchange(urlUsuario, HttpMethod.GET, request, Map.class);
            Map<String, Object> usuario = userResponse.getBody();
            String username = (String) usuario.get("username");

            if ("sibre-admin".equalsIgnoreCase(username)) {
                throw new RuntimeException(Mensagens.Usuario.ADMIN_IMEXCLUIVEL);
            }

            String urlRoles = keycloakUrl + "/admin/realms/" + realm + "/users/" + id + "/role-mappings/realm";
            ResponseEntity<List> rolesResponse = restTemplate.exchange(urlRoles, HttpMethod.GET, request, List.class);
            List<Map<String, Object>> roles = rolesResponse.getBody();

            if (roles != null && roles.stream().anyMatch(r -> "admin".equalsIgnoreCase((String) r.get("name")))) {
                long qtdAdmins = listarUsuariosComRoles().stream()
                        .filter(u -> u.getRoles().contains("admin"))
                        .count();

                if (qtdAdmins <= 1) {
                    throw new RuntimeException(Mensagens.Usuario.UNICO_ADMIN);
                }
            }

            restTemplate.exchange(urlUsuario, HttpMethod.DELETE, request, String.class);
        } catch (HttpClientErrorException e) {
            throw new RuntimeException(Mensagens.Usuario.FALHA_EXCLUIR + ": " + e.getResponseBodyAsString(), e);
        }
    }

    private String obterTokenAdmin() {
        String url = keycloakUrl + "/realms/" + realm + "/protocol/openid-connect/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("grant_type", "client_credentials");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);
            String token = (String) response.getBody().get("access_token");

            if (token == null) {
                throw new RuntimeException(Mensagens.Token.NULO);
            }
            return token;
        } catch (HttpClientErrorException e) {
            throw e;
        }
    }
}

