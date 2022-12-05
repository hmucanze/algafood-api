package com.mucanze.algafood.infrastruture.service.email;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FakeEnvioEmailService extends SmtpEnvioEmailService {

	@Override
	public void enviar(Mensagem mensagem) {
		String corpoEmailProcessado = processarCorpoEmail(mensagem);
		
		log.info("[FAKE-EMAIL] para: {}\n{}", mensagem.getDestinatarios(), corpoEmailProcessado);
	}
	
}
