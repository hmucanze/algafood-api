package com.mucanze.algafood.domain.exception;

public class CozinhaInexistenteException extends EntidadeInexistenteException {

	private static final long serialVersionUID = 1L;

	public CozinhaInexistenteException(String mensagem) {
		super(mensagem);
	}
	
	public CozinhaInexistenteException(Long id) {
		this(String.format("Não existe registro de cozinha com o identificador %d.", id));
	}

}
