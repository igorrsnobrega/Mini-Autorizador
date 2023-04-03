package br.com.miniautorizador.exception;

public class CartaoException extends RuntimeException{

    private final String errorMessage;

    public CartaoException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }
}
