package br.com.devsibre.Service;

import java.util.List;

import br.com.devsibre.Model.CantinaModel;

public interface CantinaService {

	List<CantinaModel> listAll();

	List<CantinaModel> findByNomeContainingIgnoreCase(String nome);

	boolean alterar(CantinaModel cnt);

	CantinaModel getId(Long id);

	CantinaModel saveOrUpdate(CantinaModel cm);

	void delete(Long id);
}
