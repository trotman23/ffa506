package com.ffa.models;

import java.util.List;

public class User {

	private League defaultLeague;
	private String email;
	private String userId;
	private List<League> leagues;
	
	public User(){
		
	}
	
	public void addNewLeague(String leagueId){
		
	}
	
	public List<League> getLeagues() {
		return leagues;
	}
	
	public void addNewTeam(League currLeague){
		
	}
	
	public List<LeagueTeam> getUserTeams(){
		
	}
	
	public void setDefaultLeague(League defaultLeague) {
		this.defaultLeague = defaultLeague;
	}
	
	public League getDefaultLeague() {
		return defaultLeague;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void removeLeague(League league){
		
	}
	
	public void removeUserAccount(){
		
	}
}
