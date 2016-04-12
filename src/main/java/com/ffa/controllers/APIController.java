package com.ffa.controllers;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ffa.models.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.*;

@RestController
public class APIController {

	private static final String rest = "/rest/";

	@RequestMapping(rest + "NFLTeam")
	public NFLTeam team(@RequestParam(value="TeamID") String id) {
		return new NFLTeam(Integer.parseInt(id));
	}


	@RequestMapping(rest + "Roster")
	public List<Roster> roster(
			@RequestParam(value="LeagueID") String LeagueID,
			@RequestParam(value = "TeamID") String TeamID,
			@RequestParam(value = "Week") String Week,
			@RequestParam(value = "Year") String Year
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
	public List<Player> DraftBuddy(){
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




	@RequestMapping(rest + "LeagueInsult")
	public   Map<String,String> LeagueInsult(
			@RequestParam(value = "TeamID", defaultValue = "1") String TeamID
			){
		InsultGenerator inst = new InsultGenerator(TeamID);
		Map<String,String> map = new HashMap<String,String>();
		map.put("key", inst.getInsults() );
		return map;
	}

	@RequestMapping(rest + "Awards")
	public String Awards(
			@RequestParam(value="LeagueID", defaultValue = "1") String LeagueID,
			@RequestParam(value="Week", defaultValue = "1") String Week
			){
		//stuff is being weird with serializing the Award object, going to try and use jackson
		Award a =  new Award(Integer.parseInt(LeagueID), Integer.parseInt(Week));
		ObjectMapper mapper = new ObjectMapper();
		String json = "";
		try {
			json = mapper.writeValueAsString(a);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}

	@RequestMapping(rest + "CompositeRank")
	public List<CompositeRankings> CompositeRank(
			@RequestParam(value="LeagueID", defaultValue = "1") String LeagueID,
			@RequestParam(value="Week", defaultValue = "1") String Week
			){
		CompositeRankings cr = new CompositeRankings();
		return cr.getCompositeRankings(Integer.parseInt(LeagueID), Integer.parseInt(Week));	
	}


	@RequestMapping(rest + "WavierWireAid")
	public List<Player> WavierWireAid(
			@RequestParam(value="Position", defaultValue = "QB") String Position
			){

		FreeAgents fa = new FreeAgents();
		return fa.getTopFA(Position);
	}


	@RequestMapping(value = rest + "InsertPoll", method = RequestMethod.PUT)
	public boolean InsertPoll(
			@RequestBody List<Poll> p
			){
		if(PollHelper.pollExists(p)){
			return false;
		} else {
			PollHelper.insertPoll(p);
			return true;
		}

	}


}
