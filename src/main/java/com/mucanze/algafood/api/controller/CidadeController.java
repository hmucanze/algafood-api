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

import com.mucanze.algafood.api.assembler.CidadeInputDisassembler;
import com.mucanze.algafood.api.assembler.CidadeOutputModelAssembler;
import com.mucanze.algafood.api.model.input.CidadeInputModel;
import com.mucanze.algafood.api.model.output.CidadeOutputModel;
import com.mucanze.algafood.domain.model.Cidade;
import com.mucanze.algafood.domain.repository.CidadeRepository;
import com.mucanze.algafood.domain.service.CadastroCidadeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "Cidades")
@RestController
@RequestMapping("/cidades")
public class CidadeController {
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CadastroCidadeService cadastroCidadeService;
	
	@Autowired
	private CidadeOutputModelAssembler cidadeOutputModelAssembler;
	
	@Autowired
	private CidadeInputDisassembler cidadeInputDisassembler;
	
	@ApiOperation("Lista todas Cidades")
	@GetMapping
	public List<CidadeOutputModel> listar() {
		return cidadeOutputModelAssembler.toCollectionModel(cidadeRepository.findAll());
	}
	
	@ApiOperation("Busca uma Cidade por ID")
	@GetMapping("/{id}")
	public CidadeOutputModel buscarPorId(@ApiParam(value = "ID da Cidade") @PathVariable Long id) {
		Cidade cidade = cadastroCidadeService.buscarPorId(id);
		return cidadeOutputModelAssembler.toModel(cidade);
	}
	
	@ApiOperation("Cadastra uma Cidade")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeOutputModel salvar(@RequestBody @Valid CidadeInputModel cidadeInputModel) {
		Cidade cidade = cidadeInputDisassembler.toDomainObject(cidadeInputModel);
		return cidadeOutputModelAssembler.toModel(cadastroCidadeService.salvar(cidade));
	}
	
	@ApiOperation("Actualiza uma Cidade por ID")
	@PutMapping("/{id}")
	public CidadeOutputModel actualizar(@RequestBody @Valid CidadeInputModel cidadeInpuModel,
			@ApiParam(value="ID da Cidade") @PathVariable Long id) {
		Cidade cidade = cidadeInputDisassembler.toDomainObject(cidadeInpuModel);
		cidade = cadastroCidadeService.actualizar(cidade, id);
		
		return cidadeOutputModelAssembler.toModel(cidade);
	}
	
	@ApiOperation("Remove uma Cidade por ID")
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@ApiParam(value="ID da Cidade") @PathVariable Long id) {
		cadastroCidadeService.remover(id);
	}

}
