package br.com.devsibre.error;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String msg) { super(msg); }

    public static NotFoundException dadoNaoEncontrado() {
        return new NotFoundException("Cadastro não encontrado");
    }
}
