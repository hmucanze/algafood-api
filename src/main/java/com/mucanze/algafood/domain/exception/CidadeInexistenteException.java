package com.mucanze.algafood.domain.exception;

public class CidadeInexistenteException extends EntidadeInexistenteException {

	private static final long serialVersionUID = 1L;

	public CidadeInexistenteException(String mensagem) {
		super(mensagem);
	}
	
	public CidadeInexistenteException(Long cidadeId) {
		this(String.format("Não existe registro de cidade com o identificador %d.", cidadeId));
	}

}
