package com.mucanze.algafood.api.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mucanze.algafood.api.assembler.FotoProdutoOutputModelAssembler;
import com.mucanze.algafood.api.model.input.FotoProdutoInput;
import com.mucanze.algafood.api.model.output.FotoProdutoOutputModel;
import com.mucanze.algafood.domain.model.FotoProduto;
import com.mucanze.algafood.domain.model.Produto;
import com.mucanze.algafood.domain.service.CadastroProdutoService;
import com.mucanze.algafood.domain.service.CatalogoFotoProdutoService;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/fotos")
public class RestauranteProdutoFotoController {
	
	@Autowired
	private CatalogoFotoProdutoService catalogoFotoProdutoService;
	
	@Autowired
	private CadastroProdutoService cadastroProdutoService;
	
	@Autowired
	private FotoProdutoOutputModelAssembler fotoProdutoOutputModelAssembler;
	
	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public FotoProdutoOutputModel actualizarFoto(@PathVariable Long restauranteId,
			@PathVariable Long produtoId, @Valid FotoProdutoInput fotoProdutoInput) throws IOException {
		
		Produto produto = cadastroProdutoService.buscarPorId(restauranteId, produtoId);
		
		FotoProduto fotoProduto = criarFotoProduto(fotoProdutoInput, produto);
		
		FotoProduto fotoProdutoRetorndo = catalogoFotoProdutoService.salvar(fotoProduto, fotoProdutoInput.getFicheiro().getInputStream());
		
		return fotoProdutoOutputModelAssembler.toModel(fotoProdutoRetorndo);
	}

	private FotoProduto criarFotoProduto(FotoProdutoInput fotoProdutoInput, Produto produto) {
		FotoProduto fotoProduto = new FotoProduto();
		
		//fotoProduto.setId(produto.getId());
		fotoProduto.setProduto(produto);
		fotoProduto.setDescricao(fotoProdutoInput.getDescricao());
		fotoProduto.setNomeArquivo(fotoProdutoInput.getFicheiro().getOriginalFilename());
		fotoProduto.setContentType(fotoProdutoInput.getFicheiro().getContentType());
		fotoProduto.setTamanho(fotoProdutoInput.getFicheiro().getSize());
		
		return fotoProduto;
	}

}
