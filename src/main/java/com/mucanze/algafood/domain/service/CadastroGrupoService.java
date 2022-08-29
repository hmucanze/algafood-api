package com.mucanze.algafood.domain.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mucanze.algafood.domain.exception.EntidadeEmUsoException;
import com.mucanze.algafood.domain.exception.GrupoInexistenteException;
import com.mucanze.algafood.domain.model.Grupo;
import com.mucanze.algafood.domain.model.Permissao;
import com.mucanze.algafood.domain.repository.GrupoRepository;

@Service
public class CadastroGrupoService {
	
	private static final String MSG_GRUPO_EM_USO =
			"Grupo de código %d não pode ser removido, pois está em uso.";
	
	@Autowired
	private GrupoRepository grupoRepository;
	
	@Autowired
	private CadastroPermissaoService cadastroPermissaoService;
	
	public Grupo salvar(Grupo grupo) {
		return grupoRepository.save(grupo);
	}
	
	public Grupo actualizar(Grupo grupo, Long id) {
		Grupo grupoRetornado = buscarPorId(id);
		
		BeanUtils.copyProperties(grupo, grupoRetornado, "id");
		
		return salvar(grupoRetornado);
	}
	
	public void remover(Long id) {
		try {
			grupoRepository.deleteById(id);
		} catch(EmptyResultDataAccessException e) {
			throw new GrupoInexistenteException(id);
		} catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_GRUPO_EM_USO, id));
		}
	}
	
	@Transactional
	public void associarPermissao(Long grupoId, Long permissaoId) {
		Grupo grupo = buscarPorId(grupoId);
		
		Permissao permissao = cadastroPermissaoService.buscarPorId(permissaoId);
		
		grupo.associarPermissao(permissao);
	}
	
	@Transactional
	public void desassociarPermissoa(Long grupoId, Long PermissaoId) {
		Grupo grupo = buscarPorId(grupoId);
		
		Permissao permissao = cadastroPermissaoService.buscarPorId(PermissaoId);
		
		grupo.desassociarPermissao(permissao);
	}
	
	public Grupo buscarPorId(Long id) {
		return grupoRepository.findById(id).orElseThrow(
				() -> new GrupoInexistenteException(id));
	}
	
}
