package com.mucanze.algafood.infrastruture.repository.spec;

import java.math.BigDecimal;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.mucanze.algafood.domain.model.Restaurante;

public class TaxaFreteZeroSpecification implements Specification<Restaurante> {

	private static final long serialVersionUID = 1L;

	@Override
	public Predicate toPredicate(Root<Restaurante> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		
		return criteriaBuilder.equal(root.get("taxaFrete"), BigDecimal.ZERO);
	}

}