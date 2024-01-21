package br.com.devsibre.Service.Inteface;

import java.util.List;

import br.com.devsibre.Domain.Entity.DTO.RelacionamentoDTO;
import br.com.devsibre.Domain.Entity.Formulario;
import br.com.devsibre.Domain.Entity.GrauDeParentesco;
import br.com.devsibre.Domain.Entity.Relacionamento;

public interface RelacionamentoService {

	List<Relacionamento> adicionarRelacionamento(Formulario pessoa1, Formulario pessoa2, GrauDeParentesco grauDeParentesco);
	
	void deletarRelacionamento(Long id);

	List<RelacionamentoDTO> listarFamiliarEGrauParentesco(Long idPessoa);

}
