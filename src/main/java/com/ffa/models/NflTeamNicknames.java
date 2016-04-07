package com.ffa.models;
import java.util.*;

public class NflTeamNicknames {

	Map<String, String> nicknames = new HashMap<String, String>();
	public NflTeamNicknames(){
		nicknames.put("NE", "New England Patriots");
		nicknames.put("NO", "New Orleans Saints" );
		nicknames.put("Atl", "Atlanta Falcons");
		nicknames.put("Phi", "Philadelphia Eagles");
		nicknames.put("NYG", "New York Giants");
		nicknames.put("Wsh", "Washington Redskins");
		nicknames.put("Min", "Minnesota Vikings");
		nicknames.put("Mia", "Miami Dolphins");
		nicknames.put("SF", "San Francisco 49ers");
		nicknames.put("Dal", "Dallas Cowboys");
		nicknames.put("Pit", "Pittsburgh Steelers");
		nicknames.put("Den", "Denver Broncos");
		nicknames.put("GB", "Green Bay Packers");
		nicknames.put("Ind", "Indianapolis Colts");
		nicknames.put("Sea", "Seattle Seahawks");
		nicknames.put("SD", "San Diego Chargers");
		nicknames.put("Ari", "Arizona Cardinals");
		nicknames.put("Ten", "Tennessee Titans");
		nicknames.put("Car", "Carolina Panthers");
		nicknames.put("Chi", "Chicago Bears");
		nicknames.put("Bal", "Baltimore Ravens");
		nicknames.put("Jax", "Jacksonville Jaguars");
		nicknames.put("StL", "St. Louis Rams");
		nicknames.put("Cin", "Cincinnati Bengals");
		nicknames.put("Cle", "Cleveland Browns");
		nicknames.put("Hou", "Houston Texans");
		nicknames.put("KC", "Kansas City Chiefs");
		nicknames.put("Oak", "Oakland Raiders");
		nicknames.put("Buf", "Buffalo Bills");
		nicknames.put("NYJ", "New York Jets");
		nicknames.put("Mia", "Miami Dolphins");
		nicknames.put("Det", "Detroit Lions");
		nicknames.put("TB", "Tampa Bay Buccaneers");
		
	}
	
	public Map<String, String> getNicknames(){
		return nicknames;
	}
}
