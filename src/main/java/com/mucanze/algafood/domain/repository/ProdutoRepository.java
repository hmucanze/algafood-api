package com.mucanze.algafood.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mucanze.algafood.domain.model.FotoProduto;
import com.mucanze.algafood.domain.model.Produto;
import com.mucanze.algafood.domain.model.Restaurante;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>, ProdutoRepositoryQueries {
	
	@Query("from Produto where restaurante.id = :restauranteId and id = :produtoId")
	public Optional<Produto> findById(@Param("restauranteId") Long restauranteId, @Param("produtoId") Long produtoId);
	
	public List<Produto> findByRestaurante(Restaurante restaurante);
	
	@Query("from Produto p where p.activo = true and p.restaurante = :restaurante")
	public List<Produto> findActivoByRestaurante(Restaurante restaurante);
	
	@Query("Select f from FotoProduto f join f.produto p "
			+ "where p.restaurante.id = :restauranteId and f.produto.id = :produtoId")
	public Optional<FotoProduto> findFotoById(Long restauranteId, Long produtoId);

}
