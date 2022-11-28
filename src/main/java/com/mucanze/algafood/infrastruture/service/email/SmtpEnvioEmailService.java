package com.mucanze.algafood.infrastruture.service.email;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.mucanze.algafood.core.email.EmailProperties;
import com.mucanze.algafood.domain.service.EnvioEmailService;
import com.mucanze.algafood.infrastruture.service.email.exception.EmailException;

@Service
public class SmtpEnvioEmailService implements EnvioEmailService {

	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private EmailProperties emailProperties;
	
	@Override
	public void enviar(Mensagem mensagem) {
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
			helper.setFrom(emailProperties.getRemetente());
			helper.setTo(mensagem.getDestinatarios().toArray(new String[0]));
			helper.setSubject(mensagem.getAssunto());
			helper.setText(mensagem.getTexto(), true);
			
			mailSender.send(mimeMessage);
		} catch(Exception e) {
			throw new EmailException("Não foi possível enviar o email", e);
		}
	}

}
