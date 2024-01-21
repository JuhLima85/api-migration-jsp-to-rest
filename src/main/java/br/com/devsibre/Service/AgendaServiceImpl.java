package br.com.devsibre.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.devsibre.Domain.Entity.Agenda;
import br.com.devsibre.Domain.Repository.AgendaRepository;
import br.com.devsibre.Service.Inteface.AgendaService;

@Service
public class AgendaServiceImpl implements AgendaService{

	@Autowired
	private AgendaRepository cr; 
	
	@Override
	public List<Agenda> listAll() {
		List<Agenda> cm = new ArrayList<>();
        cr.findAll().forEach(cm::add); //fun with Java 8
        return cm;
    }

	@Override
	public Agenda getById(Long id) {
		 return cr.findById(id).orElse(null);
	}

	@Override
	public Agenda saveOrUpdate(Agenda p) {
		 cr.save(p);
	        return p;
	}

	@Override
	public void delete(Long id) {
		 cr.deleteById(id);
		
	}

	@Override
	public Agenda saveOrUpdateCadastro(Agenda p) {
		 cr.save(p);
	        return p;
	}

}
