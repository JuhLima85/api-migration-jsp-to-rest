package br.com.devsibre.Service.Inteface;

import java.util.List;

import br.com.devsibre.Domain.Entity.DTO.SaveOrUpdateResponse;
import br.com.devsibre.Domain.Entity.Formulario;
import br.com.devsibre.Domain.Entity.GrauDeParentesco;
import br.com.devsibre.Domain.Entity.Relacionamento;

public interface FormularioService {

	List<Formulario> listAll();

	List<Formulario> findByNomeContainingIgnoreCase(String nome);

	boolean alterar(Formulario dto);

	Formulario getId(Long id);

	//FormularioModel saveOrUpdate(FormularioModel cm);
	
	 SaveOrUpdateResponse saveOrUpdate(Formulario cm);
	 
	void delete(Long id);

	// Relacionamento adicionarRelacionamento(PessoaModel pessoa1, PessoaModel
	// pessoa2, GrauDeParentesco grauDeParentesco);

	GrauDeParentesco buscarGrauDeParentescoPorGrau(int parentesco, Relacionamento relacionamento);

}
