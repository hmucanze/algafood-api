package com.mucanze.algafood.domain.exception;

public class FotoProdutoInexistenteException extends EntidadeInexistenteException {

	private static final long serialVersionUID = 1L;
	
	public FotoProdutoInexistenteException(String mensagem) {
		super(mensagem);
	}
	
	public FotoProdutoInexistenteException(Long restauranteId, Long produtoId) {
		this(String.format(
				"Não existe registro de foto de produto com o identificador %d no restaurante de identificador %d.",
				produtoId, restauranteId));
	}

}
