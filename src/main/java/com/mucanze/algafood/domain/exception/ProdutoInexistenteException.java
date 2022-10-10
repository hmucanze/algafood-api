package com.mucanze.algafood.domain.exception;

public class ProdutoInexistenteException extends EntidadeInexistenteException {

	private static final long serialVersionUID = 1L;
	
	public ProdutoInexistenteException(String mensagem) {
		super(mensagem);
	}
	
	public ProdutoInexistenteException(Long restauranteId, Long produtoId) {
		this(String.format(
				"Não existe registro de produto com o identificador %d no restaurante de identificador %d.",
				produtoId, restauranteId));
	}
}
