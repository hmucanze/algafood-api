package com.mucanze.algafood.infrastruture.service.storage;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.mucanze.algafood.core.storage.StorageProperties;
import com.mucanze.algafood.domain.service.FotoStorageService;
import com.mucanze.algafood.infrastruture.service.storage.exception.StorageException;

@Service
public class S3FotoStorageService implements FotoStorageService {
	
	@Autowired
	private AmazonS3 amazonS3;
	
	@Autowired
	private StorageProperties storageProperties;

	@Override
	public void armazenar(NovaFoto novaFoto) {
		try {
			String caminhoArquivo = getCaminhoArquivo(novaFoto.getNomeArquivo());
			
			var objectMetadata = new ObjectMetadata();
			
			var putObjectRequest = new PutObjectRequest(
					storageProperties.getS3().getNomeBucket(),
					caminhoArquivo,
					novaFoto.getInputStream(),
					objectMetadata)
				.withCannedAcl(CannedAccessControlList.PublicRead);
			
			amazonS3.putObject(putObjectRequest);			
		} catch(Exception e) {
			throw new StorageException("Não foi possível enviar o arquivo para amazon S3.", e);
		}
		
	}


	@Override
	public void remover(String nomeFoto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public InputStream recuperar(String nomeFoto) {
		// TODO Auto-generated method stub
		return null;
	}

	private String getCaminhoArquivo(String nomeArquivo) {
		return String.format("%s/%s",
				storageProperties.getS3().getDirectorioFotos(), nomeArquivo);
	}
}
