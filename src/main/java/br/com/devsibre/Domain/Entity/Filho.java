package br.com.devsibre.Domain.Entity;

import br.com.devsibre.Enuns.StatusEnum;

import javax.persistence.*;

@Entity
@Table(name = "filhos")
public class Filho {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nome;
    private String telefone;
    private String email;
    private String dataNascProl;
    @Enumerated(EnumType.STRING)
    private StatusEnum seBatizado;
    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    public Filho() {
		// TODO Auto-generated constructor stub
	}

	public Filho(Long id, String nome, String telefone, String email, String dataNascProl, StatusEnum seBatizado,
				 StatusEnum status) {
		super();
		this.id = id;
		this.nome = nome;
		this.telefone = telefone;
		this.email = email;
		this.dataNascProl = dataNascProl;
		this.seBatizado = seBatizado;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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

	public String getDataNascProl() {
		return dataNascProl;
	}

	public void setDataNascProl(String dataNascProl) {
		this.dataNascProl = dataNascProl;
	}

	public StatusEnum getSeBatizado() {
		return seBatizado;
	}

	public void setSeBatizado(StatusEnum seBatizado) {
		this.seBatizado = seBatizado;
	}

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}


}
