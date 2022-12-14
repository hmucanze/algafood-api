package com.mucanze.algafood.api.model.output;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class EnderecoOutputModel {

	private String cep;
	private String logradouro;
	private String numero;
	private String complemento;
	private String bairro;
	private CidadeOutputResumoModel cidade;

}
