package com.sip.syshumres_services;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sip.syshumres_entities.PasswordRecovery;


@Service
public class EmailServiceImpl implements EmailService {
	
    private JavaMailSender mailSender;
	
	@Value("${EMAIL.FROM}")
	private String from;
	
	@Value("${LINK.EMAIL.PASSWORD.RECOVERY}")
	private String linkRecovery;
	
	@Autowired
	public EmailServiceImpl(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	@Transactional(readOnly = true)
	@Override
    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }
    
	@Transactional(readOnly = true)
	@Override
    public void sendHtmlEmail(String to, String subject, String body) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        message.setFrom(new InternetAddress(this.from));
        message.setRecipients(MimeMessage.RecipientType.TO, to);
        message.setSubject(subject);
        message.setContent(body, "text/html; charset=utf-8");

        mailSender.send(message);
    }
	
	@Transactional(readOnly = true)
	@Override
    public String sendHtmlEmailRecoveryPassword(String to, String subject, PasswordRecovery entity) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        message.setFrom(new InternetAddress(this.from));
        message.setRecipients(MimeMessage.RecipientType.TO, to);
        message.setSubject(subject);
        
        String link = this.linkRecovery + entity.getUuid();
        String htmlContent = "<h1>De clic a la sig. liga, para que pueda restablecer su contraseña: </h1>" +
                             "<p><a href=\"" + link + "\" target=\"_blank\" rel=\"noopener noreferrer\">De clic aqui</a></p>";
        message.setContent(htmlContent, "text/html; charset=utf-8");

        mailSender.send(message);
        
        return link;
    }

}
