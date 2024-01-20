package br.com.devsibre.Dtos;

import br.com.devsibre.Model.FormularioModel;

public class SaveOrUpdateResponse {
    private FormularioModel formularioModel;
    private boolean isNewCadastro;

    public SaveOrUpdateResponse(FormularioModel formularioModel, boolean isNewCadastro) {
        this.formularioModel = formularioModel;
        this.isNewCadastro = isNewCadastro;
    }

    public FormularioModel getFormularioModel() {
        return formularioModel;
    }

    public boolean isNewCadastro() {
        return isNewCadastro;
    }
}

