package br.com.devsibre.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.devsibre.Model.GrauDeParentesco;

@Repository
public interface GrauDeParentescoRepository extends JpaRepository<GrauDeParentesco, Long> {
	GrauDeParentesco findByGrau(int grau);
	
	

}
