package com.mucanze.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mucanze.algafood.api.model.output.PedidoOutputModel;
import com.mucanze.algafood.domain.model.Pedido;

@Component
public class PedidoOutputModelAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public PedidoOutputModel toModel(Pedido pedido) {
		return modelMapper.map(pedido, PedidoOutputModel.class);
	}
	
	public List<PedidoOutputModel> toCollectionModel(List<Pedido> pedidos) {
		return pedidos.stream()
				.map(pedido -> toModel(pedido))
				.collect(Collectors.toList());
	}

}
