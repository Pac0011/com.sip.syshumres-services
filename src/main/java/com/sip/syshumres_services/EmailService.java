package com.sip.syshumres_services;

import javax.mail.MessagingException;

import com.sip.syshumres_entities.PasswordRecovery;


public interface EmailService {
	
	public void configEmailService(String from, String linkRecovery);
	
	public void sendEmail(String to, String subject, String body);
	
	public void sendHtmlEmail(String to, String subject, String body) throws MessagingException;
	
	public String sendHtmlEmailRecoveryPassword(String to, String subject, PasswordRecovery entity) throws MessagingException;

}
