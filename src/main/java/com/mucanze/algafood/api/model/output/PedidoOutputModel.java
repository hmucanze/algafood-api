package com.mucanze.algafood.api.model.output;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import com.mucanze.algafood.domain.model.StatusPedido;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoOutputModel {
	
	private String codigo;
	private BigDecimal subtotal;
	private BigDecimal taxaFrete;
	private BigDecimal valorTotal;
	private OffsetDateTime dataCriacao;
	private OffsetDateTime dataConfirmacao;
	private OffsetDateTime dataCancelamento;
	private OffsetDateTime dataEntrega;
	private StatusPedido statusPedido;
	private EnderecoOutputModel enderecoEntrega;
	private FormaPagamentoOutputModel formaPagamento;
	private RestauranteOutputResumoModel restaurante;
	private UsuarioOutputModel cliente;
	private List<ItemPedidoOutputModel> itensPedido;

}
