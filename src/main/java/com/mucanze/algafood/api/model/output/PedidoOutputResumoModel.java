package com.mucanze.algafood.api.model.output;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import com.mucanze.algafood.domain.model.StatusPedido;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoOutputResumoModel {
	
	private Long pedidoId;
	private BigDecimal subtotal;
	private BigDecimal taxaFrete;
	private BigDecimal valorTotal;
	private OffsetDateTime dataCriacao;
	private StatusPedido statusPedido;
	private RestauranteOutputResumoModel restaurante;
	private UsuarioOutputModel cliente;

}
