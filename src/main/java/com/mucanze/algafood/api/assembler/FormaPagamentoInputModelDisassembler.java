package com.mucanze.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mucanze.algafood.api.model.input.FormaPagamentoInputModel;
import com.mucanze.algafood.domain.model.FormaPagamento;

@Component
public class FormaPagamentoInputModelDisassembler {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public FormaPagamento toDomainModel(FormaPagamentoInputModel formaPagamentoInputModel) {
		return modelMapper.map(formaPagamentoInputModel, FormaPagamento.class);
	}

}
