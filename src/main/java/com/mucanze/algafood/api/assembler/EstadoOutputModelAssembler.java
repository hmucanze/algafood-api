package com.mucanze.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mucanze.algafood.api.model.output.EstadoOutputModel;
import com.mucanze.algafood.domain.model.Estado;

@Component
public class EstadoOutputModelAssembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public EstadoOutputModel toModel(Estado estado) {
		return modelMapper.map(estado, EstadoOutputModel.class);
	}
	
	public List<EstadoOutputModel> toCollectionModel(List<Estado> estados) {
		return estados.stream()
				.map(estado -> toModel(estado))
				.collect(Collectors.toList());
	}

}
