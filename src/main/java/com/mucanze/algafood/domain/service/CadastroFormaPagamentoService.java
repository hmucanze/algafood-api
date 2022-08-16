package com.mucanze.algafood.domain.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.mucanze.algafood.domain.exception.EntidadeEmUsoException;
import com.mucanze.algafood.domain.exception.FormaPagamentoInexistenteException;
import com.mucanze.algafood.domain.model.FormaPagamento;
import com.mucanze.algafood.domain.repository.FormaPagamentoRepository;

@Service
public class CadastroFormaPagamentoService {
	
	private static final String MSG_FORMA_PAGAMENTO_EM_USO =
			"Forma Pagamento de código %d não pode ser removido, pois está em uso.";
	
	@Autowired
	private FormaPagamentoRepository formaPagamentoRepository;
	
	public FormaPagamento salvar(FormaPagamento formaPagamento) {
		return formaPagamentoRepository.save(formaPagamento);
	}
	
	public FormaPagamento actualizar(FormaPagamento formaPagamento, Long id) {
		FormaPagamento formaPagamentoRetornada = buscarPorId(id);
		
		BeanUtils.copyProperties(formaPagamento, formaPagamentoRetornada, "id");
		
		return salvar(formaPagamentoRetornada);
	}
	
	public void remover(Long id) {
		try {
			formaPagamentoRepository.deleteById(id);
		} catch(EmptyResultDataAccessException e) {
			throw new FormaPagamentoInexistenteException(id);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_FORMA_PAGAMENTO_EM_USO, id));
		}
	}

	public FormaPagamento buscarPorId(Long id) {
		return formaPagamentoRepository.findById(id)
				.orElseThrow(() -> new FormaPagamentoInexistenteException(id));
	}

}
