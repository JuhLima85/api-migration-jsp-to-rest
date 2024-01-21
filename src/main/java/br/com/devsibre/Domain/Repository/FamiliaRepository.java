package br.com.devsibre.Domain.Repository;

import br.com.devsibre.Domain.Entity.Familia;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface FamiliaRepository extends CrudRepository<Familia, Long> {



}
