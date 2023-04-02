package br.com.miniautorizador.exception;

public class TransacaoException extends RuntimeException {

    private final String errorMessage;

    public TransacaoException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

}
