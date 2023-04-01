package br.com.miniautorizador.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CartaoDTO {

    @JsonIgnore
    private Long id;
    private String numeroCartao;
    private String senhaCartao;
    @JsonIgnore
    private BigDecimal saldoCartao;
}
