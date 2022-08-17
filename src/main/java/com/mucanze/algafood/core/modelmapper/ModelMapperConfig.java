package com.mucanze.algafood.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mucanze.algafood.api.model.output.EnderecoOutputModel;
import com.mucanze.algafood.domain.model.Endereco;

@Configuration
public class ModelMapperConfig {
	
	@Bean
	public ModelMapper modelMapper() {
		var modelMapper = new ModelMapper();
		
		var enderecoToEnderecoOutputModel = modelMapper.createTypeMap(Endereco.class, EnderecoOutputModel.class);
		enderecoToEnderecoOutputModel.<String>addMapping(
				enderecoSrc -> enderecoSrc.getCidade().getEstado().getNome(),
				(enderecoDest, value) -> enderecoDest.getCidade().setEstado(value));
		
		return modelMapper;
	}

}
