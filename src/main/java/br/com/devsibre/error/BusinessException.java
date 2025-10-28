package br.com.devsibre.error;

public class BusinessException extends RuntimeException {
    public BusinessException(String msg) { super(msg); }
}
