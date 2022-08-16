package com.mucanze.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.mucanze.algafood.api.model.input.EstadoInputModel;
import com.mucanze.algafood.domain.model.Estado;

@Component
public class EstadoInputModelDisassembler {
	
	private ModelMapper modelMapper;
	
	public Estado toDomainModel(EstadoInputModel estadoInputModel) {
		return modelMapper.map(estadoInputModel, Estado.class);
	}

}
