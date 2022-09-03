package com.mucanze.algafood.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mucanze.algafood.api.model.input.ItemPedidoInputModel;
import com.mucanze.algafood.api.model.output.EnderecoOutputModel;
import com.mucanze.algafood.api.model.output.ItemPedidoOutputModel;
import com.mucanze.algafood.domain.model.Endereco;
import com.mucanze.algafood.domain.model.ItemPedido;

@Configuration
public class ModelMapperConfig {
	
	@Bean
	public ModelMapper modelMapper() {
		var modelMapper = new ModelMapper();
		
		modelMapper.createTypeMap(ItemPedidoInputModel.class, ItemPedido.class)
			.addMappings(mapper -> mapper.skip(ItemPedido::setId));
		
		modelMapper.createTypeMap(ItemPedido.class, ItemPedidoOutputModel.class)
			.addMapping(ItemPedido::getId, ItemPedidoOutputModel::setItemPedidoId);
		
		var enderecoToEnderecoOutputModel = modelMapper.createTypeMap(Endereco.class, EnderecoOutputModel.class);
		enderecoToEnderecoOutputModel.<String>addMapping(
				enderecoSrc -> enderecoSrc.getCidade().getEstado().getNome(),
				(enderecoDest, value) -> enderecoDest.getCidade().setEstado(value));
		
		return modelMapper;
	}

}
