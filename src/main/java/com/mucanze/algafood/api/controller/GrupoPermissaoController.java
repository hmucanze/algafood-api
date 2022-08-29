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

import com.mucanze.algafood.api.assembler.PermissaoOutputModelAssembler;
import com.mucanze.algafood.api.model.output.PermissaoOutputModel;
import com.mucanze.algafood.domain.exception.EntidadeInexistenteException;
import com.mucanze.algafood.domain.exception.NegocioException;
import com.mucanze.algafood.domain.model.Grupo;
import com.mucanze.algafood.domain.service.CadastroGrupoService;

@RestController
@RequestMapping("grupos/{grupoId}/permissoes")
public class GrupoPermissaoController {
	
	@Autowired
	private CadastroGrupoService cadastroGrupoService;
	
	@Autowired
	private PermissaoOutputModelAssembler permissaoOutputModelAssembler;
	
	@GetMapping
	public List<PermissaoOutputModel> listarPermissoes(@PathVariable Long grupoId) {
		Grupo grupo = cadastroGrupoService.buscarPorId(grupoId);
		
		return permissaoOutputModelAssembler.toCollectionModel(grupo.getPermissoes());
	}
	
	@PutMapping("/{permissaoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void associarPermissao(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
		try {
			cadastroGrupoService.associarPermissao(grupoId, permissaoId);
		} catch (EntidadeInexistenteException e) {
			throw new NegocioException(e.getMessage());
		}
	}
	
	@DeleteMapping("/{permissaoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desassociarPermissao(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
		try {
			cadastroGrupoService.desassociarPermissoa(grupoId, permissaoId);
		} catch (EntidadeInexistenteException e) {
			throw new NegocioException(e.getMessage());
		}
	}

}
