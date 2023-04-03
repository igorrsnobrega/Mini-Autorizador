package br.com.miniautorizador.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.miniautorizador.domain.Cartao;
import br.com.miniautorizador.dto.CartaoDTO;
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

            hasCartao.map(cartao -> {

                CartaoDTO newCartaoDTO = new CartaoDTO();

                newCartaoDTO.setNumeroCartao(cartao.getNumeroCartao());
                newCartaoDTO.setSenhaCartao(cartao.getSenhaCartao());

                return newCartaoDTO;
            });

        } else {

            Cartao newCartao = new Cartao();

            newCartao.setNumeroCartao(cartaoDTO.getNumeroCartao());
            newCartao.setSenhaCartao(cartaoDTO.getSenhaCartao());

            cartaoRepository.save(newCartao);

        }

        return cartaoDTO;


    }

//    public CartaoDTO findByNumeroCartao(String numeroCartao) {
//
//        Cartao cartao = cartaoRepository.findByNumeroCartao(numeroCartao);
//
//        CartaoDTO cartaoDTO = new CartaoDTO();
//
//        BeanUtils.copyProperties(cartao, cartaoDTO);
//
//        return cartaoDTO;
//    }

    public BigDecimal findSaldoCartao(String numeroCartao) {
        Cartao cartao = cartaoRepository.findByNumeroCartao(numeroCartao);
        return cartao.getSaldoCartao();
    }

}
