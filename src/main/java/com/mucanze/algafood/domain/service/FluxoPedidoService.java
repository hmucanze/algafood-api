package com.mucanze.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mucanze.algafood.domain.model.Pedido;

@Service
public class FluxoPedidoService {
	
	@Autowired
	private EmissaoPedidoService emissaoPedidoService; 
	
	@Transactional
	public void confirmar(Long pedidoId) {
		Pedido pedido = emissaoPedidoService.buscarPorId(pedidoId);
		
		pedido.confirmar();
	}
	
	@Transactional
	public void entregar(Long pedidoId) {
		Pedido pedido = emissaoPedidoService.buscarPorId(pedidoId);
		
		pedido.entregar();
	}
	
	@Transactional
	public void cancelar(Long pedidoId) {
		Pedido pedido = emissaoPedidoService.buscarPorId(pedidoId);
		
		pedido.cancelar();
	}

}
