package com.mucanze.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mucanze.algafood.api.model.output.ProdutoOutputModel;
import com.mucanze.algafood.domain.model.Produto;

@Component
public class ProdutoOutputModelAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public ProdutoOutputModel toModel(Produto produto) {
		return modelMapper.map(produto, ProdutoOutputModel.class);
	}
	
	public List<ProdutoOutputModel> toCollectionModel(List<Produto> produtos) {
		return produtos.stream()
				.map(produto -> toModel(produto))
				.collect(Collectors.toList());
	}

}
