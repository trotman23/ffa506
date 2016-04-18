package com.ffa;


import static org.junit.Assert.*;

import javax.mail.MessagingException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;

import com.ffa.controllers.Application;
import com.ffa.models.EmailSend;

import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class TestEmailSender {
	
	@Test
	public void emailTest(){
		EmailSend ms = new EmailSend();
		boolean test;
			try {
				test =ms.sendMail("Test using juint", "joey");
			} catch (MessagingException e) {
				test=false;
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			assertTrue(test);
	
		
	}
	
	
}