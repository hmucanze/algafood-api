package com.mucanze.algafood.core.jackson;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.mucanze.algafood.api.model.mixin.CidadeMixin;
import com.mucanze.algafood.api.model.mixin.CozinhaMixin;
import com.mucanze.algafood.api.model.mixin.RestauranteMixin;
import com.mucanze.algafood.domain.model.Cidade;
import com.mucanze.algafood.domain.model.Cozinha;
import com.mucanze.algafood.domain.model.Restaurante;

@Component
public class JacksonMixinModule extends SimpleModule {

	private static final long serialVersionUID = 1L;
	
	public JacksonMixinModule() {
		setMixInAnnotation(Restaurante.class, RestauranteMixin.class);
		setMixInAnnotation(Cidade.class, CidadeMixin.class);
		setMixInAnnotation(Cozinha.class, CozinhaMixin.class);
	}

}
