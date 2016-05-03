package com.ffa.models;

import java.util.Random;

public class InsultGenerator {
final String[] INSULTS= {"This team drafts worst then the browns", "0-16??", 
		"Liblits team is better", "Browns should hire this you as their GM",
		"This team looks worst then Johnny Manziel after a weekend in vegas", 
		"This team lacks faith I would consider picking up Tim Tebow", 
		"Maybe this team should stick to European Football", "This isn't golf", 
		"This team is almost Trotty bad", "This team makes these insults look good",
		"This team makes Jay Cutler look good", "This team is like Peyton isn the playoffs",
		"This team should use auto-draft next year", "This team performs as well as Apple Maps",
		"I've seen better from Joel Stave", "This team should just move on to next year"};
	String teamID;
  public InsultGenerator(String teamID) {
		// TODO Auto-generated constructor stub
	  this.teamID=teamID;
	}


  
  public String getInsults(){
	Random rand= new Random(INSULTS.length);
	return INSULTS[rand.nextInt(INSULTS.length)];  
  }
	
	
}