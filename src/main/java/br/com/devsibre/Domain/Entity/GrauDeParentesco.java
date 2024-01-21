package br.com.devsibre.Domain.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class GrauDeParentesco {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private int grau;
	private String descricao;

	public GrauDeParentesco() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	public GrauDeParentesco(Long id, int grau, String descricao) {
		super();
		this.id = id;
		this.grau = grau;
		this.descricao = descricao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getGrau() {
		return grau;
	}

	public void setGrau(int grau) {
		this.grau = grau;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
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
		GrauDeParentesco other = (GrauDeParentesco) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "GrauDeParentesco [id=" + id + ", grau=" + grau + ", descricao=" + descricao + "]";
	}

}
