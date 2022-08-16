package com.mucanze.algafood.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mucanze.algafood.api.model.output.FormaPagamentoOutputModel;
import com.mucanze.algafood.domain.model.FormaPagamento;

@Component
public class FormaPagamentoOutputModelAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public FormaPagamentoOutputModel toModel(FormaPagamento formaPagamento) {
		return modelMapper.map(formaPagamento, FormaPagamentoOutputModel.class);
	}
	
	public List<FormaPagamentoOutputModel> toCollectionModel(List<FormaPagamento> formasPagamento){
		return formasPagamento.stream()
				.map(formaPagamento -> toModel(formaPagamento))
				.collect(Collectors.toList());
	}
	
}
