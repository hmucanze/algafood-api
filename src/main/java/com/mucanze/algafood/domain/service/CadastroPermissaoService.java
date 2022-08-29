package com.mucanze.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mucanze.algafood.domain.exception.PermissaoInexistenteException;
import com.mucanze.algafood.domain.model.Permissao;
import com.mucanze.algafood.domain.repository.PermissaoRepository;

@Service
public class CadastroPermissaoService {

	@Autowired
	private PermissaoRepository permissaoRepository;
	
	
	public Permissao buscarPorId(Long id) {
		return permissaoRepository.findById(id).orElseThrow(
				() -> new PermissaoInexistenteException(id));
	}
	
}
