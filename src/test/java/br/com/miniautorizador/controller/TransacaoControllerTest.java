package br.com.miniautorizador.controller;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.miniautorizador.dto.TransacaoDTO;
import br.com.miniautorizador.service.TransacaoService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransacaoControllerTest {

    @InjectMocks
    private TransacaoController transacaoController;

    @Mock
    private TransacaoService transacaoService;

    @Test
    public void testDoTransacao() {

        TransacaoDTO transacaoDTO = new TransacaoDTO();
        transacaoDTO.setValor(BigDecimal.valueOf(50.0));
        transacaoDTO.setNumeroCartao("1234567890123456");
        transacaoDTO.setSenhaCartao("1234");

        when(transacaoService.doTransacao(any())).thenReturn("OK");

        ResponseEntity<String> response = transacaoController.doTransacao(transacaoDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("OK", response.getBody());
    }

}
