package br.com.devsibre.Controller;


import br.com.devsibre.Domain.Entity.DTO.KeycloakUserDTO;
import br.com.devsibre.Service.KeycloakAdminServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class UsuarioController {

    private final KeycloakAdminServiceImpl keycloakAdminService;

    @PostMapping
    public ResponseEntity<String> criarUsuario(@RequestBody KeycloakUserDTO dto) {
        keycloakAdminService.criarUsuario(dto);
        return ResponseEntity.ok("Usuário criado com sucesso no Keycloak!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizar(@PathVariable String id, @RequestBody KeycloakUserDTO dto) {
        keycloakAdminService.atualizarUsuario(id, dto);
        return ResponseEntity.ok("Usuário atualizado com sucesso!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluir(@PathVariable String id) {
        keycloakAdminService.excluirUsuario(id);
        return ResponseEntity.ok("Usuário excluído com sucesso!");
    }

    @GetMapping
    public ResponseEntity<List<KeycloakUserDTO>>  listarTodos() {
        List<KeycloakUserDTO> usuarios = keycloakAdminService.listarUsuariosComRoles();
        return ResponseEntity.ok(usuarios);
    }
}

