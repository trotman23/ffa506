package com.ffa;

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
		
		boolean unfair = ftj.isFair(adrianPeterson, eddieLacy);
		
		assert(!unfair);
		System.out.println("Unfair trade logic pass");
		//assert(unfair);
				
	}
	
	@Test
	public void testRosterModel(){
		Roster testRoster = new Roster();
		testRoster.getRoster("1", "1", "1", "2015");
		System.out.println(testRoster.Name);
		
	}
	
	
	
}
