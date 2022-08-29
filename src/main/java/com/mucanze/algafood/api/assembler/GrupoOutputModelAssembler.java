package com.mucanze.algafood.api.assembler;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mucanze.algafood.api.model.output.GrupoOutputModel;
import com.mucanze.algafood.domain.model.Grupo;

@Component
public class GrupoOutputModelAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public GrupoOutputModel toModel(Grupo grupo) {
		return modelMapper.map(grupo, GrupoOutputModel.class);
	}
	
	public List<GrupoOutputModel> toCollectionModel(Collection<Grupo> grupos) {
		return grupos.stream()
				.map(grupo -> toModel(grupo))
				.collect(Collectors.toList());
	} 

}
