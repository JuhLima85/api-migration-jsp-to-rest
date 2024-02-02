package br.com.devsibre.Controller;

import br.com.devsibre.Domain.Entity.Contatos.Oracao;
import br.com.devsibre.Service.OracaoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST})
@RequestMapping("/oracao")
public class OracaoController {
	
    private OracaoService oracaoService;

    @Autowired
    public OracaoController(OracaoService oracaoService){
       this.oracaoService = oracaoService;
    }
    @GetMapping
    public List<Oracao> listarTodosOracoes() {
        return oracaoService.ListAll();
    }
    @GetMapping("/{id}")
    public Optional<Oracao> buscarOracaoPorId(@PathVariable Long id) {
        return Optional.ofNullable(oracaoService.getById(id));
    }
    @PostMapping("/gravar")
    public Oracao savar(@RequestBody Oracao oracao) {
        return oracaoService.save(oracao);
    }
    @DeleteMapping("/{id}")
    public void excluirOracoes(@PathVariable Long id) {
        oracaoService.delete(id);
    }


}
