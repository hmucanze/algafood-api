package com.mucanze.algafood.domain.service;

import com.mucanze.algafood.domain.filter.VendaDiariaFilter;

public interface VendaReportService {
	
	public byte[] emitirRelatorioVendasDiarias(VendaDiariaFilter vendaDiariaFilter, String timeOffset);

}
