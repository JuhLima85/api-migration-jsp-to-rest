package br.com.devsibre.Domain.Entity.DTO;

import br.com.devsibre.Domain.Entity.Formulario;

public class SaveOrUpdateResponse {
    private Formulario formulario;
    private boolean isNewCadastro;

    public SaveOrUpdateResponse(Formulario formulario, boolean isNewCadastro) {
        this.formulario= formulario;
        this.isNewCadastro = isNewCadastro;
    }

    public Formulario getFormulario() {
        return formulario;
    }

    public boolean isNewCadastro() {
        return isNewCadastro;
    }
}

