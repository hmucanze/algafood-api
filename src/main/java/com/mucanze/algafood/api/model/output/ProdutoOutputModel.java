package com.mucanze.algafood.api.model.output;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoOutputModel {
	
	private Long produtoId;
	private String nome;
	private String descricao;
	private BigDecimal preco;
	private Boolean activo;

}
