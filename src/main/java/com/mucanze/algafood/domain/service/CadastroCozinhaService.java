package com.mucanze.algafood.domain.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.mucanze.algafood.domain.exception.CozinhaInexistenteException;
import com.mucanze.algafood.domain.exception.EntidadeEmUsoException;
import com.mucanze.algafood.domain.model.Cozinha;
import com.mucanze.algafood.domain.repository.CozinhaRepository;

@Service
public class CadastroCozinhaService {
	
	private static final String MSG_COZINHA_EM_USO =
			"Cozinha de código %d não pode ser removido, pois está em uso.";
		
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	public Cozinha salvar(Cozinha cozinha) {
		return cozinhaRepository.save(cozinha);
	}
	
	public Cozinha actualizar(Cozinha cozinha, Long id) {
		Cozinha cozinhaRetornada = buscarPorId(id);
		
		BeanUtils.copyProperties(cozinha, cozinhaRetornada, "id");
		return this.salvar(cozinhaRetornada);
	}
	
	public void remover(Long id) {
		try {
			cozinhaRepository.deleteById(id);
		} catch(EmptyResultDataAccessException e) {
			throw new CozinhaInexistenteException(id);
			
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(MSG_COZINHA_EM_USO, id)
					);
		}
	}
	
	public Cozinha buscarPorId(Long id) {
		return cozinhaRepository.findById(id).orElseThrow(
				() -> new CozinhaInexistenteException(id));
	}

}
