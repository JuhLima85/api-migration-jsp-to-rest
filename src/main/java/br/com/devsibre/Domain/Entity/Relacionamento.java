package br.com.devsibre.Domain.Entity;

//import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Relacionamento {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idRelacionamento;

	@ManyToOne
	@JoinColumn(name = "pessoa1_id")
	@JsonBackReference  // evita serializar de volta
	private Formulario pessoa1;

	@ManyToOne
	@JoinColumn(name = "pessoa2_id")
	private Formulario pessoa2;

	@ManyToOne
	@JoinColumn(name = "grau_de_parentesco_id")
	private GrauDeParentesco grauDeParentesco;

	public Relacionamento(Long idRelacionamento, Formulario pessoa2, GrauDeParentesco grauDeParentesco) {
		super();
		this.idRelacionamento = idRelacionamento;
		this.pessoa2 = pessoa2;
		this.grauDeParentesco = grauDeParentesco;
	}
}
	