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
        if (user == null
                || user.getUsername() == null || user.getUsername().isBlank()
                || user.getPassword() == null || user.getPassword().isBlank()
                || user.getEmail() == null || user.getEmail().isBlank()
                || user.getFirstName() == null || user.getFirstName().isBlank()
                || user.getLastName() == null || user.getLastName().isBlank()
                || user.getRoles() == null || user.getRoles().isEmpty()) {
            throw new BusinessException(Mensagens.Usuario.CAMPO_VAZIO);
        }

        String token = obterTokenAdmin();

        // 🧩 Cria o usuário no Keycloak
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
            if (e.getStatusCode() == HttpStatus.CONFLICT) {
                throw new BusinessException(Mensagens.Usuario.USUARIO_DUPLICADO);
            }
            throw new BusinessException(Mensagens.Usuario.FALHA_CRIAR + ": " + e.getResponseBodyAsString());
        }

        // Busca o usuário recém-criado pelo username
        String userId = buscarUserIdPorUsername(user.getUsername(), token);
        if (userId == null) {
            throw new BusinessException("Erro ao obter ID do usuário criado no Keycloak.");
        }

        // 3️⃣ Define a senha (reset-password)
        definirSenhaUsuario(userId, user.getPassword(), token);

        // Busca a role diretamente da lista de roles do realm (sem endpoint restrito)
        String roleName = user.getRoles().get(0); // Ex: "gestor"
        Map<String, Object> role = buscarRoleCompleta(roleName, token);
        if (role == null) {
            throw new BusinessException("Role '" + roleName + "' não encontrada no Keycloak.");
        }

        // Atribui a role ao usuário
        atribuirRoleAoUsuario(userId, role, token);
    }

    private Map<String, Object> buscarRoleCompleta(String roleName, String token) {
        String url = keycloakUrl + "/admin/realms/" + realm + "/roles"; // lista todas as roles do realm
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        try {
            ResponseEntity<List> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), List.class);
            if (response.getBody() != null) {
                for (Object item : response.getBody()) {
                    Map<String, Object> role = (Map<String, Object>) item;
                    if (roleName.equalsIgnoreCase((String) role.get("name"))) {
                        return role; // retorna o objeto completo da role
                    }
                }
            }
        } catch (HttpClientErrorException e) {
            System.err.println("⚠️ Erro ao listar roles do realm: " + e.getMessage());
        }

        return null;
    }

    // 🔎 Busca o ID do usuário pelo username
    private String buscarUserIdPorUsername(String username, String token) {
        String url = keycloakUrl + "/admin/realms/" + realm + "/users?username=" + username;
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        ResponseEntity<List> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), List.class);
        if (response.getBody() != null && !response.getBody().isEmpty()) {
            Map<String, Object> user = (Map<String, Object>) response.getBody().get(0);
            return (String) user.get("id");
        }
        return null;
    }

    private void atribuirRoleAoUsuario(String userId, Map<String, Object> role, String token) {
        String url = keycloakUrl + "/admin/realms/" + realm + "/users/" + userId + "/role-mappings/realm";
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // O Keycloak aceita "name" e (opcionalmente) "id"
        HttpEntity<List<Map<String, Object>>> request = new HttpEntity<>(List.of(role), headers);

        try {
            restTemplate.postForEntity(url, request, String.class);
        } catch (HttpClientErrorException e) {
            System.err.println("⚠️ Erro ao atribuir role '" + role.get("name") + "' ao usuário ID " + userId);
            System.err.println("➡️ Resposta: " + e.getResponseBodyAsString());
            throw new BusinessException("Erro ao atribuir role '" + role.get("name") + "' ao usuário.");
        }
    }

    private void definirSenhaUsuario(String userId, String senha, String token) {
        String url = keycloakUrl + "/admin/realms/" + realm + "/users/" + userId + "/reset-password";

        Map<String, Object> credenciais = Map.of(
                "type", "password",
                "value", senha,
                "temporary", false
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(credenciais, headers);

        try {
            restTemplate.put(url, request);
        } catch (HttpClientErrorException e) {
            System.err.println("⚠️ Erro ao definir senha para o usuário ID " + userId);
            System.err.println("➡️ Resposta: " + e.getResponseBodyAsString());
            throw new BusinessException("Erro ao definir senha para o usuário.");
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

