package com.mucanze.algafood.domain.service;

import java.io.InputStream;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
		
		Optional<FotoProduto> fotoRetornada = produtoRepository.findFotoById(restauranteId, produtoId);
		
		if(fotoRetornada.isPresent()) {
			produtoRepository.delete(fotoRetornada.get());
		}
		
		String novoNomeArquivo = fotoStorageService.gerarNomeArquivo(fotoProduto.getNomeArquivo());
		
		fotoProduto.setNomeArquivo(novoNomeArquivo);
		fotoProduto = produtoRepository.save(fotoProduto);
		
		NovaFoto novaFoto = NovaFoto.builder()
				.nomeArquivo(fotoProduto.getNomeArquivo())
				.inputStream(inputStream)
				.build();
		
		fotoStorageService.armazenar(novaFoto);	
		
		return fotoProduto;
	}

}
