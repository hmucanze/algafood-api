package com.mucanze.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mucanze.algafood.api.model.input.UsuarioComSenhaInputModel;
import com.mucanze.algafood.domain.model.Usuario;

@Component
public class UsuarioComSenhaInputModelDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Usuario toDomainObject(UsuarioComSenhaInputModel usuarioComSenhaInputModel) {
		return modelMapper.map(usuarioComSenhaInputModel, Usuario.class);
	}

}
