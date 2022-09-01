package com.mucanze.algafood.domain.exception;

public class PedidoInexistenteException extends EntidadeInexistenteException {

	private static final long serialVersionUID = 1L;
	
	public PedidoInexistenteException(String mensagem) {
		super(mensagem);
	}
	
	public PedidoInexistenteException(Long pedidoId) {
		this(String.format("Não existe registo de pedido com o id %d.", pedidoId));
	}


}
