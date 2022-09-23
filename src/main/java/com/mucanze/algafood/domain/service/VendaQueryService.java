package com.mucanze.algafood.domain.service;

import java.util.List;

import com.mucanze.algafood.domain.filter.VendaDiariaFilter;
import com.mucanze.algafood.domain.model.dto.VendaDiaria;

public interface VendaQueryService {
	
	public List<VendaDiaria> listarVendasDiarias(VendaDiariaFilter vendaDiariaFilter, String timeOffset);

}
