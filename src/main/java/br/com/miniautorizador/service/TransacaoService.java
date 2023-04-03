package br.com.miniautorizador.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import br.com.miniautorizador.domain.Cartao;
import br.com.miniautorizador.domain.Transacao;
import br.com.miniautorizador.dto.TransacaoDTO;
import br.com.miniautorizador.exception.TransacaoException;
import br.com.miniautorizador.repository.TransacaoRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;

    private final CartaoService cartaoService;


    private void validTransacao(TransacaoDTO transacaoDTO) throws TransacaoException {
        Cartao cartao = cartaoService.findCartaoExists(transacaoDTO.getNumeroCartao())
                .orElseThrow(() -> new TransacaoException("CARTAO_INVALIDO"));

        if (!cartao.getSenhaCartao().equals(transacaoDTO.getSenhaCartao())) {
            throw new TransacaoException("SENHA_INVALIDA");
        }

        BigDecimal valor = transacaoDTO.getValor();
        BigDecimal saldo = cartao.getSaldoCartao();

        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new TransacaoException("VALOR_INCORRETO");
        }

        if (saldo.compareTo(BigDecimal.ZERO) <= 0) {
            throw new TransacaoException("SALDO_INSUFICIENTE");
        }

        if (valor.compareTo(saldo) > 0) {
            throw new TransacaoException("SALDO_INSUFICIENTE");
        }

        cartao.setSaldoCartao(saldo.subtract(valor));
    }



    public String doTransacao(TransacaoDTO transacaoDTO) throws TransacaoException {

        validTransacao(transacaoDTO);

        Transacao transacao = new Transacao();

        Optional<Cartao> cartao = cartaoService.findCartaoExists(transacaoDTO.getNumeroCartao());

        transacao.setCartao(cartao.get());

        BeanUtils.copyProperties(transacaoDTO, transacao);

        transacaoRepository.save(transacao);

        return "OK";
    }

}
