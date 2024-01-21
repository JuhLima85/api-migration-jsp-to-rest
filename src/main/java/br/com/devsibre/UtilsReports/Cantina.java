package br.com.devsibre.UtilsReports;

import javax.persistence.*;

@Entity
@Table(name = "cantina")
public class Cantina {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_n;
    private String nome;
    private String descricao;
    private String data;
		
	public Cantina() {
		
	}
	
	public Cantina(Long id_n, String nome, String descricao, String data) {
		super();
		this.id_n = id_n;
		this.nome = nome;
		this.descricao = descricao;
		this.data = data;
	}

	public Long getId_n() {
		return id_n;
	}
	public void setId_n(Long id_n) {
		this.id_n = id_n;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
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
		result = prime * result + ((id_n == null) ? 0 : id_n.hashCode());
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
		Cantina other = (Cantina) obj;
		if (id_n == null) {
			if (other.id_n != null)
				return false;
		} else if (!id_n.equals(other.id_n))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Cantina [id_n=" + id_n + ", nome=" + nome + ", descricao=" + descricao + ", data=" + data + "]";
	}
	
	
	
}
