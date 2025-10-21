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
            System.out.println("✅ Usuário criado com sucesso no Keycloak! Status: " + response.getStatusCode());
        } catch (HttpClientErrorException e) {
            System.err.println("❌ Erro ao criar usuário no Keycloak");
            System.err.println("Status: " + e.getStatusCode());
            System.err.println("Headers: " + e.getResponseHeaders());
            System.err.println("Body: " + e.getResponseBodyAsString());
            throw new RuntimeException("Falha ao criar usuário: " + e.getResponseBodyAsString(), e);
        } catch (Exception e) {
            System.err.println("⚠️ Erro inesperado ao criar usuário: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erro inesperado ao criar usuário", e);
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
            ResponseEntity<List> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    request,
                    List.class
            );

            List<Map<String, Object>> usuarios = response.getBody();
            System.out.println("📋 Total de usuários encontrados: " + (usuarios != null ? usuarios.size() : 0));

            return usuarios;

        } catch (HttpClientErrorException e) {
            System.err.println("❌ Erro ao listar usuários no Keycloak:");
            System.err.println("Status: " + e.getStatusCode());
            System.err.println("Body: " + e.getResponseBodyAsString());
            throw new RuntimeException("Falha ao listar usuários: " + e.getResponseBodyAsString(), e);
        } catch (Exception e) {
            System.err.println("⚠️ Erro inesperado ao listar usuários: " + e.getMessage());
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
            System.out.println("✅ Usuário atualizado com sucesso no Keycloak! ID: " + id);

            // Se vier senha, atualiza também
            if (user.getPassword() != null && !user.getPassword().isBlank()) {
                atualizarSenha(id, user.getPassword(), token);
            }

        } catch (HttpClientErrorException e) {
            System.err.println("❌ Erro ao atualizar usuário no Keycloak:");
            System.err.println("Status: " + e.getStatusCode());
            System.err.println("Headers: " + e.getResponseHeaders());
            System.err.println("Body: " + e.getResponseBodyAsString());
            throw new RuntimeException("Falha ao atualizar usuário: " + e.getResponseBodyAsString(), e);
        } catch (Exception e) {
            System.err.println("⚠️ Erro inesperado ao atualizar usuário: " + e.getMessage());
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
            System.err.println("❌ Erro ao atualizar senha no Keycloak: " + e.getResponseBodyAsString());
            throw new RuntimeException("Falha ao atualizar senha: " + e.getResponseBodyAsString(), e);
        }
    }

    public void excluirUsuario(String id) {
        String token = obterTokenAdmin();
        String url = keycloakUrl + "/admin/realms/" + realm + "/users/" + id;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        try {
            restTemplate.exchange(url, HttpMethod.DELETE, request, String.class);
            System.out.println("🗑️ Usuário excluído com sucesso do Keycloak! ID: " + id);
        } catch (HttpClientErrorException e) {
            System.err.println("❌ Erro ao excluir usuário no Keycloak:");
            System.err.println("Status: " + e.getStatusCode());
            System.err.println("Body: " + e.getResponseBodyAsString());
            throw new RuntimeException("Falha ao excluir usuário: " + e.getResponseBodyAsString(), e);
        } catch (Exception e) {
            System.err.println("⚠️ Erro inesperado ao excluir usuário: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erro inesperado ao excluir usuário", e);
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
            System.err.println("❌ Falha ao obter token administrativo: " + e.getResponseBodyAsString());
            throw e;
        }
    }
}
