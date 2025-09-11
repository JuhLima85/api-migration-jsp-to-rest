package br.com.devsibre.Domain.Repository;

import br.com.devsibre.Domain.Entity.DTO.RelacionamentoDTO;
import br.com.devsibre.Domain.Entity.Parentescos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParentescosRepository extends JpaRepository<Parentescos, Long> {

    @Query("""
     SELECT new br.com.devsibre.Domain.Entity.DTO.RelacionamentoDTO(
        r.id,
        CASE 
            WHEN r.pessoa.id = :id THEN r.pessoaRelacionada.nome
            ELSE r.pessoa.nome
        END,
        r.tipo
    )
    FROM Parentescos r
    WHERE r.pessoa.id = :id OR r.pessoaRelacionada.id = :id
""")
    List<RelacionamentoDTO> buscarRelacionamentos(@Param("id") Long id);
}
