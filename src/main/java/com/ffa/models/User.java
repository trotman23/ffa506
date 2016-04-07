package com.ffa.models;

import java.util.List;

public class User {

	private int userId;
	private String name;
	private String email;
	private String password;
	/*
	private League defaultLeague;
	private List<League> leagues;
	private List<LeagueTeam> userTeams;
	*/
	
	public User() {
	}
	
	
	public User(int userId, String name, String email, String password){
		this.userId = userId;
		this.name = name;
		this.email = email;
		this.password = password;
		/*
		this.defaultLeague = defaultLeague;
		this.leagues = leagues;
		this.userTeams = userTeams;
		*/
		
	}
	
	public void addNewLeague(String leagueId){
		
	}
	/*
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
	*/
	
	public int getUserId() {
		return userId;
	}
	
	public void setId(int userId) {
		this.userId = userId;
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
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void removeLeague(League league){
		
	}
	
	public void removeUserAccount(){
		
	}
}
