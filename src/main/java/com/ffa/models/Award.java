package com.ffa.models;
import java.util.*;
import java.sql.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ffa.controllers.DbSource;
import com.ffa.models.*;

public class Award {
	public int LeagueID;
	public int Week;
	
	//TODO: Add a bunch more awards, be creative
	//good awards
	//top weekly overall score
	public int topOverallScore;
	public String topOverallScoreTeam;
	public String topOverallScoreOwner;
	//top scorer for individual person
	public int topScorerPlayerPoints;
	public String topScorerPlayerName;
	public String topScorerPlayerTeam;
	public String topScorerPlayerOwner;

	//bad awards
	//private String mostPointsAllowed;
	//most bend points left on the bench by entire bench
	public int mostBenchPoints;
	public String mostBenchPointsTeam;
	public String mostBenchPointsOwner;
	//most points left on the bench by one player
	public int mostBenchPointsIndividualPlayerPoints;
	public String mostBenchPointsIndividualPlayerName;
	public String mostBenchPointsIndividualPlayerTeam;
	public String mostBenchPointsIndividualPlayerOwner;
	
	
	/*public String getAwardsJson(){
		//jackson sucks, it's not working and idk why because the stack trace is too long
		String json = "[{'topOverallScore\":" + this.topOverallScore + "," +
						"'topOverallScoreTeam\":\"" + this.topOverallScoreTeam + "\"," +  
						"\"topOverallScoreOwner\":\"" + this.topOverallScoreOwner + "\"," + 
						"\"topScorerPlayerPoints\":" + this.topScorerPlayerPoints + "," +
						"\"topScorerPlayerName\":\"" + this.topScorerPlayerName + "\"," + 
						"\"topScorerPlayerTeam\":\"" + this.topScorerPlayerTeam + "\"," + 
						"\"topScorerPlayerOwner\":\"" + this.topScorerPlayerOwner + "\"," + 
						"\"mostBenchPoints\":" + this.mostBenchPoints + "," +
						"\"mostBenchPointsTeam\":\"" + this.mostBenchPointsTeam + "\"," + 
						"\"mostBenchPointsOwner\":\"" + this.mostBenchPointsOwner + "\"," + 
						"\"mostBenchPointsIndividualPlayerPoints\":" + this.mostBenchPointsIndividualPlayerPoints + "," +
						"\"mostBenchPointsIndividualPlayerName\":\"" + this.mostBenchPointsIndividualPlayerName + "\"," + 
						"\"mostBenchPointsIndividualPlayerTeam\":\"" + this.mostBenchPointsIndividualPlayerTeam + "\"," + 
						"\"mostBenchPointsIndividualPlayerOwner\":\"" + this.mostBenchPointsIndividualPlayerOwner + "\"" + 
					"}]";
		
		System.out.println(json);
		return json;
	}*/
	
