package br.com.devsibre.ServiceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.devsibre.Model.AgendaModel;
import br.com.devsibre.Repository.AgendaRepository;
import br.com.devsibre.Service.AgendaService;

@Service
public class AgendaServiceImpl implements AgendaService{

	@Autowired
	private AgendaRepository cr; 
	
	@Override
	public List<AgendaModel> listAll() {
		List<AgendaModel> cm = new ArrayList<>();
        cr.findAll().forEach(cm::add); //fun with Java 8
        return cm;
    }

	@Override
	public AgendaModel getById(Long id) {
		 return cr.findById(id).orElse(null);
	}

	@Override
	public AgendaModel saveOrUpdate(AgendaModel p) {
		 cr.save(p);
	        return p;
	}

	@Override
	public void delete(Long id) {
		 cr.deleteById(id);
		
	}

	@Override
	public AgendaModel saveOrUpdateCadastro(AgendaModel p) {
		 cr.save(p);
	        return p;
	}

}
