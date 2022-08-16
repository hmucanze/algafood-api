package com.mucanze.algafood.domain.exception;

public class FormaPagamentoInexistenteException extends EntidadeInexistenteException {

	private static final long serialVersionUID = 1L;
	
	public FormaPagamentoInexistenteException(String mensagem) {
		super(mensagem);
	}
	
	public FormaPagamentoInexistenteException(Long formaPagamentoId) {
		this(String.format("Não existe forma de pagamento com o registo %d.", formaPagamentoId));
	}

}
