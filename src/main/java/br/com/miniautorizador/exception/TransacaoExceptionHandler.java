package br.com.miniautorizador.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TransacaoExceptionHandler {
    @ExceptionHandler(TransacaoException.class)
    public ResponseEntity<String> handleCustomException(TransacaoException ex) {
        String errorResponse = ex.getMessage();
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errorResponse);
    }
}
