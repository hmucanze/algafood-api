package com.mucanze.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mucanze.algafood.api.assembler.CozinhaInputModelDisassembler;
import com.mucanze.algafood.api.assembler.CozinhaOutputModelAssembler;
import com.mucanze.algafood.api.model.CozinhasXMLWrapper;
import com.mucanze.algafood.api.model.input.CozinhaInputModel;
import com.mucanze.algafood.api.model.output.CozinhaOutputModel;
import com.mucanze.algafood.domain.model.Cozinha;
import com.mucanze.algafood.domain.repository.CozinhaRepository;
import com.mucanze.algafood.domain.service.CadastroCozinhaService;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Autowired
	private CadastroCozinhaService cadastroCozinhaService;
	
	@Autowired
	private CozinhaOutputModelAssembler cozinhaOutputModelAssembler;
	
	@Autowired
	private CozinhaInputModelDisassembler cozinhaInputModelDisassembler;
	
	@GetMapping
	public List<CozinhaOutputModel> listar(){
		return cozinhaOutputModelAssembler.toCollectionModel(cozinhaRepository.findAll());
	}
	
	@GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
	public CozinhasXMLWrapper listarXML() {
		return new CozinhasXMLWrapper(cozinhaRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public CozinhaOutputModel buscarPorId(@PathVariable Long id) {
		Cozinha cozinha = cadastroCozinhaService.buscarPorId(id);
		return cozinhaOutputModelAssembler.toModel(cozinha);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaOutputModel salvar(@RequestBody @Valid CozinhaInputModel cozinhaInputModel) {
		
		Cozinha cozinha = cozinhaInputModelDisassembler.toDomainObject(cozinhaInputModel);
		return cozinhaOutputModelAssembler.toModel(cadastroCozinhaService.salvar(cozinha));
	}
	
	@PutMapping("/{id}")
	public CozinhaOutputModel atualizar(@RequestBody @Valid CozinhaInputModel cozinhaInputModel, @PathVariable Long id) {
		Cozinha cozinha = cozinhaInputModelDisassembler.toDomainObject(cozinhaInputModel);
		cozinha = cadastroCozinhaService.actualizar(cozinha, id);
		
		return cozinhaOutputModelAssembler.toModel(cozinha);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		cadastroCozinhaService.remover(id);
	}
	 
}
