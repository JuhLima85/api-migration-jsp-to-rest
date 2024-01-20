package br.com.devsibre.ServiceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.devsibre.Model.PatrimonioModel;
import br.com.devsibre.Repository.PatrimonioRepository;
import br.com.devsibre.Service.PatrimonioService;


@Service
public class PatrimonioServiceImpl implements PatrimonioService{

	@Autowired
    private PatrimonioRepository pr;
	
	@Override
	public List<PatrimonioModel> listAll() {
		List<PatrimonioModel> cm = new ArrayList<>();
        pr.findAll().forEach(cm::add); //fun with Java 8
        return cm;		
	}	

	@Override
	public boolean alterar(PatrimonioModel cnt) {
		try {
            pr.save(cnt);
            return true;
        } catch (Exception e) {
            return false;
        }  
	}

	@Override
	public PatrimonioModel getId(Long id) {
		return pr.findById(id).get();
	}

	@Override
	public PatrimonioModel saveOrUpdate(PatrimonioModel cm) {
		pr.save(cm);
        return cm;
	}

	@Override
	public void delete(Long id) {
		  pr.deleteById(id);
		
	}

}
