package com.mucanze.algafood.api.model.input;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormaPagamentoInputModel {
	
	@NotBlank
	private String descricao;

}
