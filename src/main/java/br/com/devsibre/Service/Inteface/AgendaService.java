package br.com.devsibre.Service.Inteface;

import java.util.List;

import br.com.devsibre.Domain.Entity.Agenda;


public interface AgendaService {

	List<Agenda> listAll();

	Agenda getById(Long id);

	Agenda saveOrUpdate(Agenda p);

	void delete(Long id);

	Agenda saveOrUpdateCadastro(Agenda p);
}
