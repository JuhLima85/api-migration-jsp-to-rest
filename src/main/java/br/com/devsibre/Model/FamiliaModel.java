package br.com.devsibre.Model;

import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.devsibre.Enuns.StatusEnum;

@Entity
@Table(name = "familia")
public class FamiliaModel {
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
    private List<FilhoModel> filhos;
    
    public FamiliaModel() {
		// TODO Auto-generated constructor stub
	}	

	public FamiliaModel(Long id, String nomeDoConjuge, String telefone, String email, Integer quantidadeFilhos,
			String dataNascEsp, StatusEnum seBatizado, StatusEnum status, List<FilhoModel> filhos) {
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

	public List<FilhoModel> getFilhos() {
		return filhos;
	}

	public void setFilhos(List<FilhoModel> filhos) {
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
		FamiliaModel other = (FamiliaModel) obj;
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
