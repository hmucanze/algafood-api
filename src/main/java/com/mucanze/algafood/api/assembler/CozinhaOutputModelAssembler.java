package com.mucanze.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mucanze.algafood.api.model.output.CozinhaOutputModel;
import com.mucanze.algafood.domain.model.Cozinha;

@Component
public class CozinhaOutputModelAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public CozinhaOutputModel toModel(Cozinha cozinha) {
		return modelMapper.map(cozinha, CozinhaOutputModel.class);
	}
	
	public List<CozinhaOutputModel> toCollectionModel(List<Cozinha> cozinhas) {
		return cozinhas.stream()
				.map(cozinha -> toModel(cozinha))
				.collect(Collectors.toList());
	}

}
