package br.com.devsibre.Domain.Entity.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelacionamentoDTO {
	private Long idRelacionamento;
	private Long idPessoa1;
	private Long idPessoa2;
	private String nomeFamiliar;
	private String grauParentesco;
}
