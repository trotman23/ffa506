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
		final Long EXPECTED_ID = (long) 1;
		final String EXPECTED_EMAIL = "test@email.com";
		final String EXPECTED_PASSWORD = "password";
		final League EXPECTED_DEFAULT_LEAGUE = null;
		final List<League> EXPECTED_LEAGUES = null;
		final List<LeagueTeam> EXPECTED_USER_TEAMS = null;

		User testUser = new User(EXPECTED_ID, EXPECTED_EMAIL, EXPECTED_PASSWORD,
				EXPECTED_DEFAULT_LEAGUE, EXPECTED_LEAGUES, EXPECTED_USER_TEAMS);

		assertEquals(EXPECTED_ID, testUser.getId());
        assertEquals(EXPECTED_EMAIL, testUser.getEmail());
        assertEquals(EXPECTED_PASSWORD, testUser.getPassword());
        assertEquals(EXPECTED_DEFAULT_LEAGUE, testUser.getDefaultLeague());
        assertEquals(EXPECTED_LEAGUES, testUser.getLeagues());
        assertEquals(EXPECTED_USER_TEAMS, testUser.getUserTeams());
        
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
