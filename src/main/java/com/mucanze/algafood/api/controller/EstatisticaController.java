package com.mucanze.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mucanze.algafood.domain.filter.VendaDiariaFilter;
import com.mucanze.algafood.domain.model.dto.VendaDiaria;
import com.mucanze.algafood.domain.service.VendaQueryService;
import com.mucanze.algafood.domain.service.VendaReportService;

@RestController
@RequestMapping("/estatisticas")
public class EstatisticaController {
	
	@Autowired
	public VendaQueryService vendaQueryService; 
	
	@Autowired
	private VendaReportService vendaReportService;
	
	@GetMapping(path = "/vendas-diarias")
	public List<VendaDiaria> listarVendasDiarias(VendaDiariaFilter vendaDiariaFilter,
			@RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {
		return vendaQueryService.listarVendasDiarias(vendaDiariaFilter, timeOffset);
	}
	
	@GetMapping(path = "/vendas-diarias", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> listarVendasDiariasPdf(VendaDiariaFilter vendaDiariaFilter,
			@RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {
		
		byte[] relatorioPdf = vendaReportService.emitirRelatorioVendasDiarias(vendaDiariaFilter, timeOffset);
		
		var headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attchment; filename=vendas_diarias.pdf");
		
		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_PDF)
				.headers(headers)
				.body(relatorioPdf);
	}

}
