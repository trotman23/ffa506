package com.ffa.models;

import java.util.Random;

public class InsultGenerator {
final String[] INSULTS= {"insult 1", "insult 2", "insult 3", "insult 4"};
	String teamID;
  public InsultGenerator(String teamID) {
		// TODO Auto-generated constructor stub
	  this.teamID=teamID;

	}


  
  public String getInsults(){
	Random rand= new Random();
	return INSULTS[rand.nextInt(4)];  
  }
	
	
}
