package com.mucanze.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mucanze.algafood.domain.exception.PedidoInexistenteException;
import com.mucanze.algafood.domain.model.Pedido;
import com.mucanze.algafood.domain.repository.PedidoRepository;

@Service
public class CadastroPedidoService {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	public Pedido buscarPorId(Long id) {
		return pedidoRepository.findById(id).orElseThrow(
				() -> new PedidoInexistenteException(id));
	}

}
