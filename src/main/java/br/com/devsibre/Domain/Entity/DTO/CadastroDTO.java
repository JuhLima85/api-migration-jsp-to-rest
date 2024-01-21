package br.com.devsibre.Domain.Entity.DTO;

public class CadastroDTO {
	
	private Long idPessoa;
	private String pessoaNome;
	private String pessoaFone;
	private String pessoaEmail;
	private String pessoaData;
	private String pessoaCep;
	private String pessoaLogradouro;
	private String pessoaBairro;
	private String pessoaLocalidade;
	private String pessoaUf;
	private boolean pessoaMembro;
	
	public CadastroDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CadastroDTO(Long idPessoa, String pessoaNome, String pessoaFone, String pessoaEmail, String pessoaData,
			String pessoaCep, String pessoaLogradouro, String pessoaBairro, String pessoaLocalidade, String pessoaUf,
			boolean pessoaMembro) {
		super();
		this.idPessoa = idPessoa;
		this.pessoaNome = pessoaNome;
		this.pessoaFone = pessoaFone;
		this.pessoaEmail = pessoaEmail;
		this.pessoaData = pessoaData;
		this.pessoaCep = pessoaCep;
		this.pessoaLogradouro = pessoaLogradouro;
		this.pessoaBairro = pessoaBairro;
		this.pessoaLocalidade = pessoaLocalidade;
		this.pessoaUf = pessoaUf;
		this.pessoaMembro = pessoaMembro;
	}	

	public Long getIdPessoa() {
		return idPessoa;
	}

	public void setIdPessoa(Long idPessoa) {
		this.idPessoa = idPessoa;
	}

	public String getPessoaNome() {
		return pessoaNome;
	}

	public void setPessoaNome(String pessoaNome) {
		this.pessoaNome = pessoaNome;
	}

	public String getPessoaFone() {
		return pessoaFone;
	}

	public void setPessoaFone(String pessoaFone) {
		this.pessoaFone = pessoaFone;
	}

	public String getPessoaEmail() {
		return pessoaEmail;
	}

	public void setPessoaEmail(String pessoaEmail) {
		this.pessoaEmail = pessoaEmail;
	}

	public String getPessoaData() {
		return pessoaData;
	}

	public void setPessoaData(String pessoaData) {
		this.pessoaData = pessoaData;
	}

	public String getPessoaCep() {
		return pessoaCep;
	}

	public void setPessoaCep(String pessoaCep) {
		this.pessoaCep = pessoaCep;
	}

	public String getPessoaLogradouro() {
		return pessoaLogradouro;
	}

	public void setPessoaLogradouro(String pessoaLogradouro) {
		this.pessoaLogradouro = pessoaLogradouro;
	}

	public String getPessoaBairro() {
		return pessoaBairro;
	}

	public void setPessoaBairro(String pessoaBairro) {
		this.pessoaBairro = pessoaBairro;
	}

	public String getPessoaLocalidade() {
		return pessoaLocalidade;
	}

	public void setPessoaLocalidade(String pessoaLocalidade) {
		this.pessoaLocalidade = pessoaLocalidade;
	}

	public String getPessoaUf() {
		return pessoaUf;
	}

	public void setPessoaUf(String pessoaUf) {
		this.pessoaUf = pessoaUf;
	}

	public boolean isPessoaMembro() {
		return pessoaMembro;
	}

	public void setPessoaMembro(boolean pessoaMembro) {
		this.pessoaMembro = pessoaMembro;
	}

}
