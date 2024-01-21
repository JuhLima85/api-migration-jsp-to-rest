package br.com.devsibre.Service;

import java.util.ArrayList;
import java.util.List;

import br.com.devsibre.Domain.Entity.Patrimonio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.devsibre.Domain.Repository.PatrimonioRepository;
import br.com.devsibre.Service.Inteface.PatrimonioService;


@Service
public class PatrimonioServiceImpl implements PatrimonioService{

	@Autowired
    private PatrimonioRepository pr;
	
	@Override
	public List<Patrimonio> listAll() {
		List<Patrimonio> cm = new ArrayList<>();
        pr.findAll().forEach(cm::add); //fun with Java 8
        return cm;		
	}	

	@Override
	public boolean alterar(Patrimonio cnt) {
		try {
            pr.save(cnt);
            return true;
        } catch (Exception e) {
            return false;
        }  
	}

	@Override
	public Patrimonio getId(Long id) {
		return pr.findById(id).get();
	}

	@Override
	public Patrimonio saveOrUpdate(Patrimonio cm) {
		pr.save(cm);
        return cm;
	}

	@Override
	public void delete(Long id) {
		  pr.deleteById(id);
		
	}

}
