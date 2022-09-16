package com.mucanze.algafood.infrastruture.repository.spec;

import java.math.BigDecimal;

import org.springframework.data.jpa.domain.Specification;

import com.mucanze.algafood.domain.model.Restaurante;

public class RestauranteSpecifications {
	
	public static Specification<Restaurante> taxaFreteZero() {
		return (root, query, builder) -> builder.equal(root.get("taxaFrete"), BigDecimal.ZERO);
	}

}
