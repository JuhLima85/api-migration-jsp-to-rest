package br.com.devsibre.Domain.Repository;


import br.com.devsibre.Domain.Entity.Agenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgendaRepository extends JpaRepository<Agenda, Long>{

}
