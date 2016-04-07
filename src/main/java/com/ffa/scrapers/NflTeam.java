package com.ffa.scrapers;

public class NflTeam {
	public int NFLTeamID;
	public String NFLTeamName;
	public int Wins;
	public int Losses;
	public int Ties;
	public NflTeam(int id, String name, int w, int l, int t){
		this.NFLTeamID = id;
		this.NFLTeamName = name;
		this.Wins = w;
		this.Losses = l;
		this.Ties = t;
	}
	public NflTeam(){
		
	}
	
	@Override
	public String toString(){
		String teamString = "";
		teamString += "NFLTeamID: " + new Integer(this.NFLTeamID).toString() + "\n";
		teamString += "NFLTeamName: " + this.NFLTeamName + "\n";
		teamString += "Wins: " + new Integer(this.Wins).toString() + "\n";
		teamString += "Losses: " + new Integer(this.Losses).toString() + "\n";
		teamString += "Ties: " + new Integer(this.Ties).toString() + "\n";
		return teamString;
	}
}
