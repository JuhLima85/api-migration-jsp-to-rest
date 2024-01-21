package br.com.devsibre.Domain.Repository;

import br.com.devsibre.Domain.Entity.GrauDeParentesco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrauDeParentescoRepository extends JpaRepository<GrauDeParentesco, Long> {
	GrauDeParentesco findByGrau(int grau);

}
