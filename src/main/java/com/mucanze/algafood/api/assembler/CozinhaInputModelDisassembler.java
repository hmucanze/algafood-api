package com.mucanze.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mucanze.algafood.api.model.input.CozinhaInputModel;
import com.mucanze.algafood.domain.model.Cozinha;

@Component
public class CozinhaInputModelDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Cozinha toDomainObject(CozinhaInputModel cozinhaInputModel) {
		return modelMapper.map(cozinhaInputModel, Cozinha.class);
	}

}
