package com.mucanze.algafood.api.model.output;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteOutputModel {
	
	private Long idRestaurante;
	private String nomeRestaurante;
	private BigDecimal frete;
	private Boolean activo;
	private Boolean aberto;
	private CozinhaOutputModel cozinha;
	private EnderecoOutputModel endereco;

}
