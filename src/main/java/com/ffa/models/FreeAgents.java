package com.ffa.models;

import java.util.ArrayList;
import java.util.List;

public class FreeAgents{
	public FreeAgents(){		
	}
	
	public List<Player> getTopFA(String Position){
		List<Player> FAs= new ArrayList<Player>();
		// run some sql shit
		Player testPlayer = new Player();
		testPlayer.Name = "Luke sucks at "+Position;
		FAs.add(testPlayer);
		Player testPlayer1 = new Player();
		testPlayer1.Name = "Luke really sucks at "+Position;
		FAs.add(testPlayer1);
		return FAs;
	}
	
}