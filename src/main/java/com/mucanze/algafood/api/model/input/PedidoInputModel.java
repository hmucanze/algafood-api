package com.mucanze.algafood.api.model.input;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoInputModel {
	
	@Valid
	@NotNull
	private RestauranteIdInput restaurante;
	
	@Valid
	@NotNull
	private FormaPagamentoIdInput formaPagamento;
	
	@Valid
	@NotNull
	private EnderecoInputModel enderecoEntrega;
	
	@Valid
	@NotNull
	@Size(min = 1)
	private List<ItemPedidoInputModel> itensPedido;

}
