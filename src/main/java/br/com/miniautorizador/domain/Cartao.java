package br.com.miniautorizador.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(of = "id")
@Table(name = "cartao")
public class Cartao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "Numero cartao e obrigatorio")
    private String numeroCartao;
    @NotNull(message = "A senha e obrigatoria")
    private String senhaCartao;
    private BigDecimal saldoCartao = BigDecimal.valueOf(500);

}