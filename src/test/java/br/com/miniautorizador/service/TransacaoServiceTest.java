package br.com.miniautorizador.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.miniautorizador.domain.Cartao;
import br.com.miniautorizador.dto.TransacaoDTO;
import br.com.miniautorizador.exception.CartaoInvalidoException;
import br.com.miniautorizador.exception.SaldoInsuficienteException;
import br.com.miniautorizador.exception.SenhaInvalidaException;
import br.com.miniautorizador.exception.TransacaoException;
import br.com.miniautorizador.exception.ValorIncorretoException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransacaoServiceTest {

    @Mock
    private CartaoService cartaoService;

    @InjectMocks
    private TransacaoService transacaoService;

    private TransacaoDTO transacaoDTO;

    private Cartao cartao;

    @Before
    public void setUp() {
        transacaoDTO = new TransacaoDTO();
        transacaoDTO.setNumeroCartao("1234567890123456");
        transacaoDTO.setSenhaCartao("1234");
        transacaoDTO.setValor(new BigDecimal("50.00"));

        cartao = new Cartao();
        cartao.setNumeroCartao(transacaoDTO.getNumeroCartao());
        cartao.setSenha(transacaoDTO.getSenhaCartao());
        cartao.setSaldoCartao(new BigDecimal("100.00"));
    }

    @Test(expected = CartaoInvalidoException.class)
    public void testDoTransacaoComCartaoInvalido() throws TransacaoException {

        when(cartaoService.findCartaoExists(anyString())).thenReturn(Optional.empty());

        transacaoService.doTransacao(transacaoDTO);

        verify(cartaoService, times(1)).findCartaoExists(anyString());

        verify(transacaoService, times(0)).doTransacao(transacaoDTO);
    }

    @Test(expected = SenhaInvalidaException.class)
    public void testDoTransacaoComSenhaInvalida() throws TransacaoException {

        transacaoDTO.setSenhaCartao("senha errada");
        when(cartaoService.findCartaoExists(anyString())).thenReturn(Optional.of(cartao));

        transacaoService.doTransacao(transacaoDTO);

        verify(cartaoService, times(1)).findCartaoExists(anyString());
        verify(transacaoService, times(0)).doTransacao(transacaoDTO);
    }

    @Test(expected = ValorIncorretoException.class)
    public void testDoTransacaoComValorIncorreto() throws TransacaoException {

        transacaoDTO.setValor(new BigDecimal("0.00"));
        when(cartaoService.findCartaoExists(anyString())).thenReturn(Optional.of(cartao));

        transacaoService.doTransacao(transacaoDTO);

        verify(cartaoService, times(1)).findCartaoExists(anyString());
        verify(transacaoService, times(0)).doTransacao(transacaoDTO);
    }

    @Test(expected = SaldoInsuficienteException.class)
    public void testDoTransacaoComSaldoInsuficiente() throws TransacaoException {

        transacaoDTO.setValor(new BigDecimal("150.00"));
        when(cartaoService.findCartaoExists(anyString())).thenReturn(Optional.of(cartao));

        transacaoService.doTransacao(transacaoDTO);

        verify(cartaoService, times(2)).findCartaoExists(anyString());
        verify(transacaoService, times(0)).doTransacao(transacaoDTO);
    }
}

