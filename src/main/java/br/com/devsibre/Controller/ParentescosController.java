package br.com.devsibre.Controller;

import br.com.devsibre.Domain.Entity.DTO.RelacionamentoDTO;
import br.com.devsibre.Service.Inteface.ParentescosService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parentescos")
@CrossOrigin(origins = {
        "http://localhost:4200",
        "https://sibre-frontend-production.up.railway.app"
})
public class ParentescosController {

     private final ParentescosService parentescosService;

    public ParentescosController(ParentescosService parentescosService) {
        this.parentescosService = parentescosService;
    }

    @GetMapping("/pessoa/{id}/relacionamentos")
    public List<RelacionamentoDTO> buscarRelacionamentos(@PathVariable Long id) {
        return parentescosService.buscarRelacionamentos(id);
    }
}
