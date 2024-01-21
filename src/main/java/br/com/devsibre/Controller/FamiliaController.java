package br.com.devsibre.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.devsibre.Domain.Entity.DTO.FamiliaDTO;
import br.com.devsibre.Service.FamiliaServiceImpl;

@RestController
@RequestMapping("/familia")
public class FamiliaController {

	private final FamiliaServiceImpl familiaService;

	@Autowired
	public FamiliaController(FamiliaServiceImpl familiaService) {
		this.familiaService = familiaService;
	}

	@GetMapping("/{nome}")
	public List<FamiliaDTO> buscarPorNome(@PathVariable String nome) {
		return familiaService.buscarPorNome(nome);
	}
}

