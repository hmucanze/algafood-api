package com.mucanze.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mucanze.algafood.api.model.input.CidadeInputModel;
import com.mucanze.algafood.domain.model.Cidade;

@Component
public class CidadeInputDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Cidade toDomainObject(CidadeInputModel cidadeInputModel) {
		return modelMapper.map(cidadeInputModel, Cidade.class);
	}

}
