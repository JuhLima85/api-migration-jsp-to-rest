package br.com.devsibre.Domain.Entity.DTO;

import lombok.Data;
import java.util.List;

@Data
public class KeycloakUserDTO {
    private String id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private boolean enabled = true;
    private String password;
    private List<String> roles;
}
