package com.mucanze.algafood.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "foto_produto")
public class FotoProduto {
	
	@EqualsAndHashCode.Include
	@Id
	@Column(name = "produto_id")
	private Long id;
	
	@OneToOne
	@MapsId
	private Produto produto;
	
	@Column(name = "nome_arquivo")
	private String nomeArquivo;
	private String descricao;
	
	@Column(name = "content_type")
	private String contentType;
	private Long tamanho;
	
	public Long getRestauranteId() {
		if(this.produto != null) {
			return this.getProduto().getRestaurante().getId();
		}
		return null;
	}

}
