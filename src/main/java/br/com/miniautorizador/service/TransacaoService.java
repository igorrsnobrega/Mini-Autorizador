package br.com.miniautorizador.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import br.com.miniautorizador.domain.Cartao;
import br.com.miniautorizador.domain.Transacao;
import br.com.miniautorizador.dto.TransacaoDTO;
import br.com.miniautorizador.repository.TransacaoRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;

    private final CartaoService cartaoService;


    private void validTransacao(TransacaoDTO transacaoDTO) {
        Optional<Cartao> cartaoOptional = cartaoService.findByNumeroCartao(transacaoDTO.getNumeroCartao());

        if (cartaoOptional.isEmpty()) {
            throw new IllegalArgumentException("CARTAO_INVALIDO");
        }

        Cartao cartao = cartaoOptional.get();

        if (!cartao.getSenhaCartao().equals(transacaoDTO.getSenhaCartao())) {
            throw new IllegalArgumentException("SENHA_INVALIDA");
        }

        BigDecimal valor = transacaoDTO.getValor();
        BigDecimal saldo = cartao.getSaldoCartao();

        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("VALOR_INCORRETO");
        }

        if (saldo.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("SALDO_INSUFICIENTE");
        }

        if (valor.compareTo(saldo) > 0) {
            throw new IllegalArgumentException("SALDO_INSUFICIENTE");
        }

        cartao.setSaldoCartao(saldo.subtract(valor));
    }


    public Transacao doTransacao(TransacaoDTO transacaoDTO) {

        validTransacao(transacaoDTO);

        Transacao transacao = new Transacao();

        Optional<Cartao> cartao = cartaoService.findByNumeroCartao(transacaoDTO.getNumeroCartao());

        transacao.setCartao(cartao.get());

        BeanUtils.copyProperties(transacaoDTO, transacao);

        return  transacaoRepository.save(transacao);


    }

}
