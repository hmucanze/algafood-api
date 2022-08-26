package com.mucanze.algafood.domain.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.mucanze.algafood.domain.exception.NegocioException;
import com.mucanze.algafood.domain.exception.ProdutoInexistenteException;
import com.mucanze.algafood.domain.model.Produto;
import com.mucanze.algafood.domain.model.Restaurante;
import com.mucanze.algafood.domain.repository.ProdutoRepository;

@Service
public class CadastroProdutoService {
	
	private static final String MSG_PRODUTO_EM_USO =
			"Produto de código %d não pode ser removido, pois está em uso.";
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CadastroRestauranteService cadastroRestauranteService;
	
	public List<Produto> buscarPorRestauranteId(Long restauranteId) {
		Restaurante restaurante = cadastroRestauranteService.buscarPorId(restauranteId);
		
		return produtoRepository.findByRestaurante(restaurante);
	}
	
	public Produto salvar(Long restauranteId, Produto produto) {
		Restaurante restaurante = cadastroRestauranteService.buscarPorId(restauranteId);
		produto.setRestaurante(restaurante);
		
		return produtoRepository.save(produto);
	}
	
	public Produto actualizar(Long restauranteId, Long produtoId, Produto produto) {
		Produto produtoRetornado = buscarPorId(restauranteId, produtoId);
		
		BeanUtils.copyProperties(produto, produtoRetornado, "id", "restaurante");
		
		return salvar(restauranteId, produtoRetornado);
	}
	
	public void remover(Long restauranteId,Long produtoId) {
		try {
			produtoRepository.deleteById(produtoId);
		} catch(EmptyResultDataAccessException e) {
			throw new ProdutoInexistenteException(restauranteId, produtoId);
		} catch(DataIntegrityViolationException e) {
			throw new NegocioException(String.format(MSG_PRODUTO_EM_USO, produtoId));
		}
	}
	
	public Produto buscarPorId(Long restauranteId, Long produtoId) {
		return produtoRepository.findById(restauranteId, produtoId).orElseThrow(
				() ->  new ProdutoInexistenteException(restauranteId, produtoId));
	}

}
