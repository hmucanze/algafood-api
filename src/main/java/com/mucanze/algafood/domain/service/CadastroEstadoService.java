package com.mucanze.algafood.domain.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.mucanze.algafood.domain.exception.EntidadeEmUsoException;
import com.mucanze.algafood.domain.exception.EstadoInexistenteException;
import com.mucanze.algafood.domain.model.Estado;
import com.mucanze.algafood.domain.repository.EstadoRepository;

@Service
public class CadastroEstadoService {
	
	private static final String MSG_ESTADO_EM_USO =
			"Estado de código %d não pode ser removido, pois está em uso.";
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	public Estado salvar(Estado estado) {
		return estadoRepository.save(estado);
	}
	
	public Estado actualizar(Estado estado, Long id) {
		Estado estadoRetornado = buscarPorId(id);
		
		BeanUtils.copyProperties(estado, estadoRetornado, "id");
		
		return salvar(estadoRetornado);
	}
	
	public void remover(Long id) {
		try {
			estadoRepository.deleteById(id);
		} catch(EmptyResultDataAccessException e) {
			throw new EstadoInexistenteException(id);
		} catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(MSG_ESTADO_EM_USO, id)
					);
		}
	}
	
	public Estado buscarPorId(Long id) {
		return estadoRepository.findById(id).orElseThrow(
				() -> new EstadoInexistenteException(id));
	}

}
