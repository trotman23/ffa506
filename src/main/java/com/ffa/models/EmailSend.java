package com.ffa.models;

import java.util.Properties;

import javax.mail.MessagingException;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service("mailService")
public class EmailSend {
	@Autowired
	private MailSender mailSender;


	/**
	 * This method will send compose and send the message 
	 * */
	public boolean sendMail(String subject, String body) throws MessagingException 
	{
		try {

		JavaMailSenderImpl mailSender = new JavaMailSenderImpl(); 
		mailSender.setHost("smtp.gmail.com");
		mailSender.setUsername("ffa.506.help.request@gmail.com");
		mailSender.setPort(465);
		mailSender.setPassword("myffa506");
		Properties prop = mailSender.getJavaMailProperties();
		//prop.put("mail.transport.protocol", "smtp");
		//prop.put("mail.smtp.port", "465");
		//prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable", "false");
		//prop.put("mail.debug", "true");
		//prop.put( "mail.smtp.quitwait", "false");
	    mailSender.setJavaMailProperties(prop);
		
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper mailMsg = new MimeMessageHelper(mimeMessage);
		mailMsg.setFrom("ffa.506.help.request@gmail.com");
		mailMsg.setTo("ffa.506.help.request@gmail.com");
		mailMsg.setSubject(subject);
		mailMsg.setText(body);
		mailSender.send(mimeMessage);
		} catch (MessagingException e) {
			return false;
		}
		return true;
		/*
		 SimpleMailMessage msg = new SimpleMailMessage();
	        msg.setTo("ffa.506.help.request@gmail.com");
	        msg.setSubject(subject);
	        msg.setText(body);
	        try{
	            this.mailSender.send(msg);
	        }
	        catch (MailException ex) {
	        	return false;
	        }
		
		return true;*/
	}




}
