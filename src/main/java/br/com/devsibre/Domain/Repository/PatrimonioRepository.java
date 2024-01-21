package br.com.devsibre.Domain.Repository;

import br.com.devsibre.Domain.Entity.Patrimonio;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatrimonioRepository extends CrudRepository<Patrimonio, Long> {

	//List<PatrimonioModel> findByNomeContainingIgnoreCase(String nome);
}
