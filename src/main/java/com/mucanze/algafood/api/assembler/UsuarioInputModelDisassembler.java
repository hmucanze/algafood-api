package com.mucanze.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mucanze.algafood.api.model.input.UsuarioInputModel;
import com.mucanze.algafood.domain.model.Usuario;

@Component
public class UsuarioInputModelDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Usuario toDomainObject(UsuarioInputModel usuarioInputModel) {
		return modelMapper.map(usuarioInputModel, Usuario.class);
	}
	

}
