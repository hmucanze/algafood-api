package com.mucanze.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.mucanze.algafood.api.assembler.PedidoInputModelDisassembler;
import com.mucanze.algafood.api.assembler.PedidoOutputModelAssembler;
import com.mucanze.algafood.api.assembler.PedidoOutputResumoModelAssembler;
import com.mucanze.algafood.api.model.input.PedidoInputModel;
import com.mucanze.algafood.api.model.output.PedidoOutputModel;
import com.mucanze.algafood.api.model.output.PedidoOutputResumoModel;
import com.mucanze.algafood.domain.exception.EntidadeInexistenteException;
import com.mucanze.algafood.domain.exception.NegocioException;
import com.mucanze.algafood.domain.model.Pedido;
import com.mucanze.algafood.domain.model.Usuario;
import com.mucanze.algafood.domain.repository.PedidoRepository;
import com.mucanze.algafood.domain.service.EmissaoPedidoService;

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
	private EmissaoPedidoService emissaoPedidoService;
	
	@Autowired
	private PedidoInputModelDisassembler pedidoInputModelDisassembler;
	
	@GetMapping
	public MappingJacksonValue listar(@RequestParam(required = false) String campos) {
		
		List<Pedido> pedidos = pedidoRepository.findAll();
		List<PedidoOutputResumoModel> pedidoResumo = pedidoOutputResumoModelAssembler.toCollectionModel(pedidos);
		
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(pedidoResumo);
		
		SimpleFilterProvider filterProvider = new SimpleFilterProvider();
		filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.serializeAll());
		
		if(StringUtils.isNotBlank(campos)) {
			filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.filterOutAllExcept(campos.split(",")));
		}
		
		mappingJacksonValue.setFilters(filterProvider);
		
		return mappingJacksonValue;
	}
	
	@GetMapping("/{codigo}")
	public PedidoOutputModel buscarPorId(@PathVariable String codigo) {
		return pedidoOutputModelAssembler.toModel(emissaoPedidoService.buscarPorCodigo(codigo));
	}
	
	@PostMapping
	public PedidoOutputModel salvar(@RequestBody @Valid PedidoInputModel pedidoInputModel) {
		try {
			Pedido pedido = pedidoInputModelDisassembler.toDomainObject(pedidoInputModel);
			Usuario cliente = new Usuario();
			cliente.setId(1L);
			
			pedido.setCliente(cliente);
			
			pedido = emissaoPedidoService.salvar(pedido);
			
			return pedidoOutputModelAssembler.toModel(pedido);
		} catch (EntidadeInexistenteException e) {
			throw new NegocioException(e.getMessage());
		}
	}
	
}
