package br.com.devsibre.Domain.Entity.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RelacionamentoDTO {
	private Long id;
	private String nomeFamiliar;
	private String tipo;

	public RelacionamentoDTO(Long id, String nomeFamiliar, String tipo) {
		this.id = id;
		this.nomeFamiliar = nomeFamiliar;
		this.tipo = tipo;
	}
}
