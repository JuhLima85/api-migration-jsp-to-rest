package br.com.devsibre.Domain.Entity.DTO;

public class RelacionamentoDTO {
	private Long id;
	private String nomeFamiliar;
	private String grauParentesco;

	public RelacionamentoDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RelacionamentoDTO(Long id, String nomeFamiliar, String grauParentesco) {
		super();
		this.id = id;
		this.nomeFamiliar = nomeFamiliar;
		this.grauParentesco = grauParentesco;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeFamiliar() {
		return nomeFamiliar;
	}

	public void setNomeFamiliar(String nomeFamiliar) {
		this.nomeFamiliar = nomeFamiliar;
	}

	public String getGrauParentesco() {
		return grauParentesco;
	}

	public void setGrauParentesco(String grauParentesco) {
		this.grauParentesco = grauParentesco;
	}

}
