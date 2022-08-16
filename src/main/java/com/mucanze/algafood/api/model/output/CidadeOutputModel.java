package com.mucanze.algafood.api.model.output;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeOutputModel {

	private Long cidadeId;
	private String nome;
	private EstadoOutputModel estado;
}
