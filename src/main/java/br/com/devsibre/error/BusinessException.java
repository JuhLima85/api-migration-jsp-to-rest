package br.com.devsibre.error;

public class BusinessException extends RuntimeException {
    public BusinessException(String msg) { super(msg); }

    public static BusinessException dadoDuplicado() {
        return new BusinessException("Já existe um cadastro com este telefone");
    }

    public static BusinessException relacionamentoDuplicado() {
        return new BusinessException("Esse vínculo já existe.");
    }
}
