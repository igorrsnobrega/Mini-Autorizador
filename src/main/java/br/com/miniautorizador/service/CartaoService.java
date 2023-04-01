package br.com.miniautorizador.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import br.com.miniautorizador.domain.Cartao;
import br.com.miniautorizador.dto.CartaoDTO;
import br.com.miniautorizador.repository.CartaoRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CartaoService {

    private final CartaoRepository cartaoRepository;

    public Optional<Cartao> beforeSave(String numeroCartao) {
        return cartaoRepository.findByNumeroCartao(numeroCartao);
    }

    public Cartao save(CartaoDTO cartaoDTO) {

        Cartao newCartao = new Cartao();
        BeanUtils.copyProperties(cartaoDTO, newCartao);

        return cartaoRepository.save(newCartao);
    }

    public Optional<Cartao> findByNumeroCartao(String numeroCartao) {
        return cartaoRepository.findByNumeroCartao(numeroCartao);
    }

    public BigDecimal findSaldoCartao(String numeroCartao) {
        Optional<Cartao> hasCartao = cartaoRepository.findByNumeroCartao(numeroCartao);
        return hasCartao.get().getSaldoCartao();
    }

}
