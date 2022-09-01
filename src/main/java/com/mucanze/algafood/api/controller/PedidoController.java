package com.mucanze.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mucanze.algafood.api.assembler.PedidoOutputModelAssembler;
import com.mucanze.algafood.api.assembler.PedidoOutputResumoModelAssembler;
import com.mucanze.algafood.api.model.output.PedidoOutputModel;
import com.mucanze.algafood.api.model.output.PedidoOutputResumoModel;
import com.mucanze.algafood.domain.repository.PedidoRepository;
import com.mucanze.algafood.domain.service.CadastroPedidoService;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PedidoOutputModelAssembler pedidoOutputModelAssembler;
	
	@Autowired
	private PedidoOutputResumoModelAssembler pedidoOutputResumoModelAssembler;
	
	@Autowired
	private CadastroPedidoService cadastroPedidoService;
	
	@GetMapping
	public List<PedidoOutputResumoModel> listar() {
		return pedidoOutputResumoModelAssembler.toCollectionModel(pedidoRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public PedidoOutputModel buscarPorId(@PathVariable Long id) {
		return pedidoOutputModelAssembler.toModel(cadastroPedidoService.buscarPorId(id));
	}
	
}
