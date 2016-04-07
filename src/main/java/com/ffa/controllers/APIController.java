package com.ffa.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ffa.models.*;

import java.util.*;

@RestController
public class APIController {

	private static final String rest = "/rest/";
	
	@RequestMapping(rest + "NFLTeam")
    public NFLTeam team(@RequestParam(value="TeamID", defaultValue = "1") String id) {
        return new NFLTeam(Integer.parseInt(id));
    }
	
	@RequestMapping(rest + "Roster")
	public List<Roster> roster(
			@RequestParam(value="LeagueID", defaultValue = "1") String LeagueID,
			@RequestParam(value = "TeamID", defaultValue = "1") String TeamID,
			@RequestParam(value = "Week", defaultValue = "1") String Week,
			@RequestParam(value = "Year", defaultValue = "1") String Year
			) {
		Roster r = new Roster();
		return r.getRoster(LeagueID, TeamID, Week, Year);
	}
	
	//Fair Trade Judge stuffs
	@RequestMapping(rest + "LeagueTeams")
	public List<FtjStats> LeagueTeams(
			@RequestParam(value="LeagueID", defaultValue = "1682132") String LeagueID
			){
		FtjStats ftj = new FtjStats();
		return ftj.LeagueTeams(Integer.parseInt(LeagueID));
		
	}
	
	//Draft Buddy Service
	@RequestMapping(rest + "DraftBuddy")
	public List<List<Player>> Players(){
		DraftBuddy draftBuddy = new DraftBuddy();
		return draftBuddy.Players();
	}
	
	@RequestMapping(rest + "FTJ")
	public boolean FTJ(
			@RequestParam(value="PlayerID1", defaultValue = "1") String PlayerID1,
			@RequestParam(value="PlayerID2", defaultValue = "1") String PlayerID2
			){
		FtjStats ftj = new FtjStats();
		int temp1 = Integer.parseInt(PlayerID1);
		int temp2 = Integer.parseInt(PlayerID2);
		return ftj.isFair(Integer.parseInt(PlayerID1), Integer.parseInt(PlayerID2));
	}
	@RequestMapping(rest + "SmartRank")
	public List<SmartRankings> SmartRank(
			@RequestParam(value="LeagueID", defaultValue = "1") String LeagueID,
			@RequestParam(value="Week", defaultValue = "1") String Week
			){
		SmartRankings sr = new SmartRankings();
		return sr.getSmartRankingsList(Integer.parseInt(LeagueID), Integer.parseInt(Week));
		
	}
	
	
}
