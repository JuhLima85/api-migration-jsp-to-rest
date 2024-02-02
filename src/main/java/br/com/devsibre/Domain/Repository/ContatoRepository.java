package br.com.devsibre.Domain.Repository;

import br.com.devsibre.Domain.Entity.Contatos.Contato;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContatoRepository extends JpaRepository<Contato, Long> {

}
