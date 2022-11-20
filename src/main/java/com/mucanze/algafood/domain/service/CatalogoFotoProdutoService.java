package com.mucanze.algafood.domain.service;

import java.io.InputStream;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mucanze.algafood.domain.exception.FotoProdutoInexistenteException;
import com.mucanze.algafood.domain.model.FotoProduto;
import com.mucanze.algafood.domain.repository.ProdutoRepository;
import com.mucanze.algafood.domain.service.FotoStorageService.NovaFoto;

@Service	
public class CatalogoFotoProdutoService {
		
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private FotoStorageService fotoStorageService;
	
	@Transactional
	public FotoProduto salvar(FotoProduto fotoProduto, InputStream inputStream) {
		Long restauranteId = fotoProduto.getRestauranteId();
		Long produtoId = fotoProduto.getProduto().getId();
		String nomeFotoExistente = null;
		
		Optional<FotoProduto> fotoRetornada = produtoRepository.findFotoById(restauranteId, produtoId);
		
		if(fotoRetornada.isPresent()) {
			nomeFotoExistente = fotoRetornada.get().getNomeArquivo();
			produtoRepository.delete(fotoRetornada.get());
		}
		
		String novoNomeArquivo = fotoStorageService.gerarNomeArquivo(fotoProduto.getNomeArquivo());
		
		fotoProduto.setNomeArquivo(novoNomeArquivo);
		fotoProduto = produtoRepository.save(fotoProduto);
		produtoRepository.flush();
		
		NovaFoto novaFoto = NovaFoto.builder()
				.nomeArquivo(fotoProduto.getNomeArquivo())
				.contentType(fotoProduto.getContentType())
				.inputStream(inputStream)
				.build();
		
		fotoStorageService.substituir(nomeFotoExistente, novaFoto);
		
		return fotoProduto;
	}
	
	@Transactional
	public void remover(Long restauranteId, Long produtoId) {
		FotoProduto fotoProduto = buscarPorId(restauranteId, produtoId);
		
		produtoRepository.delete(fotoProduto);
		produtoRepository.flush();
		
		fotoStorageService.remover(fotoProduto.getNomeArquivo());
	}

	public FotoProduto buscarPorId(Long restauranteId, Long produtoId) {
		return produtoRepository.findFotoById(restauranteId, produtoId)
				.orElseThrow(()-> new FotoProdutoInexistenteException(restauranteId, produtoId));
	}

	

}
