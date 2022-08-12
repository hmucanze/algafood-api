package com.mucanze.algafood.domain.exception;

public class RestauranteInexistenteException extends EntidadeInexistenteException {

	private static final long serialVersionUID = 1L;

	public RestauranteInexistenteException(String mensagem) {
		super(mensagem);
	}
	
	public RestauranteInexistenteException(Long restauranteId) {
		this(String.format("Não existe registro de restaurante com o identificador %d.", restauranteId));
	}

}
