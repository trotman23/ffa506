package com.ffa.models;

public class Player {

	public int PlayerID;
	public String Name;
	public String Position;
	public String NFLTeamName;
	public boolean Injured;
	public int NFLTeam_NFLTeamID;
	
	public Player(){

	}
	
	public Player(int playerID, String Name, String Position, String NFLTeamName, boolean Injured, int NFLTeam_NFLTeamID){
		this.PlayerID = playerID;
		this.Name = Name;
		this.Position = Position;
		this.NFLTeamName = NFLTeamName;
		this.Injured = Injured;
		this.NFLTeam_NFLTeamID = NFLTeam_NFLTeamID;
	}
	/*@Override
	public String toString(){
		String ts = "";
		ts += "PlayerID: " + new Integer(this.PlayerID).toString() + "\n";
		ts += "Name: " + this.Name + "\n";
		ts += "Position: " + this.Position + "\n";
		ts += "NFLTeamName: " + this.NFLTeamName + "\n";
		ts += "Injured: " + this.Injured + "\n";
		ts += "NFLTeam_NFL_TeamID: " + new Integer(this.NFLTeam_NFLTeamID).toString() + "\n";
		
		return ts;
	}*/
}
