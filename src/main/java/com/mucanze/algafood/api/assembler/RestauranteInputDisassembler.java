package com.mucanze.algafood.api.assembler;

import javax.validation.Valid;

import org.springframework.stereotype.Component;

import com.mucanze.algafood.api.model.input.RestauranteInputModel;
import com.mucanze.algafood.domain.model.Cozinha;
import com.mucanze.algafood.domain.model.Restaurante;

@Component
public class RestauranteInputDisassembler {
	
	public Restaurante toDomainObject(@Valid RestauranteInputModel restauranteInputModel) {
		
		Restaurante restaurante = new Restaurante();
		
		Cozinha cozinha = new Cozinha();
		cozinha.setId(restauranteInputModel.getCozinha().getId());
		
		restaurante.setNome(restauranteInputModel.getNome());
		restaurante.setTaxaFrete(restauranteInputModel.getFrete());
		restaurante.setActivo(restauranteInputModel.getActivo());
		restaurante.setAberto(restauranteInputModel.getAberto());
		restaurante.setCozinha(cozinha);
		
		return restaurante;
	}

}
