package br.com.devsibre.Controller;

import br.com.devsibre.Domain.Entity.Contatos.Contato;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import br.com.devsibre.Service.ContatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static br.com.devsibre.UtilsReports.ModelAuthentication_Report.addAuthenticationStatusToModel;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST})
@RequestMapping("/contato")
public class ContatoController {

    private final ContatoService contatoService;

    @Autowired
    public ContatoController(ContatoService contatoService){
        this.contatoService = contatoService;
    }

    @PostMapping("/gravar")
    public ResponseEntity<Object> gravarContato(@RequestBody Contato contato, Model model, Authentication authentication) {
        addAuthenticationStatusToModel(model, authentication);
        Contato novoContato = contatoService.save(contato);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoContato);
    }

    @GetMapping
    public List<Contato> listarTodosContato(Model model, Authentication authentication) {
        addAuthenticationStatusToModel(model, authentication);
        return contatoService.ListAll();
    }
    @GetMapping("/{id}")
    public Optional<Contato> buscarContatoPorId(@PathVariable Long id, Model model, Authentication authentication) {
        addAuthenticationStatusToModel(model, authentication);
        return Optional.ofNullable(contatoService.getById(id));
    }
    @DeleteMapping("/{id}")
    public void excluirContato(@PathVariable Long id, Model model, Authentication authentication) {
        addAuthenticationStatusToModel(model, authentication);
        contatoService.delete(id);
    }


}
