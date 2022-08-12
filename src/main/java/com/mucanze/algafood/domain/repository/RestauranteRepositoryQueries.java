package com.mucanze.algafood.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import com.mucanze.algafood.domain.model.Restaurante;

public interface RestauranteRepositoryQueries {

	List<Restaurante> consultarPorNome(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);

}