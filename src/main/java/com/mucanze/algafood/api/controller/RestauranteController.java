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

import com.fasterxml.jackson.annotation.JsonView;
import com.mucanze.algafood.api.assembler.RestauranteInputDisassembler;
import com.mucanze.algafood.api.assembler.RestauranteOutputModelAssembler;
import com.mucanze.algafood.api.model.input.RestauranteInputModel;
import com.mucanze.algafood.api.model.output.RestauranteOutputModel;
import com.mucanze.algafood.api.model.output.view.RestauranteView;
import com.mucanze.algafood.domain.exception.EntidadeInexistenteException;
import com.mucanze.algafood.domain.exception.NegocioException;
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
	private RestauranteOutputModelAssembler restauranteOutputModelAssembler;
	
	@Autowired
	private RestauranteInputDisassembler restauranteInputDisassembler;
	
	/*@Autowired
	private SmartValidator validator;*/
	
	@GetMapping
	public List<RestauranteOutputModel> listar() {
		return restauranteOutputModelAssembler.toCollectionModel(restauranteRepository.findAll());
	}
	
	@JsonView(RestauranteView.Resumo.class)
	@GetMapping(params = "projeccao=resumo")
	public List<RestauranteOutputModel> listarResumido() {
		return listar();
	}
	
	@GetMapping("/{id}")
	public RestauranteOutputModel buscarPorId(@PathVariable Long id) {
		
		Restaurante restaurante = cadastroRestauranteService.buscarPorId(id);
		
		return restauranteOutputModelAssembler.toModel(restaurante);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RestauranteOutputModel salvar(@RequestBody @Valid RestauranteInputModel restauranteInputModel) {
		Restaurante restaurante = restauranteInputDisassembler.toDomainObject(restauranteInputModel);
		
		return restauranteOutputModelAssembler.toModel(cadastroRestauranteService.salvar(restaurante));
	}

	@PutMapping("/{id}")
	public RestauranteOutputModel actualizar(@RequestBody @Valid RestauranteInputModel restauranteInputModel, @PathVariable Long id) {
		//Restaurante restaurante = restauranteInputDisassembler.toDomainObject(restauranteInputModel);
		
		Restaurante restauranteRetornado = cadastroRestauranteService.buscarPorId(id);
		
		restauranteInputDisassembler.copyToDomainObject(restauranteInputModel, restauranteRetornado);
		
		restauranteRetornado = cadastroRestauranteService.actualizar(restauranteRetornado);
		
		return restauranteOutputModelAssembler.toModel(restauranteRetornado);	
	}
	
	@PutMapping("/{id}/activo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void activar(@PathVariable Long id) {
		try {
			cadastroRestauranteService.activar(id);
		} catch(EntidadeInexistenteException e) {
			throw new NegocioException(e.getMessage());
		}
	}
	
	@PutMapping("/{id}/abertura")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void abrir(@PathVariable Long id) {
		try {
			cadastroRestauranteService.abrir(id);
		} catch (EntidadeInexistenteException e) {
			throw new NegocioException(e.getMessage());
		}
	}
	
	@PutMapping("/activacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void activarEmMassa(@RequestBody List<Long> restaurantesId) {
		try {
			cadastroRestauranteService.activarEmMassa(restaurantesId);
		} catch (EntidadeInexistenteException e) {
			throw new NegocioException(e.getMessage());
		}
		
	}
	
	@DeleteMapping("/activacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inactivarEmMassa(@RequestBody List<Long> restaurantesId) {
		try {
			cadastroRestauranteService.inactivarEmMassa(restaurantesId);
		} catch (NegocioException e) {
			throw new NegocioException(e.getMessage());
		}
	}
	
	@DeleteMapping("/{id}/fechamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void fechar(@PathVariable Long id) {
		try {
			cadastroRestauranteService.fechar(id);
		} catch (EntidadeInexistenteException e) {
			throw new NegocioException(e.getMessage());
		}
	}
	
	@DeleteMapping("/{id}/inactivo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inactivar(@PathVariable Long id) {
		try {
			cadastroRestauranteService.inactivar(id);
		} catch (EntidadeInexistenteException e) {
			throw new NegocioException(e.getMessage());
		}
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		try {
			cadastroRestauranteService.remover(id);
		} catch (EntidadeInexistenteException e) {
			throw new NegocioException(e.getMessage());
		}
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

}
