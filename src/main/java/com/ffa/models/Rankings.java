package com.ffa.models;
import java.util.*;
import java.sql.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ffa.controllers.DbSource; 
import com.ffa.models.*;

public class Rankings /*implements Comparator<Rankings>*/{

	public String teamName;
	private int rankScore;
	public int ffaPoints;
	public int teamID;
	public String ownerName;

	
	private static final int weeks = 17;
	private static final int year = 2015;
	public Rankings(){
		
	}
	
	public int getFfaPoints(){
		return this.ffaPoints;
	}
	public void setFfaPoints(int points){
		this.ffaPoints = points;
	}
	public int getRankScore(){
		return this.rankScore;
	}
	public void setRankScore(int rs)
	{
		this.rankScore = rs;
	}
	public List<Rankings> Rank(int LeagueID, int Week){
		List<Rankings> lr = new ArrayList<Rankings>();
		FtjStats temp = new FtjStats();
		List<FtjStats> lt = temp.LeagueTeams(LeagueID);
		//List<String, Integer> teamWeekScore = new ArrayList<String, Integer>();
		for (int i = 0; i < lt.size(); i++){
			Rankings r = new Rankings();
			r.teamID = lt.get(i).teamID;
			r.teamName = lt.get(i).teamName;
			r.ffaPoints = getTeamWeeklySumScore(LeagueID, Week, r.teamID);
			lr.add(r);
		}
		return lr;
		
	}
	
	public static int getTeamWeeklySumScore(int leagueID, int week, int teamID){
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		int score = 0;
		try {
			conn = DbSource.getDataSource().getConnection();
			stmt = conn.createStatement();
			String sql = "SELECT SUM(ws.FantasyPointsScore) FROM weeklyscores ws " + 
			"JOIN roster r ON r.Players_PlayerID = ws.Players_PlayerID " + 
			"WHERE r.Teams_Leagues_LeagueID = " + leagueID + " " +
			"AND r.Teams_FFATeamID = " + teamID + " " +
			"AND r.WeekID > 0 AND r.WeekID <= " + week + " " +
			"AND ws.Week > 0 AND ws.Week <= " + week + " " +
			"AND r.Starter = true;";
			rs = stmt.executeQuery(sql);
			
			while (rs.next()){
				score = rs.getInt(1);
			}
		} catch (Exception e){
			e.printStackTrace();
			
		} finally {
	        if (rs != null) try { rs.close(); } catch (SQLException logOrIgnore) {}
	        if (stmt != null) try { stmt.close(); } catch (SQLException logOrIgnore) {}
	        if (conn != null) try { conn.close(); } catch (SQLException logOrIgnore) {}
	    }
		return score;
	}
	
	
}