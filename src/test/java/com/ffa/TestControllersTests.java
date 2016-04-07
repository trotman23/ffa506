package com.ffa;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;

import com.ffa.controllers.APIController;
import com.ffa.controllers.Application;

import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.bind.annotation.RequestParam;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class TestControllersTests {
	
	@Test
	public void testAPIController(){
		APIController testContr = new APIController();
		String adrianPeterson = "1966936255";
		String eddieLacy= "1221912022";
		final String INTIAL_TEAM="1";
		final String INTIAL_WEEK="1";
		final String YEAR="2015";
		final String LEAGUE_ID="1682132";
		final String VIKINGS="26";

		
		//ftj
		assertFalse(testContr.FTJ(adrianPeterson, eddieLacy));
		//leagueTeams
		assertEquals(testContr.LeagueTeams(LEAGUE_ID).size(), 12);
		//rosters
		assertNotNull(testContr.roster(LEAGUE_ID, INTIAL_TEAM, INTIAL_WEEK, YEAR));
		//team
		assertNotNull(testContr.team(VIKINGS));
		//awards
		assertNotNull(testContr.Awards(LEAGUE_ID, INTIAL_WEEK));
		//compositeRank
		assertNotNull(testContr.CompositeRank(LEAGUE_ID, INTIAL_WEEK));
		//leagueInsult
		assertNotNull(testContr.LeagueInsult(INTIAL_TEAM));
		//leagueTeams
		assertNotNull(testContr.LeagueTeams(LEAGUE_ID));
		//draft buddy
		assertNotNull(testContr.DraftBuddy());
		//SmartRanking
		assertNotNull(testContr.SmartRank(LEAGUE_ID, INTIAL_WEEK));

	}
	
}