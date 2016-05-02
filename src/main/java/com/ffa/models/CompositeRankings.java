package com.ffa.models;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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
		Statement stmt = null;
		ResultSet rs = null;
		int score = 0;
		try {
			conn = DbSource.getDataSource().getConnection();
			stmt = conn.createStatement();
			String sql ="SELECT SUM(FantasyPointsScore), TeamName, u.Name, t.FFATeamID FROM weeklyscores ws " +
					"JOIN roster r ON r.Players_PlayerID = ws.Players_PlayerID " + 
					"JOIN teams t ON t.FFATeamId = r.Teams_FFATeamId " + 
					"JOIN users u ON u.UserID = t.Users_UserID " +
					"WHERE r.WeekID = " + Week + " AND ws.Week = " + Week + " "+  
					"AND r.Starter = true AND r.Teams_Leagues_LeagueID = " + LeagueID + " " +
					"GROUP BY r.Teams_FFATeamID;";
			rs = stmt.executeQuery(sql);

			while (rs.next()){
				CompositeRankings cr = new CompositeRankings();
				cr.teamID = rs.getInt(4);
				cr.teamName = rs.getString(2);
				cr.ownerName = rs.getString(3);
				cr.cPoints = rs.getInt(1);
				lcr.add(cr);
			}
			Collections.sort(lcr);
			//assign the rank based on the sort, might work, might now
			for (int j = 0; j < lcr.size(); j++){
				lcr.get(j).cRank = j + 1;
				lcr.get(j).cWins = lcr.size() - (j + 1);
				lcr.get(j).cLosses = j;
				lcr.get(j).cTies = 0; //setting ties to zero for now
			}
			
		} catch (Exception e){
			e.printStackTrace();

		} finally {
	        if (rs != null) try { rs.close(); } catch (SQLException logOrIgnore) {}
	        if (stmt != null) try { stmt.close(); } catch (SQLException logOrIgnore) {}
	        if (conn != null) try { conn.close(); } catch (SQLException logOrIgnore) {}
	    }

		return lcr;
	}


	public List<List<CompositeRankings>> populateCompositeRankingsTable(int LeagueID, int numWeeks){
		List<List<CompositeRankings>> llcr = new ArrayList<List<CompositeRankings>>();

		for (int i = 1; i <= numWeeks; i++){
			llcr.add(populateCompositeRankingsRow(LeagueID, i));
		}
		//Collections.sort((List<T>) llcr);

		return llcr;
	}
	
	public List<CompositeRankings> getCompositeRankings(int LeagueID, int Week){
		List<CompositeRankings> lcr = new ArrayList<CompositeRankings>();
		//should be a better way to do this, but can't think of it right now. 
		//going to create dummy CRs and keep track of them by index
		//TODO: query the db for the number of teams, and use that instead of 12. 
		//should be a really simple query like select count(*) from teams where Leagueid=LeagueID
		int numTeams = 12;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try{
			conn = DbSource.getDataSource().getConnection();
			stmt = conn.createStatement();
			String sql = "SELECT COUNT(*) FROM teams WHERE Leagues_LeagueID = 1";
			rs = stmt.executeQuery(sql);
			while (rs.next()){
				numTeams = rs.getInt(1);
			}
		}catch(Exception e){
			e.printStackTrace();
		} finally {
	        if (rs != null) try { rs.close(); } catch (SQLException logOrIgnore) {}
	        if (stmt != null) try { stmt.close(); } catch (SQLException logOrIgnore) {}
	        if (conn != null) try { conn.close(); } catch (SQLException logOrIgnore) {}
	    }
		
		for (int a = 0; a <numTeams; a++){
			CompositeRankings temp = new CompositeRankings();
			lcr.add(temp);
		}
		
		List<List<CompositeRankings>> grid =  populateCompositeRankingsTable(LeagueID, Week);
		for (int y = 0; y < grid.size(); y++){
			if (y == 0){
				for(int j = 0; j < grid.get(y).size(); j++){
					lcr.get(j).teamName = grid.get(y).get(j).teamName;
					lcr.get(j).teamID = grid.get(y).get(j).teamID;
					lcr.get(j).ownerName = grid.get(y).get(j).ownerName;
				}
			}
			for(int j = 0; j < grid.get(y).size(); j++){
				for (int k = 0; k < lcr.size(); k++){
					if (lcr.get(k).teamID == grid.get(y).get(j).teamID){
						lcr.get(k).cWins += grid.get(y).get(j).cWins;
						lcr.get(k).cLosses += grid.get(y).get(j).cLosses;
						lcr.get(k).cTies += grid.get(y).get(j).cTies;
						lcr.get(k).cRank = grid.get(y).get(j).cRank;
					}
				}
				
			}
			
		}
		//calculate average values for simplicity
		for (CompositeRankings avg : lcr){
			avg.avgLosses = avg.cLosses / Week;
			avg.avgRank = avg.cRank / Week;
			avg.avgTies = avg.cTies / Week;
			avg.avgWins = avg.cWins / Week;
		}
		//one last sort -_-
		//Collections.sort(lcr);
		//sort in the angular controller
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