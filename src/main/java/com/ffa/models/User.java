package com.ffa.models;

import java.util.List;

public class User {

	private League defaultLeague;
	private String email;
	private Long id;
	private List<League> leagues;
	private List<LeagueTeam> userTeams;
	
	
	public User(){
		
	}
	
	public void addNewLeague(String leagueId){
		
	}
	
	public List<League> getLeagues() {
		return leagues;
	}
	
	public void addNewTeam(League currLeague){
		//TODO
	}
	
	public List<LeagueTeam> getUserTeams() {
		return userTeams;
	}
	
	public void setDefaultLeague(League defaultLeague) {
		this.defaultLeague = defaultLeague;
	}
	
	public League getDefaultLeague() {
		return defaultLeague;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
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
