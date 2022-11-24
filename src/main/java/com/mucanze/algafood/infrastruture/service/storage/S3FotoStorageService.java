package com.mucanze.algafood.infrastruture.service.storage;

import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.mucanze.algafood.core.storage.StorageProperties;
import com.mucanze.algafood.domain.service.FotoStorageService;
import com.mucanze.algafood.infrastruture.service.storage.exception.StorageException;

//@Service
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
			objectMetadata.setContentType(novaFoto.getContentType());
			
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
		try {
			String caminhoArquivo = getCaminhoArquivo(nomeFoto);
			
			var deleteObjectRequest = new DeleteObjectRequest(
					storageProperties.getS3().getNomeBucket(),
					caminhoArquivo
					);	
			
			amazonS3.deleteObject(deleteObjectRequest);
		} catch(Exception e) {
			throw new StorageException("Não foi possível remover o arquivo do amazon S3.", e);
		}
	}

	@Override
	public FotoRecuperada recuperar(String nomeFoto) {
		String caminhoArquivo = getCaminhoArquivo(nomeFoto);
		
		URL url = amazonS3.getUrl(
				storageProperties.getS3().getNomeBucket(),
				caminhoArquivo);
		
		return FotoRecuperada.builder()
				.url(url.toString())
				.build();
	}

	private String getCaminhoArquivo(String nomeArquivo) {
		return String.format("%s/%s",
				storageProperties.getS3().getDirectorioFotos(), nomeArquivo);
	}
}
