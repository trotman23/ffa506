package com.ffa;

import java.util.List;

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

	@Test
	public void testFTJ(){
		int adrianPeterson = 1966936255;
		int eddieLacy= 1221912022;
		
		FtjStats ftj = new FtjStats();
		
		//Test two players with point differential that will return unfair
		boolean unfair = ftj.isFair(adrianPeterson, eddieLacy);
		assert(!unfair);
		System.out.println("Unfair trade logic pass");
		
		//Test player being traded with himself should return fair as point 
		//total will be identical
		boolean fair = ftj.isFair(adrianPeterson, adrianPeterson);
		assert(fair);
		System.out.println("Fair trade logic pass");

		unfair=ftj.isFair(0, eddieLacy);
		assert(!unfair);
		System.out.println("invaild player returns unfair");

		
		
				
	}
	
	@Test
	public void testRosterModel(){
		Roster testRoster = new Roster();
		List<Roster> tr=testRoster.getRoster("1682132", "1", "1", "2015");
		System.out.println(tr.get(0).Name);
		assert(tr.get(0).Name.equals("Ryan Tannehill"));
		assert(tr.get(0).NFLTeamName.equals("Miami Dolphins"));
		
		
		
		assert(tr.get(1).Name.equals("Eric Decker"));
		assert(tr.get(2).Name.equals("Jamaal Charles"));
		assert(tr.get(3).Name.equals("Giovani Bernard"));
		assert(tr.get(4).Name.equals("Dorial Green-Beckham"));
		assert(tr.get(5).Name.equals("Martavis Bryant"));
		assert(tr.get(6).Name.equals("Eric Ebron"));
		assert(tr.get(7).Name.equals("Randall Cobb"));
		assert(tr.get(8).Name.equals("Dan Bailey"));
		assert(tr.get(9).Name.equals("Roddy White"));
		assert(tr.get(10).Name.equals("Tom Brady"));
		assert(tr.get(11).Name.equals("Alshon Jeffery"));

		//assert(testRoster.)
		
	}
	
	
	
}
