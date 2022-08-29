package com.mucanze.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mucanze.algafood.api.assembler.ProdutoInputModelDisassembler;
import com.mucanze.algafood.api.assembler.ProdutoOutputModelAssembler;
import com.mucanze.algafood.api.model.input.ProdutoInputModel;
import com.mucanze.algafood.api.model.output.ProdutoOutputModel;
import com.mucanze.algafood.domain.model.Produto;
import com.mucanze.algafood.domain.service.CadastroProdutoService;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController {
	
	@Autowired
	private CadastroProdutoService cadastroProdutoService;
	
	@Autowired
	private ProdutoOutputModelAssembler produtoOutputModelAssembler;
	
	@Autowired
	private ProdutoInputModelDisassembler produtoInputModelDisassembler;
	
	@GetMapping
	public List<ProdutoOutputModel> listarProdutoPorRestauranteId(@PathVariable Long restauranteId) {
		
		return produtoOutputModelAssembler.toCollectionModel(cadastroProdutoService.buscarPorRestauranteId(restauranteId));
	}
	
	@GetMapping("/{produtoId}")
	public ProdutoOutputModel buscarProdutoPorId(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		return produtoOutputModelAssembler.toModel(cadastroProdutoService.buscarPorId(restauranteId, produtoId));
	}
	
	@PostMapping
	public ProdutoOutputModel salvarProdutoDeRestaurante(@PathVariable Long restauranteId,
			@RequestBody @Valid ProdutoInputModel produtoInputModel) {
		Produto produto = produtoInputModelDisassembler.toDomainObject(produtoInputModel);
		
		return produtoOutputModelAssembler.toModel(cadastroProdutoService.salvar(restauranteId, produto));
	}
	
	@PutMapping("/{produtoId}")
	public ProdutoOutputModel actualizarProdutoDeRestaurante(@PathVariable Long restauranteId,
			@PathVariable Long produtoId, @RequestBody @Valid ProdutoInputModel produtoInputModel) {
		
		Produto produto = produtoInputModelDisassembler.toDomainObject(produtoInputModel);
		
		return produtoOutputModelAssembler.toModel(cadastroProdutoService.actualizar(restauranteId, produtoId, produto));
	}
	
	@DeleteMapping("/{produtoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removerProduto(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		cadastroProdutoService.remover(restauranteId, produtoId);
	}

}
