package br.com.devsibre.Domain.Entity;

//import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Entity
@Table(name = "cadastro", uniqueConstraints = {@UniqueConstraint(columnNames = "fone", name = "unique_fone_constraint")})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Formulario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_c;
	@Column(length = 200, nullable = false)
	private String nome;
	@Column(length = 20, unique = true)
	private String fone;
	@Column(length = 40)
	private String email;
	private String data;
	private String cep;
	private String logradouro;
	private String bairro;
	private String localidade;
	private String uf;
	private boolean membro;

	@OneToMany(mappedBy = "pessoa1", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Relacionamento> relacionamentosPessoa1;
	
	@OneToMany(mappedBy = "pessoa2", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Relacionamento> relacionamentosPessoa2;

}
