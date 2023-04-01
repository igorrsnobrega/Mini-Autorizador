package br.com.miniautorizador.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransacaoDTO {

    private String numeroCartao;
    private String senhaCartao;
    private BigDecimal valor;

}
