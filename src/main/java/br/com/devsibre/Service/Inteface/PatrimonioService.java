package br.com.devsibre.Service.Inteface;

import java.util.List;

import br.com.devsibre.Domain.Entity.Patrimonio;


public interface PatrimonioService {

	List<Patrimonio> listAll();

	//List<PatrimonioModel> findByNomeContainingIgnoreCase(String nome);

	boolean alterar(Patrimonio cnt);

	Patrimonio getId(Long id);

	Patrimonio saveOrUpdate(Patrimonio cm);

	void delete(Long id);
}
