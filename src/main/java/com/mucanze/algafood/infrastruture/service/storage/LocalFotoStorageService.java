package com.mucanze.algafood.infrastruture.service.storage;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import com.mucanze.algafood.domain.service.FotoStorageService;
import com.mucanze.algafood.infrastruture.service.storage.exception.StorageException;

@Service
public class LocalFotoStorageService implements FotoStorageService {
	
	@Value("${algafood.storage.local.directorio-fotos}")
	private Path diretorioFotos;

	@Override
	public void armazenar(NovaFoto novaFoto) {
		try {
			Path arquivoPath = getArquivoPath(novaFoto.getNomeArquivo());
			FileCopyUtils.copy(novaFoto.getInputStream(), Files.newOutputStream(arquivoPath));
		} catch (Exception e) {
			throw new StorageException("Não foi possível armazenar o ficheiro da foto", e);
		}	
	}
	
	@Override
	public void remover(String nomeFoto) {
		try {
			Path arquivoPath = getArquivoPath(nomeFoto);
			Files.deleteIfExists(arquivoPath);
		} catch (Exception e) {
			throw new StorageException("Não foi possível remover o ficheiro da foto", e);
		}
	}
	
	@Override
	public InputStream recuperar(String nomeFoto) {
		try {
			Path arquivoPath = getArquivoPath(nomeFoto);
			return Files.newInputStream(arquivoPath);
		} catch (Exception e) {
			throw new StorageException("Não foi possível recuperar o ficheiro da foto", e);
		}
	}
	
	private Path getArquivoPath(String nomeArquivo) {
		return diretorioFotos.resolve(Path.of(nomeArquivo));
	}

}
