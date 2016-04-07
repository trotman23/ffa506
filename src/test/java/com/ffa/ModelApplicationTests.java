package com.ffa;

import java.util.List; 
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;

import com.ffa.controllers.Application;
import com.ffa.models.*;

import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class ModelApplicationTests {
	
	
	//Awards Tests
	@Test
	public void testAward(){
		
		for(int week = 1; week < 17; week++){
			int leagueid = 1682132;
			Award a = new Award(leagueid, week);
			
			//not null
			assertNotNull(a.topOverallScore);
			assertNotNull(a.topOverallScoreTeam);
			assertNotNull(a.topOverallScoreOwner);
			assertNotNull(a.topScorerPlayerPoints);
			assertNotNull(a.topScorerPlayerName);
			assertNotNull(a.topScorerPlayerTeam);
			assertNotNull(a.topScorerPlayerOwner);
			assertNotNull(a.mostBenchPoints);
			assertNotNull(a.mostBenchPointsTeam);
			assertNotNull(a.mostBenchPointsOwner);
			assertNotNull(a.mostBenchPointsIndividualPlayerPoints);
			assertNotNull(a.mostBenchPointsIndividualPlayerName);
			assertNotNull(a.mostBenchPointsIndividualPlayerTeam);
			assertNotNull(a.mostBenchPointsIndividualPlayerOwner);
			//not equal to zero
			assertNotEquals(a.topOverallScore, 0);
			assertNotEquals(a.topOverallScoreTeam, 0);
			assertNotEquals(a.topOverallScoreOwner, 0);
			assertNotEquals(a.topScorerPlayerPoints, 0);
			assertNotEquals(a.topScorerPlayerName, 0);
			assertNotEquals(a.topScorerPlayerTeam, 0);
			assertNotEquals(a.topScorerPlayerOwner, 0);
			assertNotEquals(a.mostBenchPoints, 0);
			assertNotEquals(a.mostBenchPointsTeam, 0);
			assertNotEquals(a.mostBenchPointsOwner, 0);
			assertNotEquals(a.mostBenchPointsIndividualPlayerPoints, 0);
			assertNotEquals(a.mostBenchPointsIndividualPlayerName, 0);
			assertNotEquals(a.mostBenchPointsIndividualPlayerTeam, 0);
			assertNotEquals(a.mostBenchPointsIndividualPlayerOwner, 0);
		}
		
	}
	//testing rankings
	public void testRankingsRank(){
		for (int week = 1; week < 17; week++){
			Rankings r = new Rankings();
			List<Rankings> lr = r.Rank(1682132, week);
			for (Rankings temp : lr){
				assertNotNull(temp.teamID);
				assertNotNull(temp.teamName);
				assertNotNull(temp.ffaPoints);
			}
		}
		
	}
	public void testGetTeamWeeklySumScore(){
		for (int week = 1; week <= 17; week++){
			for (int teamID = 1; teamID <= 12; teamID++){
				Rankings r2 = new Rankings();
				int ffaPoints = r2.getTeamWeeklySumScore(1682132, week, teamID);
				assertNotEquals(ffaPoints, 0);
				assertNotNull(ffaPoints);
			}
		}
	}
	
	//Composite Rankings
	public void testCompositeRankings(){
		
	}
	
	
	//SmartRankings
	
	public void testSmartRankings(){
		
	}
}
