package br.com.devsibre.Domain.Entity.DTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HistoricoDTO {

    private CadastroDTO cadastro;

    // garanta que a API sempre responda [] ao invés de null
    @Builder.Default
    private List<RelacionamentoDTO> relacionamentos = List.of();
}
