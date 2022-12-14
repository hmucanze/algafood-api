package com.mucanze.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mucanze.algafood.api.model.input.RestauranteInputModel;
import com.mucanze.algafood.domain.model.Cidade;
import com.mucanze.algafood.domain.model.Cozinha;
import com.mucanze.algafood.domain.model.Restaurante;

@Component
public class RestauranteInputDisassembler {
	
	@Autowired
	public ModelMapper modelMapper;
	
	public Restaurante toDomainObject(RestauranteInputModel restauranteInputModel) {
		return modelMapper.map(restauranteInputModel, Restaurante.class);
	}
	
	public void copyToDomainObject(RestauranteInputModel restauranteInputModel, Restaurante restaurante) {
		//Para evitar  org.hibernate.HibernateException: identifier of an instance of
		//com.mucanze.algafood.domain.model.Cozinha was altered from 1 to 2
		restaurante.setCozinha(new Cozinha());
		
		if(restaurante.getEndereco() != null) {
			restaurante.getEndereco().setCidade(new Cidade());
		}
		modelMapper.map(restauranteInputModel, restaurante);
	}

}
