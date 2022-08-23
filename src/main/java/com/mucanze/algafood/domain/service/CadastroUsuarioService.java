package com.mucanze.algafood.domain.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mucanze.algafood.domain.exception.NegocioException;
import com.mucanze.algafood.domain.exception.UsuarioInexistenteException;
import com.mucanze.algafood.domain.model.Usuario;
import com.mucanze.algafood.domain.repository.UsuarioRepository;

@Service
public class CadastroUsuarioService {
	
	private static final String MSG_USUARIO_EM_USO =
	"Usuário de código %d não pode ser removido, pois está em uso.";
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public Usuario salvar(Usuario usuario) {
	 	Optional<Usuario> usuarioRetornado = usuarioRepository.findByEmail(usuario.getEmail());
	 	
	 	if(usuarioRetornado.isPresent() && !usuarioRetornado.get().equals(usuario)) {
	 		throw new NegocioException(String.format("Já existe um usuário com email %s", usuario.getEmail()));
	 	}
	 	
		return usuarioRepository.save(usuario);
	}
	
	public Usuario actualizar(Usuario usuario, Long id) {
		Usuario usuarioRetornado = buscarPorId(id);
		
		BeanUtils.copyProperties(usuario, usuarioRetornado, "id", "dataCadastro");
		
		return salvar(usuarioRetornado);
	}
	
	@Transactional
	public void alterarSenha(String senhaActual, String senhaNova, Long id) {
		Usuario usuario = buscarPorId(id);
		
		if(usuario.senhaNaoCoincideCom(senhaActual)) {
			throw new NegocioException("Lamentamos, a senha actual informada não coincide com a senha do usuário");
		}
		
		usuario.setSenha(senhaNova);
	}
	
	public void remover(Long id) {
		try {
			usuarioRepository.deleteById(id);
		} catch(EmptyResultDataAccessException e) {
			throw new UsuarioInexistenteException(id);
		} catch(DataIntegrityViolationException e) {
			throw new NegocioException(
					String.format(MSG_USUARIO_EM_USO, id));
		}
		
	}
	
	public Usuario buscarPorId(Long id) {
		return usuarioRepository.findById(id).orElseThrow(
				() -> new UsuarioInexistenteException(id));
	}

}
