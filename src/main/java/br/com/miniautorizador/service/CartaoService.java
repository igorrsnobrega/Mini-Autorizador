package br.com.miniautorizador.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.miniautorizador.domain.Cartao;
import br.com.miniautorizador.dto.CartaoDTO;
import br.com.miniautorizador.exception.CartaoException;
import br.com.miniautorizador.repository.CartaoRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CartaoService {

    private final CartaoRepository cartaoRepository;

    public Optional<Cartao> findCartaoExists(String numeroCartao) {
        return Optional.ofNullable(cartaoRepository.findByNumeroCartao(numeroCartao));
    }

    public CartaoDTO save(CartaoDTO cartaoDTO) {
        Optional<Cartao> hasCartao = findCartaoExists(cartaoDTO.getNumeroCartao());

        if (hasCartao.isPresent()) {
            Cartao cartao = hasCartao.get();

            CartaoDTO newCartaoDTO = new CartaoDTO();
            newCartaoDTO.setNumeroCartao(cartao.getNumeroCartao());
            newCartaoDTO.setSenhaCartao(cartao.getSenhaCartao());

            return newCartaoDTO;
        } else {
            Cartao newCartao = new Cartao();
            newCartao.setNumeroCartao(cartaoDTO.getNumeroCartao());
            newCartao.setSenhaCartao(cartaoDTO.getSenhaCartao());

            cartaoRepository.save(newCartao);
            return cartaoDTO;
        }
    }


    public BigDecimal findSaldoCartao(String numeroCartao) {
        Optional<Cartao> hasCartao = findCartaoExists(numeroCartao);
        return hasCartao.map(Cartao::getSaldoCartao).orElseThrow(() -> new CartaoException("CARTAO_INVALIDO"));
    }

}
