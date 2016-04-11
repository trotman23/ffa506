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
		PreparedStatement stmt = null;
		int score = 0;
		try {
			conn = DbSource.getDataSource().getConnection();
			String sql = "SELECT SUM(ws.FantasyPointsScore) FROM weeklyscores ws " + 
			"JOIN roster r ON r.Players_PlayerID = ws.Players_PlayerID " + 
			"WHERE r.Teams_Leagues_LeagueID = ? " +
			"AND r.Teams_FFATeamID = ? " +
			"AND r.WeekID > 0 AND r.WeekID <= ? " +
			"AND ws.Week > 0 AND ws.Week <= ? " +
			"AND r.Starter = true;";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, leagueID);
			stmt.setInt(2, teamID);
			stmt.setInt(3, week);
			stmt.setInt(4, week);
			
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()){
				score = rs.getInt(1);
			}
			stmt.close();
			conn.close();
		} catch (Exception e){
			e.printStackTrace();
			
		}
		return score;
	}
	
	
}
