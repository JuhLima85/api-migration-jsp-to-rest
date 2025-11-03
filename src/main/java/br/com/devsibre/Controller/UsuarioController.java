package br.com.devsibre.Controller;

import br.com.devsibre.Domain.Entity.DTO.KeycloakUserDTO;
import br.com.devsibre.Service.KeycloakAdminServiceImpl;
import br.com.devsibre.util.Mensagens;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = {
        "http://localhost:4200",
        "https://sibre-frontend-production.up.railway.app"
})
@RequiredArgsConstructor
public class UsuarioController {

    private final KeycloakAdminServiceImpl keycloakAdminService;

    @PostMapping
    @RolesAllowed("admin")
    public ResponseEntity<String> criarUsuario(@RequestBody KeycloakUserDTO dto) {
        keycloakAdminService.criarUsuario(dto);
        return ResponseEntity.ok(Mensagens.Usuario.SUCESSO_CRIAR);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> atualizar(@PathVariable String id, @RequestBody KeycloakUserDTO dto) {
        keycloakAdminService.atualizarUsuario(id, dto);
        return ResponseEntity.ok(Mensagens.Usuario.SUCESSO_ATUALIZAR);
    }

    @DeleteMapping("/{id}")
    @RolesAllowed("admin")
    public ResponseEntity<String> excluir(@PathVariable String id) {
        keycloakAdminService.excluirUsuario(id);
        return ResponseEntity.ok(Mensagens.Usuario.SUCESSO_EXCLUIR);
    }

    @GetMapping
    @RolesAllowed({"admin", "gestor"})
    public ResponseEntity<List<KeycloakUserDTO>>  listarTodos() {
        List<KeycloakUserDTO> usuarios = keycloakAdminService.listarUsuariosComRoles();
        return ResponseEntity.ok(usuarios);
    }
}

