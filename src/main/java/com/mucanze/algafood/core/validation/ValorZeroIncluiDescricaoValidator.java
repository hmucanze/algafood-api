package com.mucanze.algafood.core.validation;

import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;

import org.springframework.beans.BeanUtils;

public class ValorZeroIncluiDescricaoValidator implements ConstraintValidator<ValorZeroIncluiDescricao, Object> {
	
	String valorField;
	String descricaoField;
	String descricaoObrigatoria;
	
	@Override
	public void initialize(ValorZeroIncluiDescricao constraintAnnotation) {
		this.valorField = constraintAnnotation.valorField();
		this.descricaoField = constraintAnnotation.descricaoField();
		this.descricaoObrigatoria = constraintAnnotation.descricaoObrigatoria();
	}

	@Override
	public boolean isValid(Object objectoValidacao, ConstraintValidatorContext context) {
		boolean valido = true;
		
		try {
			BigDecimal taxaFrete = (BigDecimal) BeanUtils.getPropertyDescriptor(objectoValidacao.getClass(), valorField)
					.getReadMethod().invoke(objectoValidacao);
			
			String descricao = (String) BeanUtils.getPropertyDescriptor(objectoValidacao.getClass(), descricaoField)
					.getReadMethod().invoke(objectoValidacao);
			
			if(taxaFrete != null && BigDecimal.ZERO.compareTo(taxaFrete) == 0 && descricao != null) {
				valido = descricao.toLowerCase().contains(descricaoObrigatoria.toLowerCase());
			}
			
			return valido;
		} catch (Exception e) {
			throw new ValidationException(e);
		} 
	}

}
