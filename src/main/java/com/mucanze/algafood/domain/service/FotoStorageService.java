package com.mucanze.algafood.domain.service;

import java.io.InputStream;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

public interface FotoStorageService {
	
	public void armazenar(NovaFoto novaFoto);
	
	public void remover(String nomeFoto);
	
	public InputStream recuperar(String nomeFoto);
	
	public default String gerarNomeArquivo(String nome) {
		return UUID.randomUUID().toString() + "-" + nome;
	}
	
	public default void substituir(String nomeFotoExistente, NovaFoto novaFoto) {
		this.armazenar(novaFoto);
		
		if(nomeFotoExistente != null) {
			this.remover(nomeFotoExistente);
		}
	}
	
	@Builder
	@Getter
	public class NovaFoto {
		private String nomeArquivo;
		private InputStream inputStream;
	}

}
