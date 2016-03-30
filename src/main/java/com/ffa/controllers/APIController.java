package com.ffa.controllers;

import java.util.concurrent.atomic.AtomicLong;
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
}
