package br.com.miniautorizador.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.miniautorizador.domain.Cartao;
import br.com.miniautorizador.dto.CartaoDTO;
import br.com.miniautorizador.exception.CartaoInvalidoException;
import br.com.miniautorizador.repository.CartaoRepository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CartaoServiceTest {

    @Mock
    private CartaoRepository cartaoRepository;

    @InjectMocks
    private CartaoService cartaoService;

    @Test
    public void testFindCartaoExists() {

        Cartao cartao = new Cartao();
        cartao.setNumeroCartao("1234567890123456");

        when(cartaoRepository.findByNumeroCartao(any())).thenReturn(cartao);

        Optional<Cartao> result = cartaoService.findCartaoExists("1234567890123456");

        assertTrue(result.isPresent());
        assertEquals(cartao, result.get());
    }

    @Test
    public void testFindCartaoNotExists() {

        when(cartaoRepository.findByNumeroCartao(any())).thenReturn(null);

        Optional<Cartao> result = cartaoService.findCartaoExists("1234567890123456");

        assertFalse(result.isPresent());
    }

    @Test
    public void testSaveCartao() {

        CartaoDTO cartaoDTO = new CartaoDTO();
        cartaoDTO.setNumeroCartao("1234567890123456");
        cartaoDTO.setSenha("1234");

        Cartao cartao = new Cartao();
        cartao.setNumeroCartao("1234567890123456");
        cartao.setSenha("1234");

        when(cartaoRepository.findByNumeroCartao(any())).thenReturn(cartao);

        CartaoDTO result = cartaoService.save(cartaoDTO);

        assertNotNull(result);
        assertEquals("1234567890123456", result.getNumeroCartao());
        assertEquals("1234", result.getSenha());
    }

    @Test
    public void testSaveCartaoNotExists() {

        CartaoDTO cartaoDTO = new CartaoDTO();
        cartaoDTO.setNumeroCartao("1234567890123456");
        cartaoDTO.setSenha("1234");

        Cartao cartao = new Cartao();
        cartao.setSenha(cartaoDTO.getSenha());
        cartao.setNumeroCartao(cartaoDTO.getNumeroCartao());

        when(cartaoRepository.findByNumeroCartao(any())).thenReturn(null);

        CartaoDTO result = cartaoService.save(cartaoDTO);

        assertNotNull(result);
        assertEquals("1234567890123456", result.getNumeroCartao());
        assertEquals("1234", result.getSenha());

        verify(cartaoRepository).save(cartao);
    }

    @Test
    public void testFindSaldoCartaoExists() {

        Cartao cartao = new Cartao();
        cartao.setNumeroCartao("1234567890123456");
        cartao.setSaldoCartao(BigDecimal.valueOf(100.00));

        when(cartaoRepository.findByNumeroCartao(any())).thenReturn(cartao);

        BigDecimal result = cartaoService.findSaldoCartao("1234567890123456");

        assertNotNull(result);
        assertEquals(cartao.getSaldoCartao(), result);
    }

    @Test
    public void testFindSaldoCartaoNotExists() {

        when(cartaoRepository.findByNumeroCartao(any())).thenReturn(null);

        assertThrows(CartaoInvalidoException.class, () -> {
            cartaoService.findSaldoCartao("1234567890123456");
        });
    }

}

