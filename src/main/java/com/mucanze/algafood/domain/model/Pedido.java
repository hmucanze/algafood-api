package com.mucanze.algafood.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.mucanze.algafood.domain.exception.NegocioException;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name="pedido")
public class Pedido {
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private BigDecimal subtotal;
	
	@Column(name = "taxa_frete")
	private BigDecimal taxaFrete;
	
	@Column(name = "valor_total")
	private BigDecimal valorTotal;
	
	@CreationTimestamp
	@Column(name = "data_criacao")
	private OffsetDateTime dataCriacao;
	
	@Column(name = "data_confirmacao")
	private OffsetDateTime dataConfirmacao;
	
	@Column(name = "data_cancelamento")
	private OffsetDateTime dataCancelamento;
	
	@Column(name = "data_entrega")
	private OffsetDateTime dataEntrega;
	
	@Enumerated(EnumType.STRING)
	private StatusPedido status = StatusPedido.CRIADO;
	
	@Embedded
	private Endereco enderecoEntrega;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "forma_pagamento_id", nullable = false)
	private FormaPagamento formaPagamento;
	
	@ManyToOne
	@JoinColumn(name = "restaurante_id", nullable = false)
	private Restaurante restaurante;
	
	@ManyToOne
	@JoinColumn(name = "usuario_cliente_id", nullable = false)
	private Usuario cliente;
	
	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
	private List<ItemPedido> itens;
	
	public void calcularValorTotal() {
		this.getItens().forEach(ItemPedido::calcularPrecoTotal);
		
		this.subtotal = getItens().stream()
				.map(item -> item.getPrecoTotal())
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		
		this.valorTotal = this.subtotal.add(this.taxaFrete);
	}
	
	public void definirTaxaFrete() {
		this.setTaxaFrete(this.getRestaurante().getTaxaFrete());
	}
	
	public void confirmar() {
		this.setStatus(StatusPedido.CONFIRMADO);
		this.setDataConfirmacao(OffsetDateTime.now());
	}
	
	public void entregar() {
		this.setStatus(StatusPedido.ENTREGUE);
		this.setDataEntrega(OffsetDateTime.now());
	}
	
	public void cancelar() {
		this.setStatus(StatusPedido.CANCELADO);
		this.setDataCancelamento(OffsetDateTime.now());
	}
	
	private void setStatus(StatusPedido novoStatus) {
		if(this.getStatus().naoPodeAlterarPara(novoStatus)) {
			if(this.getStatus().equals(novoStatus)) {
				throw new NegocioException(
						String.format("O Status do pedido %d não pode alterar para o status %s pois o pedido já está %s",
								this.getId(),
								novoStatus.getDescricao(),
								this.getStatus().getDescricao()));
			}
			throw new NegocioException(
					String.format("Pedido de identificador %d não pode transitar de status %s para status %s",
					this.getId(),
					this.getStatus().getDescricao(),
					novoStatus.getDescricao()));
		}
		this.status = novoStatus;
	}
	
}
