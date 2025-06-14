package com.mucanze.algafood.api.controller;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mucanze.algafood.api.assembler.FormaPagamentoInputModelDisassembler;
import com.mucanze.algafood.api.assembler.FormaPagamentoOutputModelAssembler;
import com.mucanze.algafood.api.model.input.FormaPagamentoInputModel;
import com.mucanze.algafood.api.model.output.FormaPagamentoOutputModel;
import com.mucanze.algafood.domain.model.FormaPagamento;
import com.mucanze.algafood.domain.repository.FormaPagamentoRepository;
import com.mucanze.algafood.domain.service.CadastroFormaPagamentoService;

@RestController
@RequestMapping("/formas-pagamento")
public class FormaPagamentoController {
	
	@Autowired
	private FormaPagamentoRepository formaPagamentoRepository;
	
	@Autowired
	private FormaPagamentoOutputModelAssembler formaPagamentoOutputModelAssembler;
	
	@Autowired
	private FormaPagamentoInputModelDisassembler formaPagamentoInputModelDisassembler;
	
	@Autowired
	private CadastroFormaPagamentoService cadastroFormaPagamentoService;
	
	@GetMapping
	public ResponseEntity<List<FormaPagamentoOutputModel>> listar() {
		
		List<FormaPagamentoOutputModel> formasPagamentoOutputModel = formaPagamentoOutputModelAssembler
				.toCollectionModel(formaPagamentoRepository.findAll());
		
		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
				.body(formasPagamentoOutputModel);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<FormaPagamentoOutputModel> buscarPorId(@PathVariable Long id) {
		
		FormaPagamento formaPagamento = cadastroFormaPagamentoService.buscarPorId(id);
		FormaPagamentoOutputModel formaPagamentoOutputModel = formaPagamentoOutputModelAssembler
				.toModel(formaPagamento);
		
		return ResponseEntity.ok()
				.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
				.body(formaPagamentoOutputModel);
	}	
	
	@PostMapping
	public FormaPagamentoOutputModel salvar(@RequestBody @Valid FormaPagamentoInputModel formaPagamentoInputModel) {
		FormaPagamento formaPagamento = formaPagamentoInputModelDisassembler.toDomainModel(formaPagamentoInputModel);
		formaPagamento = cadastroFormaPagamentoService.salvar(formaPagamento);
		
		return formaPagamentoOutputModelAssembler.toModel(formaPagamento);
	}
	
	@PutMapping("/{id}")
	public FormaPagamentoOutputModel actualizar(@RequestBody @Valid FormaPagamentoInputModel formaPagamentoInputModel, @PathVariable Long id) {
		FormaPagamento formaPagamento = formaPagamentoInputModelDisassembler.toDomainModel(formaPagamentoInputModel);
		formaPagamento = cadastroFormaPagamentoService.actualizar(formaPagamento, id);
		
		return formaPagamentoOutputModelAssembler.toModel(formaPagamento);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		cadastroFormaPagamentoService.remover(id);
	}
	

}
