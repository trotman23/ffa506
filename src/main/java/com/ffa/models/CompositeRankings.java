package com.ffa.models;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import com.ffa.controllers.DbSource;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class CompositeRankings extends Rankings implements Comparable<CompositeRankings>{

	public int cRank;
	public int cWins;
	public int cLosses;
	public int cTies;
	public int cWeek;
	public int cPoints;
	public double avgRank;
	public double avgWins;
	public double avgLosses;
	public double avgTies;

	//private final static int NUM_TEAMS = 12;
	//private final static int NUM_WEEKS = 17;

	public CompositeRankings(){

	}

	public List<CompositeRankings> populateCompositeRankingsRow(int LeagueID, int Week){
		List<CompositeRankings> lcr = new ArrayList<CompositeRankings>();
		//iterating through the weeks, this will be painfully slow, but i can't think of a
		//better way to write the SQL right now, so i'm just going for it
		Connection conn = null;
		PreparedStatement stmt = null;
		int score = 0;
		try {
			conn = DbSource.getDataSource().getConnection();
			String sql ="SELECT SUM(FantasyPointsScore), TeamName, u.Name, t.FFATeamID FROM weeklyscores ws " +
					"JOIN roster r ON r.Players_PlayerID = ws.Players_PlayerID " + 
					"JOIN teams t ON t.FFATeamId = r.Teams_FFATeamId " + 
					"JOIN users u ON u.UserID = t.Users_UserID " +
					"WHERE r.WeekID = " + Week + " AND ws.Week = " + Week + " "+  
					"AND r.Starter = true AND r.Teams_Leagues_LeagueID = " + LeagueID + " " +
					"GROUP BY r.Teams_FFATeamID;";
			stmt = conn.prepareStatement(sql);
			
			stmt.setInt(1, Week);
			stmt.setInt(2, Week);
			stmt.setInt(3, LeagueID);
			
			ResultSet rs = stmt.executeQuery();

			while (rs.next()){
				CompositeRankings cr = new CompositeRankings();
				cr.teamID = rs.getInt(4);
				cr.teamName = rs.getString(2);
				cr.ownerName = rs.getString(3);
				cr.cPoints = rs.getInt(1);
				lcr.add(cr);
			}
			//close out connections before post-processing
			stmt.close();
			conn.close();
			Collections.sort(lcr);
			//assign the rank based on the sort, might work, might now
			for (int j = 0; j < lcr.size(); j++){
				lcr.get(j).cRank = j + 1;
				lcr.get(j).cWins = lcr.size() - j;
				lcr.get(j).cLosses = j;
				lcr.get(j).cTies = 0; //setting ties to zero for now
			}
			
		} catch (Exception e){
			e.printStackTrace();

		}

		return lcr;
	}


	public List<List<CompositeRankings>> populateCompositeRankingsRable(int LeagueID, int numWeeks){
		List<List<CompositeRankings>> llcr = new ArrayList<List<CompositeRankings>>();

		for (int i = 1; i <= numWeeks; i++){
			llcr.add(this.populateCompositeRankingsRow(LeagueID, i));
		}

		return llcr;
	}
	
	public List<CompositeRankings> getCompositeRankings(int LeagueID, int Week){
		List<CompositeRankings> lcr = new ArrayList<CompositeRankings>();
		//should be a better way to do this, but can't think of it right now. 
		//going to create dummy CRs and keep track of them by index
		//TODO: query the db for the number of teams, and use that instead of 12. 
		//should be a really simple query like select count(*) from teams where Leagueid=LeagueID
		int numTeams = 12;
		try{
			Connection conn = DbSource.getDataSource().getConnection();
			String sql = "SELECT COUNT(FFATeamID) FROM teams WHERE Leagues_LeagueID = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, LeagueID);
			
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()){
				numTeams = rs.getInt(1);
			}
			stmt.close();
			conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		for (int a = 0; a < numTeams; a++){
			CompositeRankings temp = new CompositeRankings();
			lcr.add(temp);
		}
		
		List<List<CompositeRankings>> grid =  populateCompositeRankingsRable(LeagueID, Week);
		//now this is just getting dumb
		int counter = 0;
		for (List<CompositeRankings> week : grid){
			counter++;
			//need indexing with this loop, should match 1-1 with the empty one
			for(int j = 0; j < week.size(); j++){
				lcr.get(j).cWins += week.get(j).cWins;
				lcr.get(j).cLosses += week.get(j).cLosses;
				lcr.get(j).cTies += week.get(j).cTies;
				//temporarily setting the cRank for the average calcs
				lcr.get(j).cRank += week.get(j).cRank;
				if (counter == 1){
					lcr.get(j).teamName = week.get(j).teamName;
					lcr.get(j).teamID = week.get(j).teamID;
					lcr.get(j).ownerName = week.get(j).ownerName;
				}
			}
		}
		//calculate average values for simplicity
		for (CompositeRankings avg : lcr){
			avg.avgLosses = avg.cLosses / Week;
			avg.avgRank = avg.cRank / Week / 16; //idk why this is 16??? 17 football week, so idk
			avg.avgTies = avg.cTies / Week;
			avg.avgWins = avg.cWins / Week;
		}
		
		//now i don't want to figure out how to sort by wins/losses/ties so i'm going to let angular handle that on the front end with filter
		
		
		///jackson up the list to json and send it back
		//on second thought, returning a list of objects might me easier
		/*ObjectMapper mapper = new ObjectMapper();
		String json = "";
		try{
			json = mapper.writeValueAsString
		} catch (Exception e){
			e.printStackTrace();;
		}*/
		return lcr;
	}
	
	

	public int compare(CompositeRankings o1, CompositeRankings o2) {
		Integer tempo1 = (Integer) o1.cPoints;
		return tempo1.compareTo((Integer) o2.cPoints);
	}

	@Override
	public int compareTo(CompositeRankings o) {
		// TODO Auto-generated method stub
		return  o.cPoints - this.cPoints;
	}

}