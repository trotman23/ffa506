package com.ffa;

import java.util.List; 
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;

import com.ffa.controllers.Application;
import com.ffa.models.League;
import com.ffa.models.LeagueTeam;
import com.ffa.models.User;

import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class UserApplicationTests {

	@Test
	public void contextLoads() {
	}
	
	
	//Test User Model
	@Test
	public void testUserModel(){
		//TODO: Should grab a mock user from database and use those values as expected
		final int EXPECTED_ID = 1;
		final String EXPECTED_NAME = "test";
		final String EXPECTED_EMAIL = "test@email.com";
		final String EXPECTED_PASSWORD = "password";

		User testUser = new User(EXPECTED_ID, EXPECTED_NAME, EXPECTED_EMAIL, EXPECTED_PASSWORD);

		assertEquals(EXPECTED_ID, testUser.getUserId());
		assertEquals(EXPECTED_NAME, testUser.getName());
        assertEquals(EXPECTED_EMAIL, testUser.getEmail());
        assertEquals(EXPECTED_PASSWORD, testUser.getPassword());
        
        System.out.println("User Model Tests Success.");
	}
	
	@Test
	public void testUserController() {
		//TODO: Test user controller code
	}
	
	@Test
	public void testUserService() {
		//TODO: test user service code
	}
}
