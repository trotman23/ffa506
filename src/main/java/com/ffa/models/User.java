package com.ffa.models;

import java.util.List;

public class User {

	private Long id;
	private String email;
	private String password;
	private League defaultLeague;
	private List<League> leagues;
	private List<LeagueTeam> userTeams;
	
	
	public User(Long id, String email, String password, League defaultLeague,
			List<League> leagues, List<LeagueTeam> userTeams){
		this.id = id;
		this.email = email;
		this.password = password;
		this.defaultLeague = defaultLeague;
		this.leagues = leagues;
		this.userTeams = userTeams;
		
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
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void removeLeague(League league){
		
	}
	
	public void removeUserAccount(){
		
	}
}
