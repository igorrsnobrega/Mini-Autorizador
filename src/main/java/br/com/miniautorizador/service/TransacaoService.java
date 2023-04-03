package br.com.miniautorizador.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.miniautorizador.domain.Cartao;
import br.com.miniautorizador.domain.Transacao;
import br.com.miniautorizador.dto.TransacaoDTO;
import br.com.miniautorizador.exception.CartaoInvalidoException;
import br.com.miniautorizador.exception.SaldoInsuficienteException;
import br.com.miniautorizador.exception.SenhaInvalidaException;
import br.com.miniautorizador.exception.TransacaoException;
import br.com.miniautorizador.exception.ValorIncorretoException;
import br.com.miniautorizador.repository.TransacaoRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;

    private final CartaoService cartaoService;


    private void validTransacao(TransacaoDTO transacaoDTO)  {
        Cartao cartao = cartaoService.findCartaoExists(transacaoDTO.getNumeroCartao())
                .orElseThrow(CartaoInvalidoException::new);

        if (!cartao.getSenhaCartao().equals(transacaoDTO.getSenhaCartao())) {
            throw new SenhaInvalidaException();
        }

        BigDecimal valor = transacaoDTO.getValor();
        BigDecimal saldo = cartao.getSaldoCartao();

        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValorIncorretoException();
        }

        if (saldo.compareTo(BigDecimal.ZERO) <= 0) {
            throw new SaldoInsuficienteException();
        }

        if (valor.compareTo(saldo) > 0) {
            throw new SaldoInsuficienteException();
        }

        cartao.setSaldoCartao(saldo.subtract(valor));
    }



    public String doTransacao(TransacaoDTO transacaoDTO) throws TransacaoException {

        validTransacao(transacaoDTO);

        Transacao transacao = new Transacao();

        Optional<Cartao> cartao = cartaoService.findCartaoExists(transacaoDTO.getNumeroCartao());

        transacao.setCartao(cartao.get());
        transacao.setValor(transacaoDTO.getValor());

        transacaoRepository.save(transacao);

        return "OK";
    }

}
