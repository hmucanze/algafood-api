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

import com.mucanze.algafood.api.assembler.UsuarioOutputModelAssembler;
import com.mucanze.algafood.api.model.output.UsuarioOutputModel;
import com.mucanze.algafood.domain.exception.EntidadeInexistenteException;
import com.mucanze.algafood.domain.exception.NegocioException;
import com.mucanze.algafood.domain.model.Restaurante;
import com.mucanze.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/usuarios")
public class RestauranteUsuarioResponsavelController {
	
	@Autowired
	private CadastroRestauranteService cadastroRestauranteService;
	
	@Autowired
	private UsuarioOutputModelAssembler usuarioOutputModelAssembler;
	
	@GetMapping
	public List<UsuarioOutputModel> listarUsuariosResponsaveis(@PathVariable Long restauranteId) {
		Restaurante restaurante = cadastroRestauranteService.buscarPorId(restauranteId);
		
		return usuarioOutputModelAssembler.toCollectionModel(restaurante.getResponsaveis());
	}
	
	@PutMapping("/{usuarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void adicionarUsuarioResponsavel(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
		try {
			cadastroRestauranteService.adicionarUsuarioResponsavel(restauranteId, usuarioId);
		} catch (EntidadeInexistenteException e) {
			throw new NegocioException(e.getMessage());
		}
	}
	
	@DeleteMapping("/{usuarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removerUsuarioResponsavel(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
		try {
			cadastroRestauranteService.removerUsuarioResponsavel(restauranteId, usuarioId);
		} catch (EntidadeInexistenteException e) {
			throw new NegocioException(e.getMessage());
		}
	}

}
