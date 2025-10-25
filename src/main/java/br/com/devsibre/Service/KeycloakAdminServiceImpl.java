package br.com.devsibre.Service;

import br.com.devsibre.Domain.Entity.DTO.KeycloakUserDTO;
import br.com.devsibre.error.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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

        // Verifica se username já existe
        List<Map<String, Object>> usuarios = listarUsuarios(); // já usa o mesmo token

        boolean usernameJaExiste = usuarios.stream()
                .anyMatch(u -> u.get("username").equals(user.getUsername()));

        if (usernameJaExiste) {
            throw new RuntimeException(BusinessException.usuarioDuplicado(user.getUsername()));
        }

        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            throw new IllegalArgumentException("Pelo menos um papel deve ser informado para o usuário.");
        }

        // Define corpo do usuário conforme API do Keycloak
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
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            return;
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.CONFLICT) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe um usuário com este e-mail ou nome de usuário.");
            }

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Erro ao criar usuário: " + e.getResponseBodyAsString());
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
            // 1️⃣ Busca todos os usuários
            ResponseEntity<List> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    request,
                    List.class
            );

            List<Map<String, Object>> usuarios = response.getBody();

            return usuarios;

        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Falha ao listar usuários: " + e.getResponseBodyAsString(), e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro inesperado ao listar usuários", e);
        }
    }

    public List<KeycloakUserDTO> listarUsuariosComRoles() {
        String token = obterTokenAdmin();
        String urlUsuarios = keycloakUrl + "/admin/realms/" + realm + "/users";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        try {
            // 1️⃣ Busca todos os usuários
            ResponseEntity<List> response = restTemplate.exchange(
                    urlUsuarios,
                    HttpMethod.GET,
                    request,
                    List.class
            );

            List<Map<String, Object>> usuariosMap = response.getBody();
            System.out.println("📋 Total de usuários encontrados: " + (usuariosMap != null ? usuariosMap.size() : 0));

            // 2️⃣ Converte e adiciona roles para cada usuário
            List<KeycloakUserDTO> usuarios = usuariosMap.stream().map(userMap -> {
                KeycloakUserDTO dto = new KeycloakUserDTO();
                dto.setId((String) userMap.get("id"));
                dto.setUsername((String) userMap.get("username"));
                dto.setEmail((String) userMap.get("email"));
                dto.setFirstName((String) userMap.get("firstName"));
                dto.setLastName((String) userMap.get("lastName"));
                dto.setEnabled((Boolean) userMap.getOrDefault("enabled", true));

                // 3️⃣ Busca as roles do usuário
                try {
                    String urlRoles = keycloakUrl + "/admin/realms/" + realm + "/users/" + dto.getId() + "/role-mappings/realm";
                    ResponseEntity<List> rolesResponse = restTemplate.exchange(
                            urlRoles,
                            HttpMethod.GET,
                            request,
                            List.class
                    );

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

            return usuarios;

        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Falha ao listar usuários: " + e.getResponseBodyAsString(), e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro inesperado ao listar usuários", e);
        }
    }

    public void atualizarUsuario(String id, KeycloakUserDTO user) {
        String token = obterTokenAdmin();
        String url = keycloakUrl + "/admin/realms/" + realm + "/users/" + id;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Corpo com dados atualizados - O username no Keycloak é único e imutável por padrão.
        Map<String, Object> userBody = Map.of(
                "email", user.getEmail(),
                "firstName", user.getFirstName(),
                "lastName", user.getLastName(),
                "enabled", user.isEnabled()
        );

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(userBody, headers);

        try {
            restTemplate.exchange(url, HttpMethod.PUT, request, String.class);

            // Se vier senha, atualiza também
            if (user.getPassword() != null && !user.getPassword().isBlank()) {
                atualizarSenha(id, user.getPassword(), token);
            }

        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Falha ao atualizar usuário: " + e.getResponseBodyAsString(), e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro inesperado ao atualizar usuário", e);
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
            System.out.println("🔑 Senha do usuário atualizada com sucesso!");
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Falha ao atualizar senha: " + e.getResponseBodyAsString(), e);
        }
    }

    public void excluirUsuario(String id) {
        String token = obterTokenAdmin();
        String urlUsuario = keycloakUrl + "/admin/realms/" + realm + "/users/" + id;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<Void> request = new HttpEntity<>(headers);

        try {
            // Busca o usuário antes de excluir
            ResponseEntity<Map> userResponse = restTemplate.exchange(urlUsuario, HttpMethod.GET, request, Map.class);
            Map<String, Object> usuario = userResponse.getBody();
            String username = (String) usuario.get("username");

            if ("sibre-admin".equalsIgnoreCase(username)) {
                throw new RuntimeException("❌ Não é permitido excluir o usuário administrativo principal (sibre-admin).");
            }

            // Busca roles do usuário
            String urlRoles = keycloakUrl + "/admin/realms/" + realm + "/users/" + id + "/role-mappings/realm";
            ResponseEntity<List> rolesResponse = restTemplate.exchange(urlRoles, HttpMethod.GET, request, List.class);
            List<Map<String, Object>> roles = rolesResponse.getBody();

            if (roles != null && roles.stream().anyMatch(r -> "admin".equalsIgnoreCase((String) r.get("name")))) {
                // Verifica se é o único admin do sistema
                long qtdAdmins = listarUsuariosComRoles().stream()
                        .filter(u -> u.getRoles().contains("admin"))
                        .count();

                if (qtdAdmins <= 1) {
                    throw new RuntimeException("⚠️ Não é possível excluir o único administrador do sistema.");
                }
            }

            // Se passou por todas as verificações, pode excluir
            restTemplate.exchange(urlUsuario, HttpMethod.DELETE, request, String.class);
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Falha ao excluir usuário: " + e.getResponseBodyAsString(), e);
        }
    }

    private String obterTokenAdmin() {
        // token vem do realm sibre
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
                throw new RuntimeException("❌ Token administrativo retornou nulo. Verifique as credenciais do client no Keycloak.");
            }
            return token;
        } catch (HttpClientErrorException e) {
            throw e;
        }
    }
}
