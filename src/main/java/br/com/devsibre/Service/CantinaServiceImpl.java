package br.com.devsibre.Service;

import java.util.ArrayList;
import java.util.List;

import br.com.devsibre.UtilsReports.Cantina;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.devsibre.Domain.Repository.CantinaRepository;
import br.com.devsibre.Service.Inteface.CantinaService;

@Service
public class CantinaServiceImpl implements CantinaService{

	@Autowired
    private CantinaRepository cant;
	
	@Override
	public List<Cantina> listAll() {
		 List<Cantina> cm = new ArrayList<>();
	        cant.findAll().forEach(cm::add); //fun with Java 8
	        return cm;
	}

	@Override
	public List<Cantina> findByNomeContainingIgnoreCase(String nome) {
		return cant.findByNomeContainingIgnoreCase(nome);
	}

	@Override
	public boolean alterar(Cantina cnt) {
		 try {
	            cant.save(cnt);
	            return true;
	        } catch (Exception e) {
	            return false;
	        }  
	}

	@Override
	public Cantina getId(Long id) {
		return cant.findById(id).get();
	}

	@Override
	public Cantina saveOrUpdate(Cantina cm) {
		cant.save(cm);
        return cm;
	}

	@Override
	public void delete(Long id) {
		cant.deleteById(id);
		
	}

}
