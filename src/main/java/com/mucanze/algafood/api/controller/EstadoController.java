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

import com.mucanze.algafood.api.assembler.EstadoInputModelDisassembler;
import com.mucanze.algafood.api.assembler.EstadoOutputModelAssembler;
import com.mucanze.algafood.api.model.input.EstadoInputModel;
import com.mucanze.algafood.api.model.output.EstadoOutputModel;
import com.mucanze.algafood.domain.model.Estado;
import com.mucanze.algafood.domain.repository.EstadoRepository;
import com.mucanze.algafood.domain.service.CadastroEstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoController {
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CadastroEstadoService cadastroEstadoService;
	
	@Autowired
	private EstadoOutputModelAssembler estadoOutputModelAssembler;
	
	@Autowired
	private EstadoInputModelDisassembler estadoInputModelDisassembler;
	
	@GetMapping
	public List<EstadoOutputModel> listar() {
		return estadoOutputModelAssembler.toCollectionModel(estadoRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public EstadoOutputModel buscarPorId(@PathVariable Long id) {
		Estado estado = cadastroEstadoService.buscarPorId(id);
		return estadoOutputModelAssembler.toModel(estado); 
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EstadoOutputModel salvar(@RequestBody @Valid EstadoInputModel estadoInputModel) {
		Estado estado = estadoInputModelDisassembler.toDomainModel(estadoInputModel);
		estado = cadastroEstadoService.salvar(estado);
		return estadoOutputModelAssembler.toModel(estado);
	}
	
	@PutMapping("/{id}")
	public EstadoOutputModel actualizar(@RequestBody @Valid EstadoInputModel estadoInputModel, @PathVariable Long id) {
		Estado estado = estadoInputModelDisassembler.toDomainModel(estadoInputModel);
		estado = cadastroEstadoService.actualizar(estado, id);
		
		return estadoOutputModelAssembler.toModel(estado);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		cadastroEstadoService.remover(id);
	}

}
