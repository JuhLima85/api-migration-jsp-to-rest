package br.com.devsibre.Controller;

import br.com.devsibre.Domain.Entity.Contatos.Oracao;
import br.com.devsibre.Service.OracaoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static br.com.devsibre.UtilsReports.ModelAuthentication_Report.addAuthenticationStatusToModel;

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
    public List<Oracao> listarTodosOracoes(Model model, Authentication authentication) {
        addAuthenticationStatusToModel(model, authentication);
        return oracaoService.ListAll();
    }
    @GetMapping("/{id}")
    public Optional<Oracao> buscarOracaoPorId(@PathVariable Long id, Model model, Authentication authentication) {
        addAuthenticationStatusToModel(model, authentication);
        return Optional.ofNullable(oracaoService.getById(id));
    }
    @PostMapping
    public Oracao savar(@RequestBody Oracao oracao, Model model, Authentication authentication) {
        addAuthenticationStatusToModel(model, authentication);
        return oracaoService.save(oracao);
    }
    @DeleteMapping("/{id}")
    public void excluirOracoes(@PathVariable Long id, Model model, Authentication authentication) {
        addAuthenticationStatusToModel(model, authentication);
        oracaoService.delete(id);
    }
}
