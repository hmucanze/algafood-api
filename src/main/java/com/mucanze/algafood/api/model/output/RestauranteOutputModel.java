package com.mucanze.algafood.api.model.output;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonView;
import com.mucanze.algafood.api.model.output.view.RestauranteView;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteOutputModel {
	
	@JsonView(RestauranteView.Resumo.class)
	private Long idRestaurante;
	
	@JsonView(RestauranteView.Resumo.class)
	private String nomeRestaurante;
	private BigDecimal frete;
	private Boolean activo;
	private Boolean aberto;
	private CozinhaOutputModel cozinha;
	private EnderecoOutputModel endereco;

}
