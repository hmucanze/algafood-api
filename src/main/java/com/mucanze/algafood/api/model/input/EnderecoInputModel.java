package com.mucanze.algafood.api.model.input;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnderecoInputModel {
	
	@NotNull
	private String cep;
	
	@NotNull
	private String logradouro;
	
	@NotNull
	private String numero;
	private String complemento;
	
	@NotNull
	private String bairro;
	
	@Valid
	@NotNull
	private CidadeIdInput cidade;

}
