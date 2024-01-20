package br.com.devsibre.Service;

import java.util.List;

import br.com.devsibre.Dtos.RelacionamentoDTO;
import br.com.devsibre.Model.FormularioModel;
import br.com.devsibre.Model.GrauDeParentesco;
import br.com.devsibre.Model.Relacionamento;

public interface RelacionamentoService {

	List<Relacionamento> adicionarRelacionamento(FormularioModel pessoa1, FormularioModel pessoa2, GrauDeParentesco grauDeParentesco);	
	
	void deletarRelacionamento(Long id);

	List<RelacionamentoDTO> listarFamiliarEGrauParentesco(Long idPessoa);

}
