package com.esoltecnologia.cursomc.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.esoltecnologia.cursomc.domain.Pedido;

public abstract class AbstractEmailService implements EmailService{
	
	@Value("${default.sender}") //pega o conteudo da chave no aplication.properties e atribue a sender
	private String sender;

	@Override
	public void sendOrderConfirmationEmail(Pedido obj) {
		
		SimpleMailMessage sm = prepareSimpleMailMessageFromPedido(obj);
		sendEmail(sm);
	}

	protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido obj) {

		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(obj.getCliente().getEmail()); 						//pra quem vai o email
		sm.setFrom(sender); 										//pra quem vai o email
		sm.setSubject("Pedido confirmado. Código: " + obj.getId()); //titulo do email
		sm.setSentDate(new Date(System.currentTimeMillis())); 		//data do email
		
		return sm;
	}
}
