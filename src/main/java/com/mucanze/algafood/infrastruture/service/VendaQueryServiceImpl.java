package com.mucanze.algafood.infrastruture.service;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.mucanze.algafood.domain.filter.VendaDiariaFilter;
import com.mucanze.algafood.domain.model.Pedido;
import com.mucanze.algafood.domain.model.dto.VendaDiaria;
import com.mucanze.algafood.domain.service.VendaQueryService;

@Repository
public class VendaQueryServiceImpl implements VendaQueryService {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<VendaDiaria> listar(VendaDiariaFilter vendaDiariaFilter) {
		var builder = entityManager.getCriteriaBuilder();
		
		var query = builder.createQuery(VendaDiaria.class);
		
		var root = query.from(Pedido.class);
		
		var functionDateDataCriacao = builder.function("date", Date.class, root.get("dataCriacao"));
		
		var selection = builder.construct(VendaDiaria.class,
				functionDateDataCriacao,
				builder.count(root.get("id")),
				builder.sum(root.get("valorTotal")));
		
		query.select(selection);
		query.groupBy(functionDateDataCriacao);
		
		return entityManager.createQuery(query).getResultList();
	}

}
