package com.mucanze.algafood.domain.exception;

public class GrupoInexistenteException extends EntidadeInexistenteException {

	private static final long serialVersionUID = 1L;
	
	public GrupoInexistenteException(String mensagem) {
		super(mensagem);
	}
	
	public GrupoInexistenteException(Long grupoId) {
		this(String.format("Não existe registo de grupo com o id %d.", grupoId));
	}

}
