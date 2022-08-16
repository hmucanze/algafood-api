package com.mucanze.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mucanze.algafood.api.model.output.CidadeOutputModel;
import com.mucanze.algafood.domain.model.Cidade;

@Component
public class CidadeOutputModelAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public CidadeOutputModel toModel(Cidade cidade) {
	 	return modelMapper.map(cidade, CidadeOutputModel.class);
	}
	
	public List<CidadeOutputModel> toCollectionModel(List<Cidade> cidades) {
		return cidades.stream()
				.map(cidade -> toModel(cidade))
				.collect(Collectors.toList());
	}

}
