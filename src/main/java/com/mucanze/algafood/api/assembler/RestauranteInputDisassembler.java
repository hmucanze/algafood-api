package com.mucanze.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mucanze.algafood.api.model.input.RestauranteInputModel;
import com.mucanze.algafood.domain.model.Restaurante;

@Component
public class RestauranteInputDisassembler {
	
	@Autowired
	public ModelMapper modelMapper;
	
	public Restaurante toDomainObject(RestauranteInputModel restauranteInputModel) {
		return modelMapper.map(restauranteInputModel, Restaurante.class);
	}
	
	public void copyToDomainObject(RestauranteInputModel restauranteInputModel, Restaurante restaurante) {
		modelMapper.map(restauranteInputModel, restaurante);
	}

}
