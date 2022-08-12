package com.mucanze.algafood.api.exceptionHandler;

import lombok.Getter;

@Getter
public enum ProblemType {
	
	DADOS_INVALIDOS("dados-invalidos", "Dados inválidos"),
	
	ERRO_DE_SISTEMA("erro-de-sistema", "Erro de sistema"),
	
	PARAMETRO_INVALIDO("parametro-invalido", "Parâmentro inválido"),
	
	RECURSO_INEXISTENTE_EXCEPTION("recurso-nao-encontrado", "Recurso não encontrado"),
	
	ENTIDADE_EM_USO_EXCEPTION("entidade-em-uso", "Entidade em uso"),
	
	NEGOCIO_EXCEPTION("erro-de-negocio", "Violação de regra de negócio"),
	
	MENSAGEM_INCOMPREENSIVEL("mensagem-incompreensivel", "Mensagem incompreensível");
	
	private String uri;
	private String title;
	
	ProblemType(String path, String title) {
		this.uri = "https://algafood.com/" + path;
		this.title = title;
	}
	
}
