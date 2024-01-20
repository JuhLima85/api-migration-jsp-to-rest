package br.com.devsibre.ServiceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.devsibre.Model.CantinaModel;
import br.com.devsibre.Repository.CantinaRepository;
import br.com.devsibre.Service.CantinaService;

@Service
public class CantinaServiceImpl implements CantinaService{

	@Autowired
    private CantinaRepository cant;
	
	@Override
	public List<CantinaModel> listAll() {
		 List<CantinaModel> cm = new ArrayList<>();
	        cant.findAll().forEach(cm::add); //fun with Java 8
	        return cm;
	}

	@Override
	public List<CantinaModel> findByNomeContainingIgnoreCase(String nome) {
		return cant.findByNomeContainingIgnoreCase(nome);
	}

	@Override
	public boolean alterar(CantinaModel cnt) {
		 try {
	            cant.save(cnt);
	            return true;
	        } catch (Exception e) {
	            return false;
	        }  
	}

	@Override
	public CantinaModel getId(Long id) {
		return cant.findById(id).get();
	}

	@Override
	public CantinaModel saveOrUpdate(CantinaModel cm) {
		cant.save(cm);
        return cm;
	}

	@Override
	public void delete(Long id) {
		cant.deleteById(id);
		
	}

}
