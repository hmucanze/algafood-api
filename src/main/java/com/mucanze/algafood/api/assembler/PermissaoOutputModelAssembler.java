package com.mucanze.algafood.api.assembler;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mucanze.algafood.api.model.output.PermissaoOutputModel;
import com.mucanze.algafood.domain.model.Permissao;

@Component
public class PermissaoOutputModelAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	public PermissaoOutputModel toModel(Permissao permissao) {
		return modelMapper.map(permissao, PermissaoOutputModel.class);
	}
	
	public List<PermissaoOutputModel> toCollectionModel(Set<Permissao> permissoes) {
		return permissoes.stream()
				.map(permissao -> toModel(permissao))
				.collect(Collectors.toList());
	}

}
