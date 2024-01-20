package br.com.devsibre.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.devsibre.Model.AgendaModel;

@Repository
public interface AgendaRepository extends JpaRepository<AgendaModel, Long>{

}
