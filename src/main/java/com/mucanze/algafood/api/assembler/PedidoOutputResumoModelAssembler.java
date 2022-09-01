package com.mucanze.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mucanze.algafood.api.model.output.PedidoOutputResumoModel;
import com.mucanze.algafood.domain.model.Pedido;

@Component
public class PedidoOutputResumoModelAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public PedidoOutputResumoModel toModel(Pedido pedido) {
		return modelMapper.map(pedido, PedidoOutputResumoModel.class);
	}
	
	public List<PedidoOutputResumoModel> toCollectionModel(List<Pedido> pedidos) {
		return pedidos.stream()
				.map(pedido -> toModel(pedido))
				.collect(Collectors.toList());
	}

}
