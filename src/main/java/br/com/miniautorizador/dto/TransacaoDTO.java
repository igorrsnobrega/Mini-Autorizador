package br.com.miniautorizador.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransacaoDTO {

    @NotNull(message = "O número do cartão é obrigatório.")
    private String numeroCartao;
    @NotNull(message = "A senha é obrigatória.")
    private String senhaCartao;
    @NotNull(message = "O valor é obrigatório.")
    private BigDecimal valor;

}
