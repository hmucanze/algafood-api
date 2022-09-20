package com.mucanze.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mucanze.algafood.domain.filter.VendaDiariaFilter;
import com.mucanze.algafood.domain.model.dto.VendaDiaria;
import com.mucanze.algafood.domain.service.VendaQueryService;

@RestController
@RequestMapping("/estatisticas")
public class EstatisticaController {
	
	@Autowired
	public VendaQueryService vendaQueryService; 
	
	@GetMapping("/vendas-diarias")
	public List<VendaDiaria> listar(VendaDiariaFilter vendaDiariaFilter) {
		return vendaQueryService.listar(vendaDiariaFilter);
	}

}
