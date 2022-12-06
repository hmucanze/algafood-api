package com.mucanze.algafood.domain.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.mucanze.algafood.domain.event.PedidoCanceladoEvent;
import com.mucanze.algafood.domain.model.Pedido;
import com.mucanze.algafood.domain.service.EnvioEmailService;
import com.mucanze.algafood.domain.service.EnvioEmailService.Mensagem;

@Component
public class NotificacaoClientePedidoCanceladoListener {
	
	@Autowired
	private EnvioEmailService envioEmailService;
	
	@TransactionalEventListener
	public void aoCancelarPedido(PedidoCanceladoEvent event) {
		
		Pedido pedido = event.getPedido();
		
		Mensagem mensagem = Mensagem.builder()
				.assunto(pedido.getRestaurante().getNome() + " Pedido Cancelado")
				.destinatario(pedido.getCliente().getEmail())
				.texto("email_cancelamento_pedido.html")
				.variavel("pedido", pedido)
				.build();
		
		envioEmailService.enviar(mensagem);
	}

}
