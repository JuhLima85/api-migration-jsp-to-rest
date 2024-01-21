package br.com.devsibre.Domain.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import br.com.devsibre.Domain.Entity.Filho;

@Repository
@Transactional
public interface FilhoRepository extends CrudRepository<Filho, Long> {


}
