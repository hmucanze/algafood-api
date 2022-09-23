package com.mucanze.algafood.infrastruture.service.query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.mucanze.algafood.domain.filter.VendaDiariaFilter;
import com.mucanze.algafood.domain.model.Pedido;
import com.mucanze.algafood.domain.model.StatusPedido;
import com.mucanze.algafood.domain.model.dto.VendaDiaria;
import com.mucanze.algafood.domain.service.VendaQueryService;

@Repository
public class VendaQueryServiceImpl implements VendaQueryService {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<VendaDiaria> listarVendasDiarias(VendaDiariaFilter vendaDiariaFilter, String timeOffset) {
		var builder = entityManager.getCriteriaBuilder();
		
		var query = builder.createQuery(VendaDiaria.class);
		
		var root = query.from(Pedido.class);
		
		var functionDataCriacaoConvertTZ = builder.function("convert_tz", Date.class,
				root.get("dataCriacao"), builder.literal("+00:00"), builder.literal(timeOffset));
		
		var functionDateDataCriacao = builder.function("date", Date.class,
				functionDataCriacaoConvertTZ);
		
		var selection = builder.construct(VendaDiaria.class,
				functionDateDataCriacao,
				builder.count(root.get("id")),
				builder.sum(root.get("valorTotal")));
		
		Predicate[] predicates = criarPredicates(builder, root, vendaDiariaFilter);
		
		query.select(selection);
		query.groupBy(functionDateDataCriacao);
		query.where(predicates);
		
		return entityManager.createQuery(query).getResultList();
	}

	private Predicate[] criarPredicates(CriteriaBuilder builder, Root<Pedido> root,
			VendaDiariaFilter vendaDiariaFilter) {
		List<Predicate> predicates = new ArrayList<>();
		
		if(vendaDiariaFilter.getRestauranteId() != null) {
			predicates.add(builder.equal(root.get("restaurante"),
					vendaDiariaFilter.getRestauranteId()));
		}
		
		if(vendaDiariaFilter.getDataCriacaoInicial() != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"),
					vendaDiariaFilter.getDataCriacaoInicial()));
		}
		
		if(vendaDiariaFilter.getDataCriacaoFinal() != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"),
					vendaDiariaFilter.getDataCriacaoFinal()));
		}
		
		predicates.add(root.get("status")
				.in(StatusPedido.CONFIRMADO, StatusPedido.ENTREGUE));
		
		return predicates.toArray(new Predicate[0]);
	}

}
