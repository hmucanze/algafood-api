package com.mucanze.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mucanze.algafood.api.assembler.GrupoOutputModelAssembler;
import com.mucanze.algafood.api.model.output.GrupoOutputModel;
import com.mucanze.algafood.domain.exception.EntidadeInexistenteException;
import com.mucanze.algafood.domain.exception.NegocioException;
import com.mucanze.algafood.domain.model.Usuario;
import com.mucanze.algafood.domain.service.CadastroUsuarioService;

@RestController
@RequestMapping("usuarios/{usuarioId}/grupos")
public class UsuarioGrupoController {
	
	@Autowired
	private CadastroUsuarioService cadastroUsuarioService;
	
	@Autowired
	private GrupoOutputModelAssembler grupoOutputModelAssembler;
	
	@GetMapping
	public List<GrupoOutputModel> listarGrupos(@PathVariable Long usuarioId) {
		Usuario usuario = cadastroUsuarioService.buscarPorId(usuarioId);
		
		return grupoOutputModelAssembler.toCollectionModel(usuario.getGrupos());
	}
	
	@PutMapping("/{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void associarGrupo(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
		try {
			cadastroUsuarioService.associarGrupo(usuarioId, grupoId);
		} catch(EntidadeInexistenteException e) {
			throw new NegocioException(e.getMessage());
		}
		
	}
	
	@DeleteMapping("/{grupoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desassociarGrupo(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
		try {
			cadastroUsuarioService.desassociarGrupo(usuarioId, grupoId);
		} catch (EntidadeInexistenteException e) {
			throw new NegocioException(e.getMessage());
		}
	}

}
