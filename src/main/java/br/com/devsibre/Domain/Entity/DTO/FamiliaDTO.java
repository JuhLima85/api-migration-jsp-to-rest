package br.com.devsibre.Domain.Entity.DTO;

import br.com.devsibre.Domain.Entity.Familia;
import br.com.devsibre.Domain.Entity.Filho;
import br.com.devsibre.Domain.Entity.Formulario;

import java.util.List;
import java.util.Objects;

public class FamiliaDTO {
    private Formulario formulario;
    private Familia familia;
    private List<Filho> filhos;
    
    public FamiliaDTO() {
		
	}

	public FamiliaDTO(Formulario formulario, Familia familia, List<Filho> filhos) {
		super();
		this.formulario = formulario;
		this.familia = familia;
		this.filhos = filhos;
	}

	public Formulario getFormulario() {
		return formulario;
	}

	public void setFormulario(Formulario formulario) {
		this.formulario = formulario;
	}

	public Familia getFamilia() {
		return familia;
	}

	public void setFamilia(Familia familia) {
		this.familia = familia;
	}

	public List<Filho> getFilhos() {
		return filhos;
	}

	public void setFilhos(List<Filho> filhos) {
		this.filhos = filhos;
	}

	@Override
	public int hashCode() {
		return Objects.hash(familia, filhos, formulario);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FamiliaDTO other = (FamiliaDTO) obj;
		return Objects.equals(familia, other.familia) && Objects.equals(filhos, other.filhos)
				&& Objects.equals(formulario, other.formulario);
	}

	@Override
	public String toString() {
	    return "FamiliaDTO{" +
	            "nome='"  + '\'' +
	            ", quantidadeFilhos=" +
	            '}';
	}

}

