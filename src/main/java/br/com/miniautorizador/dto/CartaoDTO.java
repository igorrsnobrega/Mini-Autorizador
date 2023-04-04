package br.com.miniautorizador.dto;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CartaoDTO {

    @NotNull(message = "O número do cartão é obrigatório.")
    private String numeroCartao;
    @NotNull(message = "A senha é obrigatória.")
    private String senha;
}
