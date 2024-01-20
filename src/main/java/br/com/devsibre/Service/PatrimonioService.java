package br.com.devsibre.Service;

import java.util.List;

import br.com.devsibre.Model.PatrimonioModel;

public interface PatrimonioService {

	List<PatrimonioModel> listAll();

	//List<PatrimonioModel> findByNomeContainingIgnoreCase(String nome);

	boolean alterar(PatrimonioModel cnt);

	PatrimonioModel getId(Long id);

	PatrimonioModel saveOrUpdate(PatrimonioModel cm);

	void delete(Long id);
}
