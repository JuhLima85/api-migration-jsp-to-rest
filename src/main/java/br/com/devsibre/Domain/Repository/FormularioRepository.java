package br.com.devsibre.Domain.Repository;

import br.com.devsibre.Domain.Entity.DTO.CadastroDTO;
import br.com.devsibre.Domain.Entity.Formulario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface FormularioRepository extends CrudRepository<Formulario, Long> {

	List<Formulario> findByNomeContainingIgnoreCase(String nome);
		
	@Query("SELECT new br.com.devsibre.Domain.Entity.DTO.CadastroDTO(f.id_c, f.nome, f.fone, f.email, f.data, f.cep, f.logradouro, f.bairro, f.localidade, f.uf, f.membro) FROM Formulario f WHERE f.id = :id")
	CadastroDTO findCadastroDTOById(@Param("id") Long id);
	
	 List<Formulario> findByMembroTrue();
	 
	 List<Formulario> findByMembroFalse();
	 
	 Formulario findByFone(String fone);
	
}
