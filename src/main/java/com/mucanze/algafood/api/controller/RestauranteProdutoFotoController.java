package com.mucanze.algafood.api.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.net.HttpHeaders;
import com.mucanze.algafood.api.assembler.FotoProdutoOutputModelAssembler;
import com.mucanze.algafood.api.model.input.FotoProdutoInput;
import com.mucanze.algafood.api.model.output.FotoProdutoOutputModel;
import com.mucanze.algafood.domain.exception.EntidadeInexistenteException;
import com.mucanze.algafood.domain.model.FotoProduto;
import com.mucanze.algafood.domain.model.Produto;
import com.mucanze.algafood.domain.service.CadastroProdutoService;
import com.mucanze.algafood.domain.service.CatalogoFotoProdutoService;
import com.mucanze.algafood.domain.service.FotoStorageService;
import com.mucanze.algafood.domain.service.FotoStorageService.FotoRecuperada;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/fotos")
public class RestauranteProdutoFotoController {
	
	@Autowired
	private CatalogoFotoProdutoService catalogoFotoProdutoService;
	
	@Autowired
	private CadastroProdutoService cadastroProdutoService;
	
	@Autowired
	private FotoProdutoOutputModelAssembler fotoProdutoOutputModelAssembler;
	
	@Autowired
	private FotoStorageService fotoStorageService;
	
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public FotoProdutoOutputModel buscarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		FotoProduto fotoProduto = catalogoFotoProdutoService.buscarPorId(restauranteId, produtoId);
		
		return fotoProdutoOutputModelAssembler.toModel(fotoProduto);
	}
	
	@GetMapping
	public ResponseEntity<?> recuperarFoto(@PathVariable Long restauranteId,
			@PathVariable Long produtoId, @RequestHeader(name="accept") String acceptHeader) throws HttpMediaTypeNotAcceptableException {
		try {
			FotoProduto fotoProduto = catalogoFotoProdutoService.buscarPorId(restauranteId, produtoId);
			
			MediaType fotoMediaType = MediaType.parseMediaType(fotoProduto.getContentType());
			List<MediaType> mediaTypesAceitas = MediaType.parseMediaTypes(acceptHeader);
			
			verificarCompatibilidadeMediaType(fotoMediaType, mediaTypesAceitas);
			
			FotoRecuperada fotoRecuperada = fotoStorageService.recuperar(fotoProduto.getNomeArquivo());
			
			if(fotoRecuperada.temUrl()) {
				return ResponseEntity
						.status(HttpStatus.FOUND)
						.header(HttpHeaders.LOCATION, fotoRecuperada.getUrl())
						.build();
			} else {
				return ResponseEntity.ok()
						.contentType(fotoMediaType)
						.body(new InputStreamResource(fotoRecuperada.getInputStream()));				
			}
			
		} catch (EntidadeInexistenteException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public FotoProdutoOutputModel actualizarFoto(@PathVariable Long restauranteId,
			@PathVariable Long produtoId, @Valid FotoProdutoInput fotoProdutoInput) throws IOException {
		
		Produto produto = cadastroProdutoService.buscarPorId(restauranteId, produtoId);
		
		FotoProduto fotoProduto = criarFotoProduto(fotoProdutoInput, produto);
		
		FotoProduto fotoProdutoRetorndo = catalogoFotoProdutoService.salvar(fotoProduto, fotoProdutoInput.getFicheiro().getInputStream());
		
		return fotoProdutoOutputModelAssembler.toModel(fotoProdutoRetorndo);
	} 
	
	@DeleteMapping
	public void removerFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
		catalogoFotoProdutoService.remover(restauranteId, produtoId);
	}
	
	private void verificarCompatibilidadeMediaType(MediaType fotoMediaType,
			List<MediaType> mediaTypesAceitas) throws HttpMediaTypeNotAcceptableException {
		boolean compativel = mediaTypesAceitas.stream()
				.anyMatch(mediaTypeAceita -> mediaTypeAceita.isCompatibleWith(fotoMediaType));
		
		if(!compativel) {
			throw new HttpMediaTypeNotAcceptableException(mediaTypesAceitas);
		}
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
