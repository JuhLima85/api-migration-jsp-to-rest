package br.com.devsibre.Domain.Entity.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class VincularRelacionamentoDTO {

    @NotNull(message = "O ID da pessoa1 é obrigatório")
    private Long pessoa1Id;

    @NotNull(message = "O ID da pessoa2 é obrigatório")
    private Long pessoa2Id;

    @Min(value = 1, message = "O grau de parentesco deve ser um número válido")
    private int grauParentesco;
}
