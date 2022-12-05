package com.mucanze.algafood.infrastruture.service.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.mucanze.algafood.core.email.EmailProperties;
import com.mucanze.algafood.domain.service.EnvioEmailService;
import com.mucanze.algafood.infrastruture.service.email.exception.EmailException;

import freemarker.template.Configuration;
import freemarker.template.Template;

//@Service
public class SmtpEnvioEmailService implements EnvioEmailService {

	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private EmailProperties emailProperties;
	
	@Autowired
	private Configuration freemarkerConfig;
	
	@Override
	public void enviar(Mensagem mensagem) {
		try {
			MimeMessage mimeMessage = criarMimeMessage(mensagem);
			
			mailSender.send(mimeMessage);
		} catch(Exception e) {
			throw new EmailException("Não foi possível enviar o email", e);
		}
	}

	protected String processarCorpoEmail(Mensagem mensagem) {
		try {
			Template template = freemarkerConfig.getTemplate(mensagem.getTexto());
			
			return FreeMarkerTemplateUtils.processTemplateIntoString(template, mensagem.getVariaveis());
		} catch (Exception e) {
			throw new EmailException("Erro ao processar template de email", e);
		} 
	}

	protected MimeMessage criarMimeMessage(Mensagem mensagem) throws MessagingException {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		String corpoEmailProcessado = processarCorpoEmail(mensagem);
		
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
		helper.setFrom(emailProperties.getRemetente());
		helper.setTo(mensagem.getDestinatarios().toArray(new String[0]));
		helper.setSubject(mensagem.getAssunto());
		helper.setText(corpoEmailProcessado, true);
		
		return mimeMessage;
	}
}
