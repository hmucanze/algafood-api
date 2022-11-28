package com.mucanze.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mucanze.algafood.domain.model.Pedido;
import com.mucanze.algafood.domain.service.EnvioEmailService.Mensagem;

@Service
public class FluxoPedidoService {
	
	@Autowired
	private EmissaoPedidoService emissaoPedidoService; 
	
	@Autowired
	private EnvioEmailService envioEmailService;
	
	@Transactional
	public void confirmar(String codigoPedido) {
		Pedido pedido = emissaoPedidoService.buscarPorCodigo(codigoPedido);
		
		pedido.confirmar();
		
		Mensagem mensagem = Mensagem.builder()
				.assunto(pedido.getRestaurante().getNome() + " Pedido confirmado")
				.destinatario(pedido.getCliente().getEmail())
				.texto("O pedido de código <strong>"
						+ pedido.getCodigo() + "</strong> foi confirmado!")
				.build();
		
		System.out.println("Usuário: " + pedido.getCliente().getEmail());
		
		envioEmailService.enviar(mensagem);
	}
	
	@Transactional
	public void entregar(String codigoPedido) {
		Pedido pedido = emissaoPedidoService.buscarPorCodigo(codigoPedido);
		
		pedido.entregar();
	}
	
	@Transactional
	public void cancelar(String codigoPedido) {
		Pedido pedido = emissaoPedidoService.buscarPorCodigo(codigoPedido);
		
		pedido.cancelar();
	}

}
