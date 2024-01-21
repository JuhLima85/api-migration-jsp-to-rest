package br.com.devsibre.Domain.Repository;

import br.com.devsibre.UtilsReports.Cantina;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CantinaRepository extends CrudRepository<Cantina, Long> {

	List<Cantina> findByNomeContainingIgnoreCase(String nome);
}
