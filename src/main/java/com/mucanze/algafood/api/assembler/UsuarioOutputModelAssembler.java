package com.mucanze.algafood.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mucanze.algafood.api.model.output.UsuarioOutputModel;
import com.mucanze.algafood.domain.model.Usuario;

@Component
public class UsuarioOutputModelAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public UsuarioOutputModel toModel(Usuario usuario) {
		return modelMapper.map(usuario, UsuarioOutputModel.class);
	}
	
	public List<UsuarioOutputModel> toCollectionModel(Collection<Usuario> usuarios) {
		return usuarios.stream()
				.map(usuario -> toModel(usuario))
				.collect(Collectors.toList());
	}

}
