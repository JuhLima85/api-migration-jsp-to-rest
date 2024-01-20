package br.com.devsibre.Model;

import java.util.Objects;

import javax.persistence.*;

@Entity
public class Relacionamento {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "pessoa1_id")
	private FormularioModel pessoa1;

	@ManyToOne
	@JoinColumn(name = "pessoa2_id")
	private FormularioModel pessoa2;

	@ManyToOne
	@JoinColumn(name = "grau_de_parentesco_id")
	private GrauDeParentesco grauDeParentesco;

	public Relacionamento() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Relacionamento(Long id, FormularioModel pessoa1, FormularioModel pessoa2, GrauDeParentesco grauDeParentesco) {
		super();
		this.id = id;
		this.pessoa1 = pessoa1;
		this.pessoa2 = pessoa2;
		this.grauDeParentesco = grauDeParentesco;
	}
	
	public Relacionamento(Long id, FormularioModel pessoa2, GrauDeParentesco grauDeParentesco) {
		super();
		this.id = id;		
		this.pessoa2 = pessoa2;
		this.grauDeParentesco = grauDeParentesco;
	}

	public FormularioModel getPessoa1() {
		return pessoa1;
	}

	public void setPessoa1(FormularioModel pessoa1) {
		this.pessoa1 = pessoa1;
	}

	public FormularioModel getPessoa2() {
		return pessoa2;
	}

	public void setPessoa2(FormularioModel pessoa2) {
		this.pessoa2 = pessoa2;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public GrauDeParentesco getGrauDeParentesco() {
		return grauDeParentesco;
	}

	public void setGrauDeParentesco(GrauDeParentesco grauDeParentesco) {
		this.grauDeParentesco = grauDeParentesco;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Relacionamento other = (Relacionamento) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Relacionamento [id=" + id + ", pessoa1=" + pessoa1.getId_c() + ", pessoa2=" + pessoa2.getId_c() + ", grauDeParentesco="
				+ grauDeParentesco + "]";
	}	
}
	