package com.mucanze.algafood.api.controller;

import java.util.List;
import java.util.stream.Collectors;

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

import com.mucanze.algafood.api.model.input.RestauranteInputModel;
import com.mucanze.algafood.api.model.output.CozinhaOutputModel;
import com.mucanze.algafood.api.model.output.RestauranteOutputModel;
import com.mucanze.algafood.domain.model.Cozinha;
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
	
	/*@Autowired
	private SmartValidator validator;*/
	
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
	public RestauranteOutputModel salvar(@RequestBody @Valid RestauranteInputModel restauranteInputModel) {
		Restaurante restaurante = toDomain(restauranteInputModel);
		
		return toModel(cadastroRestauranteService.salvar(restaurante));
	}

	@PutMapping("/{id}")
	public RestauranteOutputModel actualizar(@RequestBody @Valid RestauranteInputModel restauranteInputModel, @PathVariable Long id) {
		Restaurante restaurante = toDomain(restauranteInputModel);
		
		restaurante = cadastroRestauranteService.actualizar(restaurante, id);
		
		return toModel(restaurante);	
	}
	
	/*@PatchMapping("/{id}")
	public RestauranteOutputModel actualizarParcial(@RequestBody Map<String, Object> dados, @PathVariable Long id,
			HttpServletRequest servletRequest) {
		Restaurante restauranteRetornado = cadastroRestauranteService.buscarPorId(id);
		
		merge(dados, restauranteRetornado, servletRequest);
		validate(restauranteRetornado, "restaurante");
		
		return actualizar(restauranteRetornado, id);
	}*/
	
	/*private void validate(Restaurante restaurante, String objectName) {
		BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restaurante, objectName);
		validator.validate(restaurante, bindingResult);
		
		if(bindingResult.hasErrors()) {
			throw new ValidacaoException(bindingResult);
		}
	}*/

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		cadastroRestauranteService.remover(id);
	}
	
	/*private void merge(Map<String, Object> dados, Restaurante restaurante, HttpServletRequest servletRequest) {
		
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
	}*/
	
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
	
	private Restaurante toDomain(@Valid RestauranteInputModel restauranteInputModel) {
		
		Restaurante restaurante = new Restaurante();
		
		Cozinha cozinha = new Cozinha();
		cozinha.setId(restauranteInputModel.getCozinha().getId());
		
		restaurante.setNome(restauranteInputModel.getNome());
		restaurante.setTaxaFrete(restauranteInputModel.getFrete());
		restaurante.setActivo(restauranteInputModel.getActivo());
		restaurante.setAberto(restauranteInputModel.getAberto());
		restaurante.setCozinha(cozinha);
		
		return restaurante;
	}

}
