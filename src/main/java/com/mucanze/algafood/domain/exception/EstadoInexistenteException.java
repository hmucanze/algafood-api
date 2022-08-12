package com.mucanze.algafood.domain.exception;

public class EstadoInexistenteException extends EntidadeInexistenteException {

	private static final long serialVersionUID = 1L;
	
	public EstadoInexistenteException(String mensagem) {
		super(mensagem);
	}
	
	public EstadoInexistenteException(Long estadoId) {
		this(String.format("Não existe registro de estado com o identificador %d.", estadoId));
	}

}
