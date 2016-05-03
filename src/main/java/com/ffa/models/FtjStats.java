package com.ffa.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ffa.controllers.DbSource;



public class FtjStats {

	public String name;
	public int playerID;
	public int avgPoints;
	public int totalPoints;
	public String position;
	public int positionRank;
	public int overallRank;
	public int teamID;
	public String teamName;
	
	public FtjStats(){
		
	}	
	
	
	public List<FtjStats> LeagueTeams(int LeagueID){
		List<FtjStats> teams = new ArrayList<FtjStats>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			conn = DbSource.getDataSource().getConnection();
			//select on team name, given team id

			String sql = "SELECT * FROM teams t JOIN leagues l ON t.Leagues_LeagueID = l.LeagueID WHERE l.ESPNLeagueID = ?;";
			System.out.println(sql);
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, LeagueID);
			rs = stmt.executeQuery();
			while(rs.next()){
				FtjStats temp = new FtjStats();
				temp.teamID = rs.getInt(1);
				temp.teamName = rs.getString(5);
				teams.add(temp);
			}

		} catch (Exception e){
			e.printStackTrace();
		} finally {
	        if (rs != null) try { rs.close(); } catch (SQLException logOrIgnore) {}
	        if (stmt != null) try { stmt.close(); } catch (SQLException logOrIgnore) {}
	        if (conn != null) try { conn.close(); } catch (SQLException logOrIgnore) {}
	    }
		com.fasterxml.jackson.databind.ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		try {
			String json = ow.writeValueAsString(teams);
			System.out.println(json);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return teams;
	}
	
	public List<Player> isFair(int tid1, int tid2){
		//TODO: implement fair trade judge algorithm, compare lots of stats
		int p1pts = getPlayerPoints(tid1);
		int p2pts = getPlayerPoints(tid2);
		System.out.println("player1"+p1pts+"player2"+p2pts);
		
	   /* double percentDiff = (double) p1pts/((double) p1pts+p2pts);
	    
	    if(percentDiff>0.4 && percentDiff<0.6)
	    	return true;
	    			
		return false;*/
		List<Player> ftjPlayers = new ArrayList<Player>();
		Player p1 = createPlayer(p1pts, tid1);
		Player p2 = createPlayer(p2pts, tid2);
		ftjPlayers.add(p1);
		ftjPlayers.add(p2);
		return ftjPlayers;
	}
	
	private Player createPlayer(int points,int id){
		Player newPlayer = new Player();
		newPlayer.ftjPoints=points;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs =null;
		try{

			//Class.forName("com.mysql.jdbc.Driver");
			conn = DbSource.getDataSource().getConnection();
			stmt = conn.createStatement();

			String sql= "SELECT * from players p "
					+ "WHERE p.PlayerID = "+id + ";";
			System.out.println(sql);
			 rs = stmt.executeQuery(sql);
			//testing
			while(rs.next()){
				newPlayer.Name= rs.getString(2);	
				newPlayer.OverallRank = rs.getInt(7);
			}
		
			return newPlayer;


		}catch (Exception e){
			System.out.println("Caught exceptionz");
			e.printStackTrace();
			return null;
		}finally{
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					return null;
				//	e.printStackTrace();
				}
			if(rs!=null)
				try {
					rs.close();
				} catch (SQLException e) {
					return null;
					//e.printStackTrace();
				}
		}
		
	}

	public int getPlayerPoints(int pID){
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			conn = DbSource.getDataSource().getConnection();
			String sql= "SELECT SUM(ws.FantasyPointsScore) "
					+ "FROM players p "
					+ "JOIN weeklyscores ws "
					+ "ON p.PlayerID = ws.Players_PlayerID "
					+ "WHERE p.PlayerID = ?;";
			System.out.println(sql);
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, pID);
			rs = stmt.executeQuery();
			//testing
			int scoreSum = 0;
			while(rs.next()){
				scoreSum = rs.getInt(1);	
			}
			return scoreSum;
		}catch (Exception e){
			System.out.println("Caught exceptionz");
			e.printStackTrace();
			return 0;
		} finally {
	        if (rs != null) try { rs.close(); } catch (SQLException logOrIgnore) {}
	        if (stmt != null) try { stmt.close(); } catch (SQLException logOrIgnore) {}
	        if (conn != null) try { conn.close(); } catch (SQLException logOrIgnore) {}
	    }
	}
}

