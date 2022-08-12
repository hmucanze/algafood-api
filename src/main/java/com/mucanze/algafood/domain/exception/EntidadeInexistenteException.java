package com.mucanze.algafood.domain.exception;

public abstract class EntidadeInexistenteException extends NegocioException {

	private static final long serialVersionUID = 1L;
	
	public EntidadeInexistenteException(String message) {
		super(message);
	}

}
