package br.com.devsibre.ServiceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.devsibre.Dtos.FamiliaDTO;
import br.com.devsibre.Model.FamiliaModel;
import br.com.devsibre.Model.FilhoModel;
import br.com.devsibre.Model.FormularioModel;

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
	                  "FROM FormularioModel f " +
	                  "JOIN f.familia fam " +
	                  "JOIN fam.filhos filho " +
	                  "WHERE f.nome = :nome";

	   
		TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class);
	    query.setParameter("nome", nome);

	    List<Object[]> results = query.getResultList();
	    List<FamiliaDTO> familiaDTOs = new ArrayList<>();

	    for (Object[] result : results) {
	        FormularioModel formulario = (FormularioModel) result[0];
	        FamiliaModel familia = (FamiliaModel) result[1];
	        FilhoModel filho = (FilhoModel) result[2];

	        FamiliaDTO familiaDTO = new FamiliaDTO();
	        familiaDTO.setFormulario(formulario);
	        familiaDTO.setFamilia(familia);
	        familiaDTO.setFilhos(Collections.singletonList(filho));

	        familiaDTOs.add(familiaDTO);
	    }

	    return familiaDTOs;
	}
}