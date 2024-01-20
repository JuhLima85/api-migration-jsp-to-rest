package br.com.devsibre.Service;

import java.util.List;

import br.com.devsibre.Dtos.SaveOrUpdateResponse;
import br.com.devsibre.Model.FormularioModel;
import br.com.devsibre.Model.GrauDeParentesco;
import br.com.devsibre.Model.Relacionamento;

public interface FormularioService {

	List<FormularioModel> listAll();

	List<FormularioModel> findByNomeContainingIgnoreCase(String nome);

	boolean alterar(FormularioModel dto);

	FormularioModel getId(Long id);

	//FormularioModel saveOrUpdate(FormularioModel cm);
	
	 SaveOrUpdateResponse saveOrUpdate(FormularioModel cm);
	 
	void delete(Long id);

	// Relacionamento adicionarRelacionamento(PessoaModel pessoa1, PessoaModel
	// pessoa2, GrauDeParentesco grauDeParentesco);

	GrauDeParentesco buscarGrauDeParentescoPorGrau(int parentesco, Relacionamento relacionamento);

}
