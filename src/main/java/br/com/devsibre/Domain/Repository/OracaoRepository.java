package br.com.devsibre.Domain.Repository;

import br.com.devsibre.Domain.Entity.Oracao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OracaoRepository extends JpaRepository<Oracao, Long> {
}
