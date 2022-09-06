package com.mucanze.algafood.domain.exception;

public class PedidoInexistenteException extends EntidadeInexistenteException {

	private static final long serialVersionUID = 1L;
	
	public PedidoInexistenteException(String codigo) {
		super(String.format("Não existe registo de pedido com o código %s.", codigo));
	}

}
