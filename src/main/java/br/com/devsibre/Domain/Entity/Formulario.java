package br.com.devsibre.Domain.Entity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Entity
@Table(name = "cadastro", uniqueConstraints = {@UniqueConstraint(columnNames = "fone", name = "unique_fone_constraint")})
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
	private boolean batizado; // retirar

	@OneToMany(mappedBy = "pessoa1", cascade = CascadeType.ALL)
	private List<Relacionamento> relacionamentosPessoa1;
	
	@OneToMany(mappedBy = "pessoa2", cascade = CascadeType.ALL)
	private List<Relacionamento> relacionamentosPessoa2;

	public Formulario() {

	}

	public Formulario(Long id_c, String nome, String fone, String email, String data, String cep,
                      String logradouro, String bairro, String localidade, String uf, boolean membro, boolean batizado,
                      List<Relacionamento> relacionamentosPessoa1, List<Relacionamento> relacionamentosPessoa2) {
		super();
		this.id_c = id_c;
		this.nome = nome;
		this.fone = fone;
		this.email = email;
		this.data = data;
		this.cep = cep;
		this.logradouro = logradouro;
		this.bairro = bairro;
		this.localidade = localidade;
		this.uf = uf;
		this.membro = membro;
		this.batizado = batizado;
		this.relacionamentosPessoa1 = relacionamentosPessoa1;
		this.relacionamentosPessoa2 = relacionamentosPessoa2;
	}

	public Long getId_c() {
		return id_c;
	}

	public void setId_c(Long id_c) {
		this.id_c = id_c;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getFone() {
		return fone;
	}

	public void setFone(String fone) {
		this.fone = fone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getLocalidade() {
		return localidade;
	}

	public void setLocalidade(String localidade) {
		this.localidade = localidade;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public boolean isMembro() {
		return membro;
	}

	public void setMembro(boolean membro) {
		this.membro = membro;
	}

	public boolean isBatizado() {
		return batizado;
	}

	public void setBatizado(boolean batizado) {
		this.batizado = batizado;
	}

	public List<Relacionamento> getRelacionamentosPessoa1() {
		return relacionamentosPessoa1;
	}

	public void setRelacionamentosPessoa1(List<Relacionamento> relacionamentosPessoa1) {
		this.relacionamentosPessoa1 = relacionamentosPessoa1;
	}

	public List<Relacionamento> getRelacionamentosPessoa2() {
		return relacionamentosPessoa2;
	}

	public void setRelacionamentosPessoa2(List<Relacionamento> relacionamentosPessoa2) {
		this.relacionamentosPessoa2 = relacionamentosPessoa2;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id_c);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Formulario other = (Formulario) obj;
		return Objects.equals(id_c, other.id_c);
	}

	@Override
	public String toString() {
		return "FormularioModel [id_c=" + id_c + ", nome=" + nome + ", fone=" + fone + ", email=" + email + ", data="
				+ data + ", cep=" + cep + ", logradouro=" + logradouro + ", bairro=" + bairro + ", localidade="
				+ localidade + ", uf=" + uf + ", membro=" + membro + ", batizado=" + batizado
				+ ", relacionamentosPessoa1=" + relacionamentosPessoa1.stream().map(Relacionamento::getId).collect(Collectors.toList()) + ", relacionamentosPessoa2="
				+ relacionamentosPessoa2.stream().map(Relacionamento::getId).collect(Collectors.toList()) + "]";
	}
	
}
