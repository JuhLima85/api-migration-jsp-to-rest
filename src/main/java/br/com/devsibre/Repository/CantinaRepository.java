package br.com.devsibre.Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.devsibre.Model.CantinaModel;

@Repository
public interface CantinaRepository extends CrudRepository<CantinaModel, Long> {

	List<CantinaModel> findByNomeContainingIgnoreCase(String nome);
}
