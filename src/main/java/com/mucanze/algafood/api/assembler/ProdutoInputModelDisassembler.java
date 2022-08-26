package com.mucanze.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mucanze.algafood.api.model.input.ProdutoInputModel;
import com.mucanze.algafood.domain.model.Produto;

@Component
public class ProdutoInputModelDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Produto toDomainObject(ProdutoInputModel produtoInputModel) {
		return modelMapper.map(produtoInputModel, Produto.class);
	}

}
