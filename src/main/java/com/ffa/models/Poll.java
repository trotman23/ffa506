package com.ffa.models;


//this mimics the db table
public class Poll {
	private int ESPNLeagueID;
	private int FFALeagueID;
	private int ESPNTeamID;
	private int Week;
	private int RankTeamId;
	private int Rank;
	
	public Poll(){
		
	}
	
	public int getESPNLeagueID(){
		return this.ESPNLeagueID;
	}
	
	public void setESPNLeagueID(int lid){
		this.ESPNLeagueID = lid;
	}
	public int getFFALeagueID(){
		return this.FFALeagueID;
	}
	
	public void setFFALeagueID(int lid){
		this.FFALeagueID = lid;
	}
	public void setESPNTeamID(int id){
		this.ESPNTeamID = id;
	}
	public int getESPNTeamId(){
		return this.ESPNTeamID;
	}
	
	public void setWeek(int week){
		this.Week = week;
	}
	public int getWeek(){
		return this.Week;
	}
	
	public void setRankTeamId(int id){
		this.RankTeamId = id;
	}
	public int getRankTeamId(){
		return this.RankTeamId;
	}
	
	public void setRank(int rank){
		this.Rank = rank;
	}
	public int getRank(){
		return this.Rank;
	}
}
