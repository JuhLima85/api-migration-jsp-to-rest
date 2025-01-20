package br.com.devsibre.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.devsibre.Domain.Entity.DTO.FamiliaDTO;
import br.com.devsibre.Service.FamiliaServiceImpl;

import static br.com.devsibre.UtilsReports.ModelAuthentication_Report.addAuthenticationStatusToModel;

@RestController
@RequestMapping("/familia")
public class FamiliaController {

	private final FamiliaServiceImpl familiaService;

	@Autowired
	public FamiliaController(FamiliaServiceImpl familiaService) {
		this.familiaService = familiaService;
	}

	@GetMapping("/{nome}")
	public List<FamiliaDTO> buscarPorNome(@PathVariable String nome, Model model, Authentication authentication) {
		addAuthenticationStatusToModel(model, authentication);
		return familiaService.buscarPorNome(nome);
	}
}

