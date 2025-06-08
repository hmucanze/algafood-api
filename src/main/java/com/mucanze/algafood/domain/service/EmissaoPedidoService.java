package com.mucanze.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mucanze.algafood.domain.exception.NegocioException;
import com.mucanze.algafood.domain.exception.PedidoInexistenteException;
import com.mucanze.algafood.domain.model.Cidade;
import com.mucanze.algafood.domain.model.FormaPagamento;
import com.mucanze.algafood.domain.model.Pedido;
import com.mucanze.algafood.domain.model.Produto;
import com.mucanze.algafood.domain.model.Restaurante;
import com.mucanze.algafood.domain.repository.PedidoRepository;

@Service
public class EmissaoPedidoService {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private CadastroRestauranteService cadastroRestauranteService;
	
	@Autowired
	private CadastroFormaPagamentoService cadastroFormaPagamentoService;
	
	@Autowired
	private CadastroProdutoService cadastroProdutoService;
	
	@Autowired
	private CadastroCidadeService cadastroCidadeService;
	
	@Transactional
	public Pedido salvar(Pedido pedido) {
		validarPedido(pedido);
		
		validarItensDoPedido(pedido);	
		
		pedido.calcularValorTotal();
		
		return pedidoRepository.save(pedido);
	}

	private void validarItensDoPedido(Pedido pedido) {
		pedido.getItens().forEach(item -> {
			Produto produto = cadastroProdutoService.buscarPorId(pedido.getRestaurante().getId(), item.getProduto().getId());
			
			item.setPedido(pedido);
			item.setProduto(produto);
			item.definirPrecoUnitario();
		});
	}

	private void validarPedido(Pedido pedido) {
		Restaurante restaurante = cadastroRestauranteService.buscarPorId(pedido.getRestaurante().getId());
	
		FormaPagamento formaPagamento = cadastroFormaPagamentoService.buscarPorId(pedido.getFormaPagamento().getId());
		
		Cidade cidade = cadastroCidadeService.buscarPorId(pedido.getEnderecoEntrega().getCidade().getId());
		
		pedido.setRestaurante(restaurante);
		pedido.getEnderecoEntrega().setCidade(cidade);
		pedido.setFormaPagamento(formaPagamento);
		pedido.definirTaxaFrete();
		
		if(restaurante.naoAceitaFormaPagamento(formaPagamento)) {
			throw new NegocioException(String.format("Forma de pagamento '%s' não é aceita por esse restaurante.",
	                formaPagamento.getDescricao()));
		}
	}
	
	public Pedido buscarPorCodigo(String codigo) {
		return pedidoRepository.findByCodigo(codigo).orElseThrow(
				() -> new PedidoInexistenteException(codigo));
	}

}
