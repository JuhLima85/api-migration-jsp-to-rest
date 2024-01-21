package br.com.devsibre.Domain.Entity;

import br.com.devsibre.Enuns.StatusEnum;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "familia")
public class Familia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomeDoConjuge;
    private String telefone;
    private String email;
    private Integer quantidadeFilhos;
    private String dataNascEsp;    
    @Enumerated(EnumType.STRING)
    private StatusEnum seBatizado;
    @Enumerated(EnumType.STRING)
    private StatusEnum status;    
    @OneToMany(cascade = CascadeType.ALL)
    private List<Filho> filhos;
    
    public Familia() {
		// TODO Auto-generated constructor stub
	}	

	public Familia(Long id, String nomeDoConjuge, String telefone, String email, Integer quantidadeFilhos,
				   String dataNascEsp, StatusEnum seBatizado, StatusEnum status, List<Filho> filhos) {
		super();
		this.id = id;
		this.nomeDoConjuge = nomeDoConjuge;
		this.telefone = telefone;
		this.email = email;
		this.quantidadeFilhos = quantidadeFilhos;
		this.dataNascEsp = dataNascEsp;
		this.seBatizado = seBatizado;
		this.status = status;
		this.filhos = filhos;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeDoConjuge() {
		return nomeDoConjuge;
	}

	public void setNomeDoConjuge(String nomeDoConjuge) {
		this.nomeDoConjuge = nomeDoConjuge;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getQuantidadeFilhos() {
		return quantidadeFilhos;
	}

	public void setQuantidadeFilhos(Integer quantidadeFilhos) {
		this.quantidadeFilhos = quantidadeFilhos;
	}

	public String getdataNascEsp() {
		return dataNascEsp;
	}

	public void setdataNascEsp(String dataNascEsp) {
		this.dataNascEsp = dataNascEsp;
	}

	public StatusEnum getSeBatizado() {
		return seBatizado;
	}

	public void setSeBatizado(StatusEnum batizado) {
		this.seBatizado = batizado;
	}

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

	public List<Filho> getFilhos() {
		return filhos;
	}

	public void setFilhos(List<Filho> filhos) {
		this.filhos = filhos;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dataNascEsp, email, filhos, id, nomeDoConjuge, quantidadeFilhos, seBatizado, status, telefone);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Familia other = (Familia) obj;
		return Objects.equals(dataNascEsp, other.dataNascEsp) && Objects.equals(email, other.email)
				&& Objects.equals(filhos, other.filhos) && Objects.equals(id, other.id)
				&& Objects.equals(nomeDoConjuge, other.nomeDoConjuge)
				&& Objects.equals(quantidadeFilhos, other.quantidadeFilhos)
				&& Objects.equals(seBatizado, other.seBatizado) && Objects.equals(status, other.status)
				&& Objects.equals(telefone, other.telefone);
	}

	@Override
	public String toString() {
	    return "FamiliaModel{" +
	            "nomeDoConjuge='" + nomeDoConjuge + '\'' +
	            ", telefone='" + telefone + '\'' +
	            ", email='" + email + '\'' +
	            ", dataNascEsp='" + dataNascEsp + '\'' +
	            ", quantidadeFilhos=" + quantidadeFilhos +
	            ", status=" + status +
	            ", seBatizado=" + seBatizado +
	            '}';
	}

}
