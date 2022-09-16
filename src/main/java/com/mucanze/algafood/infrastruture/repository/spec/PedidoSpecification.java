package com.mucanze.algafood.infrastruture.repository.spec;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.mucanze.algafood.domain.model.Pedido;
import com.mucanze.algafood.domain.repository.filter.PedidoFilter;

public class PedidoSpecification {
	
	public static Specification<Pedido> filtrar(PedidoFilter pedidoFilter) {
		return (root, query, builder) -> {
			List<Predicate> predicates = new ArrayList<>();
			
			if(Pedido.class.equals(query.getResultType())) {
				root.fetch("cliente");
				root.fetch("restaurante").fetch("cozinha");
			}
			
			if(pedidoFilter.getClienteId() != null) {
				predicates.add(builder.equal(root.get("cliente"), pedidoFilter.getClienteId()));
			}
			
			if(pedidoFilter.getRestauranteId() != null) {
				predicates.add(builder.equal(root.get("restaurante"), pedidoFilter.getRestauranteId()));
			}
			
			if(pedidoFilter.getDataCriacaoInicial() != null) {
				predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"),
						pedidoFilter.getDataCriacaoInicial()));
			}
			
			if(pedidoFilter.getDataCriacaoFinal() != null) {
				predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"),
						pedidoFilter.getDataCriacaoFinal()));
			}
			
			return builder.and(predicates.toArray(new Predicate[0]));
		};
	}

}
