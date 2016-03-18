package com.ffa.models;

import java.util.List;

public class LeagueTeam {

	private String teamId;
	private String userId;
	private Roster roster;
	private List<Award> awards;
	
	public LeagueTeam() {
		// TODO Auto-generated constructor stub
	}
	
	public LeagueTeam getLeagueTeam(){
		return this;
	}
	
	public List<Award> getAwards() {
		return awards;
	}
	
	public void setAwards(Award award) {
		//TODO
		this.awards.add(award);
	}
	
	public Roster getRoster() {
		return roster;
	}
	
	public void setRoster(Roster roster) {
		this.roster = roster;
	}
	
	public String getTeamId() {
		return teamId;
	}
	
	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
