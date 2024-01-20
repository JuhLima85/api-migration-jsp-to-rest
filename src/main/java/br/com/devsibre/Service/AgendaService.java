package br.com.devsibre.Service;

import java.util.List;

import br.com.devsibre.Model.AgendaModel;

public interface AgendaService {

	List<AgendaModel> listAll();

	AgendaModel getById(Long id);

	AgendaModel saveOrUpdate(AgendaModel p);

	void delete(Long id);

	AgendaModel saveOrUpdateCadastro(AgendaModel p);
}
