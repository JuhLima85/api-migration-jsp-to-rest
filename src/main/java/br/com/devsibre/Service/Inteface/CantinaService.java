package br.com.devsibre.Service.Inteface;

import java.util.List;

import br.com.devsibre.UtilsReports.Cantina;

public interface CantinaService {

	List<Cantina> listAll();

	List<Cantina> findByNomeContainingIgnoreCase(String nome);

	boolean alterar(Cantina cnt);

	Cantina getId(Long id);

	Cantina saveOrUpdate(Cantina cm);

	void delete(Long id);
}
