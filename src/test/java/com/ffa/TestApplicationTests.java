package com.ffa;

import java.util.List; 
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;

import com.ffa.controllers.Application;
import com.ffa.models.FtjStats;
import com.ffa.models.Roster;

import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class TestApplicationTests {

	@Test
	public void contextLoads() {
	}

	
	//Test Fair Trade Judge Tool
	@Test
	public void testFTJ(){
		int adrianPeterson = 1966936255;
		int eddieLacy= 1221912022;

		FtjStats ftj = new FtjStats();

		//Test two players with point differential that will return unfair
		boolean unfair = ftj.isFair(adrianPeterson, eddieLacy);
		assertFalse(unfair);
		System.out.println("Unfair trade logic pass");

		//Test player being traded with himself should return fair as point 
		//total will be identical
		boolean fair = ftj.isFair(adrianPeterson, adrianPeterson);
		assertTrue(fair);
		System.out.println("Fair trade logic pass");

		unfair=ftj.isFair(0, eddieLacy);
		assertFalse(unfair);
		System.out.println("invaild player returns unfair");




	}

	//Sanity Check of Roster Values
	@Test
	public void testRosterSanityCheck(){
		//Testing Object
		Roster testRoster = new Roster();

		//Testing Constants
		final int ROSTER_SIZE=12;
		final int LEAGUE_SIZE=12;
		final int INTIAL_TEAM=1;
		final int INTIAL_WEEK=1;
		final int SEASON_LENGTH=16;
		final String YEAR="2015";
		final String League_ID="1682132";

		//Testing varibales
		int week;
		int team_id;
		List<Roster> tr;


		//Roster Class Sanity Check
		for(team_id=INTIAL_TEAM; team_id<LEAGUE_SIZE; team_id++){
			for(week=INTIAL_WEEK; week<=SEASON_LENGTH; week++){
				tr=testRoster.getRoster(League_ID, 
						Integer.toString(team_id),Integer.toString(week), YEAR);
				
				assertTrue( tr.size()>=0);

				for(Roster currPlayer: tr){
					assertNotNull(currPlayer);

					//Check valid values
					assertEquals(currPlayer.Teams_FFATeamID,team_id);
					assertEquals(currPlayer.Teams_Leagues_LeagueID, Integer.parseInt(League_ID));
					assertEquals(currPlayer.WeekID,week);
					assertEquals(currPlayer.SeasonID, Integer.parseInt(YEAR));

					//Dummy sanity check
					assertNotEquals(currPlayer.Players_PlayerID, 0);
					assertTrue(currPlayer.Teams_FFATeamID<=12 &&currPlayer.Teams_FFATeamID>0);
					assertNotEquals(currPlayer.Teams_Users_UserID, 0);
					assertNotNull(currPlayer.InjuryCode);
					assertNotNull(currPlayer.Slot);
					assertNotNull(currPlayer.Starter);
					assertNotEquals(currPlayer.PlayerID,0);
					assertNotNull(currPlayer.Name);
					assertNotNull(currPlayer.Position);
					assertNotNull(currPlayer.NFLTeamName);
					assertTrue(currPlayer.NFLTeam_NFLTeamID>0);
				}
			}

		}
		System.out.println("Sanity Check on Roster Class Passes");
	}

	//Check for a complete roster
	@Test
	public void testRosterCompleteRosterTest(){
		Roster testRoster = new Roster();
		List<Roster> tr;
		final String INTIAL_TEAM="1";
		final String INTIAL_WEEK="1";
		final String YEAR="2015";
		final String League_ID="1682132";

		tr=testRoster.getRoster(League_ID,INTIAL_TEAM,INTIAL_WEEK,YEAR);

		//test complete roster
		assertEquals(tr.get(0).Name, "Ryan Tannehill");
		assertEquals(tr.get(1).Name, "Eric Decker");
		assertEquals(tr.get(2).Name, "Jamaal Charles");
		assertEquals(tr.get(3).Name, "Giovani Bernard");
		assertEquals(tr.get(4).Name, "Dorial Green-Beckham");
		assertEquals(tr.get(5).Name, "Martavis Bryant");
		assertEquals(tr.get(6).Name, "Eric Ebron");
		assertEquals(tr.get(7).Name, "Randall Cobb");
		assertEquals(tr.get(8).Name,"Dan Bailey");
		assertEquals(tr.get(9).Name, "Roddy White");
		assertEquals(tr.get(10).Name, "Tom Brady");
		assertEquals(tr.get(11).Name, "Alshon Jeffery");
		System.out.println("Complete roster test passes");	
	}

	//Check for complete player
	@Test
	public void testCompletePlayer(){
		Roster testRoster = new Roster();
		List<Roster> tr;
		final String INTIAL_TEAM="1";
		final String INTIAL_WEEK="1";
		final String YEAR="2015";
		final String League_ID="1682132";

		tr=testRoster.getRoster(League_ID,INTIAL_TEAM,INTIAL_WEEK,YEAR);

		//test complete player
		Roster currPlayer= tr.get(0);
		assertEquals(currPlayer.Players_PlayerID, 31743725);
		assertEquals(currPlayer.Teams_FFATeamID,1);
		assertEquals(currPlayer.Teams_Users_UserID, 1);
		assertEquals(currPlayer.Teams_Leagues_LeagueID, 1682132);
		assertEquals(currPlayer.InjuryCode,"N");
		assertEquals(currPlayer.Slot,"Bench");
		assertEquals(currPlayer.WeekID,1);
		assertFalse(currPlayer.Starter);
		assertEquals(currPlayer.SeasonID, 2015);
		assertEquals(currPlayer.PlayerID,31743725);
		assertEquals(currPlayer.Name, "Ryan Tannehill");
		assertEquals(currPlayer.Position, "QB");
		assertEquals(currPlayer.NFLTeamName, "Miami Dolphins");
		assertEquals(currPlayer.NFLTeam_NFLTeamID, 4);
		System.out.println("Complete palyer test passes");
	}





}
