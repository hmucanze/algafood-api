package com.mucanze.algafood.domain.exception;

public class PermissaoInexistenteException extends EntidadeInexistenteException {

	private static final long serialVersionUID = 1L;
	
	public PermissaoInexistenteException(String mensagem) {
		super(mensagem);
	}
	
	public PermissaoInexistenteException(Long permissaoId) {
		this(String.format("Não existe registro de permissão com o identificador %d no restaurante de identificador %d.",
				permissaoId));
	}

}
