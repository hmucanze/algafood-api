package com.mucanze.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mucanze.algafood.api.model.output.FotoProdutoOutputModel;
import com.mucanze.algafood.domain.model.FotoProduto;

@Component
public class FotoProdutoOutputModelAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public FotoProdutoOutputModel toModel(FotoProduto fotoProduto) {
		return modelMapper.map(fotoProduto, FotoProdutoOutputModel.class);
	}

}
