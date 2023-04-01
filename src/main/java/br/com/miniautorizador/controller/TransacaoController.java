package br.com.miniautorizador.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.miniautorizador.domain.Transacao;
import br.com.miniautorizador.dto.TransacaoDTO;
import br.com.miniautorizador.service.TransacaoService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

    private final TransacaoService transacaoService;

    @PostMapping
    public ResponseEntity<Transacao> doTransacao(@RequestBody TransacaoDTO transacaoDTO) {

        return new ResponseEntity<>(transacaoService.doTransacao(transacaoDTO), HttpStatus.OK);

    }
}
