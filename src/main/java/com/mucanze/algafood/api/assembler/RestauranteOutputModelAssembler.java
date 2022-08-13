package com.mucanze.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mucanze.algafood.api.model.output.RestauranteOutputModel;
import com.mucanze.algafood.domain.model.Restaurante;

@Component
public class RestauranteOutputModelAssembler {
	
	@Autowired
	public ModelMapper modelMapper;
	
	public RestauranteOutputModel toModel(Restaurante restaurante) {
		return modelMapper.map(restaurante, RestauranteOutputModel.class);
	}
	
	public List<RestauranteOutputModel> toCollectionModel(List<Restaurante> restaurantes) {
		return restaurantes.stream()
				.map(restaurante -> toModel(restaurante))
				.collect(Collectors.toList());
	}

}
