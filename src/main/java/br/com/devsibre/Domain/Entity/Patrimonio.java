package br.com.devsibre.Domain.Entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "inventario")
public class Patrimonio {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id_p;
	private String descricao;
	private int quantidade;
	private BigDecimal preco;
	private String data;

	public Patrimonio() {

	}

	public Patrimonio(Long id_p, String descricao, int quantidade, BigDecimal preco, String data) {
		super();
		this.id_p = id_p;
		this.descricao = descricao;
		this.quantidade = quantidade;
		this.preco = preco;
		this.data = data;
	}

	public Long getId_p() {
		return id_p;
	}

	public void setId_p(Long id_p) {
		this.id_p = id_p;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id_p == null) ? 0 : id_p.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Patrimonio other = (Patrimonio) obj;
		if (id_p == null) {
			if (other.id_p != null)
				return false;
		} else if (!id_p.equals(other.id_p))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PatrimonioModel [id_p=" + id_p + ", descricao=" + descricao + ", quantidade=" + quantidade + ", preco="
				+ preco + ", data=" + data + "]";
	}

}
