package br.com.devsibre.Domain.Repository;

import br.com.devsibre.Domain.Entity.Formulario;
import br.com.devsibre.Domain.Entity.Relacionamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RelacionamentoRepository extends JpaRepository<Relacionamento, Long> {
	
	 /**
     * Busca um relacionamento específico pelo ID do relacionamento.
     *
     * @param relacionamentoId O ID do relacionamento a ser buscado.
     * @return O relacionamento correspondente, ou null se não encontrado.
     */
    @Query("SELECT r FROM Relacionamento r WHERE r.id = :relacionamentoId")
    Relacionamento findRelacionamentoById(@Param("relacionamentoId") Long relacionamentoId);

	/*
	 * recupera todos os relacionamentos onde uma pessoa específica (pessoa1) está envolvida
	 * */
	 @Query("SELECT r FROM Relacionamento r WHERE r.pessoa1.id = :pessoaId")
	 List<Relacionamento> findAllByPessoa1Id(Long pessoaId);
	 
	 /*
		 * recupera todos os relacionamentos onde uma pessoa específica (pessoa2) está envolvida
		 * */
		 @Query("SELECT r FROM Relacionamento r WHERE r.pessoa2.id = :pessoaId")
		 List<Relacionamento> findAllByPessoa2Id(Long pessoaId);
	 
	
	 /*
	  * recupera relacionamentos entre duas pessoas específicas, independentemente de quem é pessoa1 ou pessoa2.
	  * */
	 @Query("SELECT r FROM Relacionamento r " +
	           "WHERE (r.pessoa1 = :pessoa1 AND r.pessoa2 = :pessoa2) OR " +
	           "(r.pessoa1 = :pessoa2 AND r.pessoa2 = :pessoa1)")
	    List<Relacionamento> findRelacionamentos(Formulario pessoa1, Formulario pessoa2);
	 
	 /**
	     * Deleta todos os relacionamentos onde a pessoa1 tem o ID especificado.
	     *
	     * @param pessoaId O ID da pessoa1 para a qual os relacionamentos devem ser deletados.
	     */
	 @Modifying
	    @Query("DELETE FROM Relacionamento r WHERE r.pessoa1.id = :pessoaId")
	    void deleteByPessoa1Id(@Param("pessoaId") Long pessoaId); 
	
 
}
