package com.mucanze.algafood.api.model.output;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.mucanze.algafood.domain.model.StatusPedido;

import lombok.Getter;
import lombok.Setter;

@JsonFilter("pedidoFilter")
@Getter
@Setter
public class PedidoOutputResumoModel {

	private String codigo;
	private BigDecimal subtotal;
	private BigDecimal taxaFrete;
	private BigDecimal valorTotal;
	private OffsetDateTime dataCriacao;
	private StatusPedido statusPedido;
	private RestauranteOutputResumoModel restaurante;
	private UsuarioOutputModel cliente;

}