	public Award(int l, int w){
		this.LeagueID = l;
		this.Week = w;
		//probably a better more OOP way of doing this, but for simplicity's sake i'm just going to calculate all of the weekly awards in the constructor
		Connection conn1 = null;
		PreparedStatement stmt1 = null;
		
		//top weekly overall score
		try{
			conn1 = DbSource.getDataSource().getConnection();

			String sql = "SELECT MAX(sumPoints), maxpoints.TeamName, maxpoints.userName " + 
						"FROM (SELECT SUM(FantasyPointsScore) as sumPoints, TeamName, u.Name as userName FROM weeklyscores ws " + 
						"JOIN roster r ON r.Players_PlayerID = ws.Players_PlayerID " + 
						"JOIN teams t ON t.FFATeamId = r.Teams_FFATeamId " + 
						"JOIN users u ON u.UserID = t.Users_UserID " +
						"WHERE r.WeekID = ? AND ws.Week = ? AND r.Starter = true AND r.Teams_Leagues_LeagueID = ? GROUP BY r.Teams_FFATeamID) as maxpoints";
			System.out.println(sql);
			stmt1 = conn1.prepareStatement(sql);
			
			stmt1.setInt(1, w);
			stmt1.setInt(2, w);
			stmt1.setInt(3, l);
			
			ResultSet rs = stmt1.executeQuery();
			while(rs.next()){
				this.topOverallScore = rs.getInt(1);
				this.topOverallScoreTeam = rs.getString(2);
				this.topOverallScoreOwner = rs.getString(3);
			}
			conn1.close();
			stmt1.close();
			rs.close();
		} catch (Exception e){
			e.printStackTrace();
		}
		
		//top scorer for individual person
		Connection conn2 = null;
		PreparedStatement stmt2 = null;
		
		try{
			conn2 = DbSource.getDataSource().getConnection();

			String sql ="SELECT MAX(ws.FantasyPointsScore), p.Name, u.Name,  t.TeamName " +
						"FROM weeklyscores ws " + 
						"JOIN roster r ON r.Players_PlayerID = ws.Players_PlayerID " +
						"JOIN teams t ON t.FFATeamId = r.Teams_FFATeamId " + 
						"JOIN users u ON u.UserID = t.Users_UserID " + 
						"JOIN players p ON p.PlayerID = ws.Players_PlayerID " + 
						"WHERE r.WeekID = ? AND ws.Week = ? AND r.Starter = true AND r.Teams_Leagues_LeagueID = ?;";
			System.out.println(sql);
			stmt2 = conn2.prepareStatement(sql);
			
			stmt2.setInt(1, w);
			stmt2.setInt(2, w);
			stmt2.setInt(3, l);
			
			ResultSet rs = stmt2.executeQuery();
			while(rs.next()){
				this.topScorerPlayerPoints = rs.getInt(1);
				this.topScorerPlayerName = rs.getString(2);
				this.topScorerPlayerOwner = rs.getString(3);
				this.topScorerPlayerTeam = rs.getString(4);
			}
			conn2.close();
			stmt2.close();
			rs.close();
		} catch (Exception e){
			e.printStackTrace();
		}
		
		//most bench points left on the bench by entire bench
		Connection conn3 = null;
		PreparedStatement stmt3 = null;
		try{
			conn3 = DbSource.getDataSource().getConnection();
			//select on team name, given team id

			String sql = "SELECT MAX(sumPoints), minpoints.TeamName, minpoints.userName " + 
					"FROM (SELECT SUM(FantasyPointsScore) as sumPoints, TeamName, u.Name as userName FROM weeklyscores ws " + 
					"JOIN roster r ON r.Players_PlayerID = ws.Players_PlayerID " + 
					"JOIN teams t ON t.FFATeamId = r.Teams_FFATeamId " + 
					"JOIN users u ON u.UserID = t.Users_UserID " +
					"WHERE r.WeekID = ? AND ws.Week = ? AND r.Starter = false AND r.Teams_Leagues_LeagueID = ? GROUP BY r.Teams_FFATeamID) as minpoints;";
			System.out.println(sql);
			stmt3 = conn3.prepareStatement(sql);
			
			stmt3.setInt(1, w);
			stmt3.setInt(2, w);
			stmt3.setInt(3, l);
			
			ResultSet rs = stmt3.executeQuery();
			while(rs.next()){
				this.mostBenchPoints = rs.getInt(1);
				this.mostBenchPointsTeam = rs.getString(2);
				this.mostBenchPointsOwner = rs.getString(3);
			}
			conn3.close();
			stmt3.close();
			rs.close();
		} catch (Exception e){
			e.printStackTrace();
		}
		
		//most points left on the bench by one player
		Connection conn4 = null;
		PreparedStatement stmt4 = null;
		
		try{
			conn4 = DbSource.getDataSource().getConnection();

			String sql ="SELECT MAX(ws.FantasyPointsScore), p.Name, u.Name,  t.TeamName " +
						"FROM weeklyscores ws " + 
						"JOIN roster r ON r.Players_PlayerID = ws.Players_PlayerID " +
						"JOIN teams t ON t.FFATeamId = r.Teams_FFATeamId " + 
						"JOIN users u ON u.UserID = t.Users_UserID " + 
						"JOIN players p ON p.PlayerID = ws.Players_PlayerID " + 
						"WHERE r.WeekID = ? AND ws.Week = ? AND r.Starter = false AND r.Teams_Leagues_LeagueID = ?;";
			System.out.println(sql);
			stmt4 = conn4.prepareStatement(sql);
			
			stmt4.setInt(1, w);
			stmt4.setInt(2, w);
			stmt4.setInt(3, l);
			
			ResultSet rs = stmt4.executeQuery();
			while(rs.next()){
				this.mostBenchPointsIndividualPlayerPoints = rs.getInt(1);
				this.mostBenchPointsIndividualPlayerName = rs.getString(2);
				this.mostBenchPointsIndividualPlayerOwner = rs.getString(3);
				this.mostBenchPointsIndividualPlayerTeam = rs.getString(4);
			}
			conn4.close();
			stmt4.close();
			rs.close();
		} catch (Exception e){
			e.printStackTrace();
		}
		
	}
	
}
