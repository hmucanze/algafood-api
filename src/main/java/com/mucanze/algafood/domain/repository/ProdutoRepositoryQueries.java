package com.mucanze.algafood.domain.repository;

import com.mucanze.algafood.domain.model.FotoProduto;

public interface ProdutoRepositoryQueries {
	
	public FotoProduto save(FotoProduto fotoProduto);
	
	public void delete(FotoProduto fotoProduto);

}
