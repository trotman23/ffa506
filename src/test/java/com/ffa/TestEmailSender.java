package com.ffa;


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
	public void testAPIController(){
		EmailSend ms = new EmailSend();
		ms.sendMail("Test using juint", "joey");
		
	
		
	}
	
	
}