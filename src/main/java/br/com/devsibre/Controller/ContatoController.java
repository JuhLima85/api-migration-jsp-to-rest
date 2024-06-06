package br.com.devsibre.Controller;

import br.com.devsibre.Domain.Entity.Contatos.Contato;
import org.springframework.web.bind.annotation.CrossOrigin;
import br.com.devsibre.Service.ContatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST})
@RequestMapping("/contato")
public class ContatoController {

    private ContatoService contatoService;

    @Autowired
    public ContatoController(ContatoService contatoService){
        this.contatoService = contatoService;
    }

    @GetMapping
    public List<Contato> listarTodosContato() {
        return contatoService.ListAll();
    }
    @GetMapping("/{id}")
    public Optional<Contato> buscarContatoPorId(@PathVariable Long id) {
        return Optional.ofNullable(contatoService.getById(id));
    }
    @PostMapping
    public Contato savar(@RequestBody Contato contato) {
        return contatoService.sava(contato);
    }
    @DeleteMapping("/{id}")
    public void excluirContato(@PathVariable Long id) {
        contatoService.delete(id);
    }


}
