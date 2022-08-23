package com.mucanze.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mucanze.algafood.api.model.input.GrupoInputModel;
import com.mucanze.algafood.domain.model.Grupo;

@Component
public class GrupoInputModelDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Grupo toDomainObject(GrupoInputModel grupoInputModel) {
		return modelMapper.map(grupoInputModel, Grupo.class);
	}

}
