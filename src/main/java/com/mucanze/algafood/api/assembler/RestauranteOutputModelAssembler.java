package com.mucanze.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.mucanze.algafood.api.model.output.CozinhaOutputModel;
import com.mucanze.algafood.api.model.output.RestauranteOutputModel;
import com.mucanze.algafood.domain.model.Restaurante;

@Component
public class RestauranteOutputModelAssembler {
	
	public RestauranteOutputModel toModel(Restaurante restaurante) {
		CozinhaOutputModel cozinhaOutputModel = new CozinhaOutputModel();
		cozinhaOutputModel.setId(restaurante.getCozinha().getId());
		cozinhaOutputModel.setNome(restaurante.getCozinha().getNome());
		
		RestauranteOutputModel restauranteOutputModel = new RestauranteOutputModel();
		restauranteOutputModel.setIdRestaurante(restaurante.getId());
		restauranteOutputModel.setNome(restaurante.getNome());
		restauranteOutputModel.setFrete(restaurante.getTaxaFrete());
		restauranteOutputModel.setActivo(restaurante.getActivo());
		restauranteOutputModel.setAberto(restaurante.getAberto());
		restauranteOutputModel.setCozinha(cozinhaOutputModel);
		return restauranteOutputModel;
	}
	
	public List<RestauranteOutputModel> toCollectionModel(List<Restaurante> restaurantes) {
		return restaurantes.stream()
				.map(restaurante -> toModel(restaurante))
				.collect(Collectors.toList());
	}

}
