package br.com.miniautorizador.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(TransacaoException.class)
    public ResponseEntity<String> handleCustomException(TransacaoException ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("TRANSACAO_INVALIDA");
    }

    @ExceptionHandler(CartaoInvalidoException.class)
    public ResponseEntity<String> handleCustomException(CartaoInvalidoException ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("CARTAO_INVALIDO");
    }

    @ExceptionHandler(SenhaInvalidaException.class)
    public ResponseEntity<String> handleCustomException(SenhaInvalidaException ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("SENHA_INVALIDA");
    }

    @ExceptionHandler(ValorIncorretoException.class)
    public ResponseEntity<String> handleCustomException(ValorIncorretoException ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("SALDO_INSUFICIENTE");
    }

    @ExceptionHandler(SaldoInsuficienteException.class)
    public ResponseEntity<String> handleCustomException(SaldoInsuficienteException ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("SALDO_INSUFICIENTE");
    }
}

