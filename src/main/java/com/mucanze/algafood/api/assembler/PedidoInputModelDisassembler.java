package com.mucanze.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mucanze.algafood.api.model.input.PedidoInputModel;
import com.mucanze.algafood.domain.model.Pedido;

@Component
public class PedidoInputModelDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Pedido toDomainObject(PedidoInputModel pedidoInputModel) {
		return modelMapper.map(pedidoInputModel, Pedido.class);
	}

}
