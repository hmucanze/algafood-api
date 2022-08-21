package com.mucanze.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mucanze.algafood.domain.exception.EntidadeEmUsoException;
import com.mucanze.algafood.domain.exception.EntidadeInexistenteException;
import com.mucanze.algafood.domain.exception.NegocioException;
import com.mucanze.algafood.domain.exception.RestauranteInexistenteException;
import com.mucanze.algafood.domain.model.Cidade;
import com.mucanze.algafood.domain.model.Cozinha;
import com.mucanze.algafood.domain.model.Restaurante;
import com.mucanze.algafood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {
	
	private static final String MSG_CIDADE_EM_USO =
			"Restaurante de código %d não pode ser removido, pois está em uso.";

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CadastroCozinhaService cadastroCozinhaService;
	
	@Autowired
	private CadastroCidadeService cadastroCidadeService;
	
	public Restaurante salvar(Restaurante restaurante) {
		try {
			Cozinha cozinha = cadastroCozinhaService.buscarPorId(restaurante.getCozinha().getId());
			restaurante.setCozinha(cozinha);
			
			Cidade cidade = cadastroCidadeService.buscarPorId(restaurante.getEndereco().getCidade().getId());
			restaurante.getEndereco().setCidade(cidade);
			return restauranteRepository.save(restaurante);
		} catch(EntidadeInexistenteException e) {
			throw new NegocioException(e.getMessage());
					//String.format("Não existe cadastro de cozinha com o identificador %d", restaurante.getCozinha().getId()));
		}
	}
	
	public Restaurante actualizar(Restaurante restaurante) {
		//Restaurante restauranteRetornado = buscarPorId(id);
		
		//BeanUtils.copyProperties(restaurante, restauranteRetornado, "id", "formasPagamento", "dataCadastro");
		
		return salvar(restaurante);
	}
	
	@Transactional
	public void activar(Long restauranteId) {
		Restaurante restaurante = buscarPorId(restauranteId);
		restaurante.activar();
	}
	
	
	@Transactional
	public void inactivar(Long restauranteId) {
		Restaurante restaurante = buscarPorId(restauranteId);
		restaurante.inactivar();
	}
	
	public void remover(Long id) {
		try {
			restauranteRepository.deleteById(id);
		} catch(EmptyResultDataAccessException e) {
			throw new RestauranteInexistenteException(id);
		} catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(MSG_CIDADE_EM_USO, id));
		}
	}
	
	public Restaurante buscarPorId(Long id) {
		return restauranteRepository.findById(id).orElseThrow(
				() -> new RestauranteInexistenteException(id));
	}

}
