package br.com.miniautorizador.controller;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.miniautorizador.domain.Cartao;
import br.com.miniautorizador.dto.CartaoDTO;
import br.com.miniautorizador.exception.CartaoException;
import br.com.miniautorizador.service.CartaoService;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CartaoControllerTest {

    @Mock
    private CartaoService cartaoService;

    @InjectMocks
    private CartaoController cartaoController;

    @Test
    public void testSaveCartaoWithExistingCartao() {

        CartaoDTO cartaoDTO = new CartaoDTO();
        cartaoDTO.setNumeroCartao("1234567890123456");
        when(cartaoService.findCartaoExists(cartaoDTO.getNumeroCartao())).thenReturn(Optional.of(new Cartao()));

        ResponseEntity<CartaoDTO> response = cartaoController.saveCartao(cartaoDTO);

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
    }

    @Test
    public void testSaveCartaoWithNonExistingCartao() {

        CartaoDTO cartaoDTO = new CartaoDTO();
        cartaoDTO.setNumeroCartao("1234567890123456");
        when(cartaoService.findCartaoExists(cartaoDTO.getNumeroCartao())).thenReturn(Optional.empty());
        when(cartaoService.save(cartaoDTO)).thenReturn(cartaoDTO);

        ResponseEntity<CartaoDTO> response = cartaoController.saveCartao(cartaoDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(cartaoDTO, response.getBody());
    }

    @Test
    public void testFindSaldoCartaoWithExistingCartao() {

        String numeroCartao = "1234567890123456";
        BigDecimal saldo = BigDecimal.TEN;
        when(cartaoService.findSaldoCartao(numeroCartao)).thenReturn(saldo);

        ResponseEntity<BigDecimal> response = cartaoController.findSaldoCartao(numeroCartao);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(saldo, response.getBody());
    }

    @Test
    public void testFindSaldoCartaoWithNonExistingCartao() {

        String numeroCartao = "1234567890123456";
        when(cartaoService.findSaldoCartao(numeroCartao)).thenThrow(new CartaoException("CARTAO_INVALIDO"));

        assertThrows(CartaoException.class, () -> {
            cartaoService.findSaldoCartao(numeroCartao);
});
    }

}

