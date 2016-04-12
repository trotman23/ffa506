package com.ffa.models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service("mailService")
public class EmailSend {
	 @Autowired
	    private MailSender mailSender;
	   
	 
	    /**
	     * This method will send compose and send the message 
	     * */
	    public void sendMail(String subject, String body) 
	    {
	        SimpleMailMessage message = new SimpleMailMessage();
	        message.setTo("ffa.506.help.request@gmail.com");
	        message.setSubject(subject);
	        message.setText(body);
	        mailSender.send(message);
	    }
	 
	


}
