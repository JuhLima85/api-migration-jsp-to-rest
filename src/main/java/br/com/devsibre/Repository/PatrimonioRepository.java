package br.com.devsibre.Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.devsibre.Model.PatrimonioModel;

@Repository
public interface PatrimonioRepository extends CrudRepository<PatrimonioModel, Long> {

	//List<PatrimonioModel> findByNomeContainingIgnoreCase(String nome);
}
