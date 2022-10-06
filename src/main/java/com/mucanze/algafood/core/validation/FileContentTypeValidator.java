package com.mucanze.algafood.core.validation;

import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.web.multipart.MultipartFile;

public class FileContentTypeValidator implements ConstraintValidator<FileContentType, MultipartFile> {

	private List<String> allowedContentType;
	
	@Override
	public void initialize(FileContentType contentType) {
		this.allowedContentType = Arrays.asList(contentType.allowed());
	}
	
	@Override
	public boolean isValid(MultipartFile multipart, ConstraintValidatorContext context) {
		return multipart == null || allowedContentType.contains(multipart.getContentType());
	}

}
