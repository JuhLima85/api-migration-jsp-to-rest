package br.com.devsibre.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.devsibre.Domain.Entity.DTO.FamiliaDTO;
import br.com.devsibre.Domain.Entity.Familia;
import br.com.devsibre.Domain.Entity.Filho;
import br.com.devsibre.Domain.Entity.Formulario;

@Service
@Transactional
public class FamiliaServiceImpl {
	
	private final EntityManager entityManager;

	@Autowired
	public FamiliaServiceImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public List<FamiliaDTO> buscarPorNome(String nome) {
	    String jpql = "SELECT f, fam, filho " +
	                  "FROM Formulario f " +
	                  "JOIN f.familia fam " +
	                  "JOIN fam.filhos filho " +
	                  "WHERE f.nome = :nome";

	   
		TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class);
	    query.setParameter("nome", nome);

	    List<Object[]> results = query.getResultList();
	    List<FamiliaDTO> familiaDTOs = new ArrayList<>();

	    for (Object[] result : results) {
	        Formulario formulario = (Formulario) result[0];
	        Familia familia = (Familia) result[1];
	        Filho filho = (Filho) result[2];

	        FamiliaDTO familiaDTO = new FamiliaDTO();
	        familiaDTO.setFormulario(formulario);
	        familiaDTO.setFamilia(familia);
	        familiaDTO.setFilhos(Collections.singletonList(filho));

	        familiaDTOs.add(familiaDTO);
	    }

	    return familiaDTOs;
	}
}