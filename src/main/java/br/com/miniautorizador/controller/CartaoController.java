package br.com.miniautorizador.controller;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.miniautorizador.domain.Cartao;
import br.com.miniautorizador.dto.CartaoDTO;
import br.com.miniautorizador.service.CartaoService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/cartoes")
public class CartaoController {

    private final CartaoService cartaoService;

    @PostMapping
    public ResponseEntity<Cartao> saveCartao(@RequestBody CartaoDTO cartaoDTO) {

        Optional<Cartao> hasCartao = cartaoService.beforeSave(cartaoDTO.getNumeroCartao());

        return hasCartao.map(cartao -> new ResponseEntity<>(cartao, HttpStatus.UNPROCESSABLE_ENTITY))
                .orElseGet(() -> new ResponseEntity<>(cartaoService.save(cartaoDTO), HttpStatus.CREATED));

    }

    @GetMapping("/{numeroCartao}")
    public ResponseEntity<BigDecimal> findSaldoCartao(@PathVariable String numeroCartao) {

        Optional<Cartao> hasCartao = cartaoService.beforeSave(numeroCartao);

        return hasCartao.map(cartao -> new ResponseEntity<>(cartaoService.findSaldoCartao(numeroCartao),HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
