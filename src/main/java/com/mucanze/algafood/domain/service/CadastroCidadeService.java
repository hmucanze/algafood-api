package com.mucanze.algafood.domain.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.mucanze.algafood.domain.exception.CidadeInexistenteException;
import com.mucanze.algafood.domain.exception.EntidadeEmUsoException;
import com.mucanze.algafood.domain.exception.EstadoInexistenteException;
import com.mucanze.algafood.domain.exception.NegocioException;
import com.mucanze.algafood.domain.model.Cidade;
import com.mucanze.algafood.domain.model.Estado;
import com.mucanze.algafood.domain.repository.CidadeRepository;

@Service
public class CadastroCidadeService {
	
	private static final String MSG_CIDADE_EM_USO =
			"Cidade de código %d não pode ser removido, pois está em uso.";

	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CadastroEstadoService cadastroEstadoService;
	
	public Cidade salvar(Cidade cidade) {
		try {
			Estado estado = cadastroEstadoService.buscarPorId(cidade.getEstado().getId());
			cidade.setEstado(estado);
			return cidadeRepository.save(cidade);
		} catch(EstadoInexistenteException e) {
			throw new NegocioException(
					String.format("Não existe estado com o identificador %d", cidade.getEstado().getId()));
		}
	}
	
	public Cidade actualizar(Cidade cidade, Long id) {
		Cidade cidadeRetornada = buscarPorId(id);
		
		BeanUtils.copyProperties(cidade, cidadeRetornada, "id");
		return salvar(cidadeRetornada);
	}
	
	public void remover(Long id) {
		try {
			cidadeRepository.deleteById(id);
		} catch(EmptyResultDataAccessException e) {
			throw new CidadeInexistenteException(id);
		} catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format(MSG_CIDADE_EM_USO, id));
		}
	}
	
	public Cidade buscarPorId(Long id) {
		return cidadeRepository.findById(id).orElseThrow(
				() -> new CidadeInexistenteException(id));
	}
	
}
