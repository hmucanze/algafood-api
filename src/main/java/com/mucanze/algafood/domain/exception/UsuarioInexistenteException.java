package com.mucanze.algafood.domain.exception;

public class UsuarioInexistenteException extends EntidadeInexistenteException {

	private static final long serialVersionUID = 1L;
	
	public UsuarioInexistenteException(String mensagem) {
		super(mensagem);
	}
	
	public UsuarioInexistenteException(Long usuarioId) {
		this(String.format("Não existe registro de usuário com o identificador %d.", usuarioId));
	}

}
