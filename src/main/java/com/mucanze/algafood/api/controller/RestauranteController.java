package com.mucanze.algafood.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mucanze.algafood.api.model.output.CozinhaOutputModel;
import com.mucanze.algafood.api.model.output.RestauranteOutputModel;
import com.mucanze.algafood.core.validation.ValidacaoException;
import com.mucanze.algafood.domain.model.Restaurante;
import com.mucanze.algafood.domain.repository.RestauranteRepository;
import com.mucanze.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CadastroRestauranteService cadastroRestauranteService;
	
	@Autowired
	private SmartValidator validator;
	
	@GetMapping
	public List<RestauranteOutputModel> listar() {
		return toCollectionModel(restauranteRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public RestauranteOutputModel buscarPorId(@PathVariable Long id) {
		
		Restaurante restaurante = cadastroRestauranteService.buscarPorId(id);
		
		return toModel(restaurante);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Restaurante salvar(@RequestBody @Valid Restaurante restaurante) {
		return cadastroRestauranteService.salvar(restaurante);
	}
	
	@PutMapping("/{id}")
	public Restaurante actualizar(@RequestBody @Valid Restaurante restaurante, @PathVariable Long id) {
		return cadastroRestauranteService.actualizar(restaurante, id);	
	}
	
	@PatchMapping("/{id}")
	public Restaurante actualizarParcial(@RequestBody Map<String, Object> dados, @PathVariable Long id,
			HttpServletRequest servletRequest) {
		Restaurante restauranteRetornado = cadastroRestauranteService.buscarPorId(id);
		
		merge(dados, restauranteRetornado, servletRequest);
		validate(restauranteRetornado, "restaurante");
		
		return actualizar(restauranteRetornado, id);
	}
	
	private void validate(Restaurante restaurante, String objectName) {
		BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restaurante, objectName);
		validator.validate(restaurante, bindingResult);
		
		if(bindingResult.hasErrors()) {
			throw new ValidacaoException(bindingResult);
		}
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		cadastroRestauranteService.remover(id);
	}
	
	private void merge(Map<String, Object> dados, Restaurante restaurante, HttpServletRequest servletRequest) {
		
		ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(servletRequest);
		
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
			
			Restaurante dadosRestaurante = objectMapper.convertValue(dados, Restaurante.class);
			
			dados.forEach((nomePropriedade, valorPropriedade) -> {
				Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
				field.setAccessible(true);
				
				Object novoValor = ReflectionUtils.getField(field, dadosRestaurante);
				
				ReflectionUtils.setField(field, restaurante, novoValor);
			});
		} catch(IllegalArgumentException e) {
			Throwable rootCause = ExceptionUtils.getRootCause(e);
			
			throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
		}
	}
	
	private RestauranteOutputModel toModel(Restaurante restaurante) {
		CozinhaOutputModel cozinhaOutputModel = new CozinhaOutputModel();
		cozinhaOutputModel.setId(restaurante.getCozinha().getId());
		cozinhaOutputModel.setNome(restaurante.getCozinha().getNome());
		
		RestauranteOutputModel restauranteOutputModel = new RestauranteOutputModel();
		restauranteOutputModel.setIdRestaurante(restaurante.getId());
		restauranteOutputModel.setNome(restaurante.getNome());
		restauranteOutputModel.setFrete(restaurante.getTaxaFrete());
		restauranteOutputModel.setActivo(restaurante.getActivo());
		restauranteOutputModel.setAberto(restaurante.getAberto());
		restauranteOutputModel.setCozinha(cozinhaOutputModel);
		return restauranteOutputModel;
	}
	
	private List<RestauranteOutputModel> toCollectionModel(List<Restaurante> restaurantes) {
		return restaurantes.stream()
				.map(restaurante -> toModel(restaurante))
				.collect(Collectors.toList());
	}

}
