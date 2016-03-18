package com.ffa.models;

import java.util.List;

public class Roster {

	private List<Player> activeRoster;
	private List<Player> benchedRoster;
	private List<Player> completeRoster;
	private int teamProjectedScore;
	
	public Roster() {
		// TODO Auto-generated constructor stub
	}
	
	public List<Player> getActiveRoster() {
		return activeRoster;
	}
	
	public void setActiveRoster(List<Player> activeRoster) {
		this.activeRoster = activeRoster;
	}
	
	public List<Player> getBenchedRoster() {
		return benchedRoster;
	}
	
	public void setBenchedRoster(List<Player> benchedRoster) {
		this.benchedRoster = benchedRoster;
	}
	
	public List<Player> getCompleteRoster() {
		return completeRoster;
	}
	
	public int getTeamProjectedScore() {
		return teamProjectedScore;
	}
}
