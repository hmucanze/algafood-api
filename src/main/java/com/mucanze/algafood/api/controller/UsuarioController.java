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

import com.mucanze.algafood.api.assembler.UsuarioComSenhaInputModelDisassembler;
import com.mucanze.algafood.api.assembler.UsuarioInputModelDisassembler;
import com.mucanze.algafood.api.assembler.UsuarioOutputModelAssembler;
import com.mucanze.algafood.api.model.input.SenhaInput;
import com.mucanze.algafood.api.model.input.UsuarioComSenhaInputModel;
import com.mucanze.algafood.api.model.input.UsuarioInputModel;
import com.mucanze.algafood.api.model.output.UsuarioOutputModel;
import com.mucanze.algafood.domain.model.Usuario;
import com.mucanze.algafood.domain.repository.UsuarioRepository;
import com.mucanze.algafood.domain.service.CadastroUsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private CadastroUsuarioService cadastroUsuarioService;
	
	@Autowired
	private UsuarioOutputModelAssembler usuarioOutputModelAssembler;
	
	@Autowired
	private UsuarioComSenhaInputModelDisassembler usuarioComSenhaInputModelDisassembler;
	
	@Autowired
	private UsuarioInputModelDisassembler usuarioInputModelDisassembler;
	
	@GetMapping
	public List<UsuarioOutputModel> listar() {
		return usuarioOutputModelAssembler.toCollectionModel(usuarioRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public UsuarioOutputModel buscarPorId(@PathVariable Long id) {
		 return usuarioOutputModelAssembler.toModel(cadastroUsuarioService.buscarPorId(id));
	} 
	
	@PostMapping
	public UsuarioOutputModel salvar(@RequestBody @Valid UsuarioComSenhaInputModel usuarioComSenhaInputModel) {
		Usuario usuario = usuarioComSenhaInputModelDisassembler.toDomainObject(usuarioComSenhaInputModel);
		
		return usuarioOutputModelAssembler.toModel(cadastroUsuarioService.salvar(usuario));
	}
	
	@PutMapping("/{id}")
	public UsuarioOutputModel actualizar(@RequestBody @Valid UsuarioInputModel usuarioInputModel, @PathVariable Long id) {
		Usuario usuario = usuarioInputModelDisassembler.toDomainObject(usuarioInputModel);
		
		return usuarioOutputModelAssembler.toModel(cadastroUsuarioService.actualizar(usuario, id));
	}
	
	@PutMapping("/{id}/senha")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void alterarSenha(@RequestBody @Valid SenhaInput senhaInput, @PathVariable Long id) {
		cadastroUsuarioService.alterarSenha(senhaInput.getSenhaActual(), senhaInput.getSenhaNova(), id);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		cadastroUsuarioService.remover(id);
	}
	
}
