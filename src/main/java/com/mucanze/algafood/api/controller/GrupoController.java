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

import com.mucanze.algafood.api.assembler.GrupoInputModelDisassembler;
import com.mucanze.algafood.api.assembler.GrupoOutputModelAssembler;
import com.mucanze.algafood.api.model.input.GrupoInputModel;
import com.mucanze.algafood.api.model.output.GrupoOutputModel;
import com.mucanze.algafood.domain.model.Grupo;
import com.mucanze.algafood.domain.repository.GrupoRepository;
import com.mucanze.algafood.domain.service.CadastroGrupoService;

@RestController
@RequestMapping("/grupos")
public class GrupoController {
	
	@Autowired
	private GrupoRepository grupoRepository;
	
	@Autowired
	private CadastroGrupoService cadastroGrupoService;
	
	@Autowired
	private GrupoOutputModelAssembler grupoOutputModelAssembler;
	
	@Autowired
	private GrupoInputModelDisassembler grupoInputModelDisassembler;
	
	@GetMapping
	public List<GrupoOutputModel> listar() {
		return grupoOutputModelAssembler.toCollectionModel(grupoRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public GrupoOutputModel buscarPorId(@PathVariable Long id) {
		Grupo grupo = cadastroGrupoService.buscarPorId(id);
		return grupoOutputModelAssembler.toModel(grupo);
	}
	
	@PostMapping
	public GrupoOutputModel salvar(@RequestBody @Valid GrupoInputModel grupoInputModel) {
		Grupo grupo = grupoInputModelDisassembler.toDomainObject(grupoInputModel);
		grupo = cadastroGrupoService.salvar(grupo);
		
		return grupoOutputModelAssembler.toModel(grupo);
	}
	
	@PutMapping("/{id}")
	public GrupoOutputModel actualizar(@RequestBody @Valid GrupoInputModel grupoInputModel, @PathVariable Long id) {
		Grupo grupo = grupoInputModelDisassembler.toDomainObject(grupoInputModel);
		grupo = cadastroGrupoService.actualizar(grupo, id);
		
		return grupoOutputModelAssembler.toModel(grupo);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		cadastroGrupoService.remover(id);
	}

}
