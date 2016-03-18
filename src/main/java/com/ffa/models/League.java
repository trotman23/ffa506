package com.ffa.models;

import java.time.LocalDateTime;
import java.util.List;

public class League {

	private LeagueRules leagueRules;
	private String leagueName;
	private List<LeagueTeam> leagueTeams;
	private List<Player> freeAgents;
	private List<User> leagueUsers;
	private LocalDateTime timeOfLastScrape;
	private List<Poll> polls;
	
	public League() {
		// TODO Auto-generated constructor stub
	}
	
	public League getLeague(){
		return this;
	}
	
	public List<Player> getFreeAgents() {
		return freeAgents;
	}
	
	public void setFreeAgents(List<Player> freeAgents) {
		this.freeAgents = freeAgents;
	}
	
	public String getLeagueName() {
		return leagueName;
	}
	
	public LeagueRules getLeagueRules() {
		return leagueRules;
	}
	
	public void setLeagueRules(LeagueRules leagueRules) {
		this.leagueRules = leagueRules;
	}
	
	public void setTriggerScrape(){
		
	}
	
	public List<LeagueTeam> getLeagueTeams() {
		return leagueTeams;
	}
	
	public List<User> getLeagueUsers() {
		return leagueUsers;
	}
	
	public List<Poll> getPolls() {
		return polls;
	}
	
	public void removePoll(Poll poll){
		
	}
	
	public void addPoll(Poll poll){
		this.polls.add(poll);
	}
	
	public LocalDateTime getTimeOfLastScrape() {
		return timeOfLastScrape;
	}
}
