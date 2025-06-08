package com.mucanze.algafood.core.openapi;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import com.fasterxml.classmate.TypeResolver;
import com.mucanze.algafood.api.exceptionHandler.Problem;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Response;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@EnableOpenApi
public class SpringFoxConfig {
	
	@Bean
	public Docket apiDocket() {
		
		var typeResolver = new TypeResolver();
		
		return new Docket(DocumentationType.OAS_30)
				.select()
					.apis(RequestHandlerSelectors.basePackage("com.mucanze.algafood.api"))
					.paths(PathSelectors.any())
					.build()
				.useDefaultResponseMessages(false)
				.globalResponses(HttpMethod.GET, globalGetResponseMessages())
				.globalResponses(HttpMethod.POST, globalPostPutResponseMessages())
				.globalResponses(HttpMethod.PUT, globalPostPutResponseMessages())
				.globalResponses(HttpMethod.DELETE, globalDeleteResponseMessages())
				.additionalModels(typeResolver.resolve(Problem.class))
				.apiInfo(apiInfo())
				.tags(new Tag("Cidades", "Gerencia as Cidades"));
	}
	
	private List<Response> globalDeleteResponseMessages() {
		return Arrays.asList(
				new ResponseBuilder()
					.code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
					.description("Requisição inválida (erro do usuário)")
					.build(),
					
				new ResponseBuilder()
					.code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
					.description("Erro interno do servidor")
					.build()
				);
	}

	private List<Response> globalPostPutResponseMessages() {
		return Arrays.asList(
				new ResponseBuilder()
					.code(String.valueOf(HttpStatus.BAD_REQUEST.value()))
					.description("Requisição inválida (erro do usuário)")
					.build(),
					
				new ResponseBuilder()
					.code(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()))
					.description("O recurso não possui representação que possa ser aceita pelo consumidor")
					.build(),
					
				new ResponseBuilder()
					.code(String.valueOf(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()))
					.description("Requisição recusada porque o corpo está em um fromato não suportado")
					.build(),
					
				new ResponseBuilder()
					.code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
					.description("Erro interno do servidor")
					.build()
				);
	}

	private List<Response> globalGetResponseMessages() {
		return Arrays.asList(
				new ResponseBuilder()
					.code(String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()))
					.description("O recurso não possui representação que possa ser aceita pelo consumidor")
					.build(),
					
				new ResponseBuilder()
					.code(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
					.description("Erro interno do servidor")
					.build()
				);
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("Algafood API")
				.description("API desenvolvida para sistema de delivery de restaurantes")
				.version("1.0")
				.contact(new Contact("Algafood", "www.algafood.mucanze.com", "contacto@mucanze.com"))
				.build();
	}
	
}
