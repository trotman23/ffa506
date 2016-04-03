package com.ffa.models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
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
		Statement stmt = null;
		try{

			Class.forName("com.mysql.jdbc.Driver");
			conn = DbSource.getDataSource().getConnection();
			stmt = conn.createStatement();
			//select on team name, given team id

			String sql = "SELECT * FROM teams t JOIN leagues l ON t.Leagues_LeagueID = l.LeagueID WHERE l.ESPNLeagueID = " + LeagueID + ";";
			System.out.println(sql);
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				FtjStats temp = new FtjStats();
				temp.teamID = rs.getInt(1);
				temp.teamName = rs.getString(5);
				teams.add(temp);
			}
			conn.close();
			rs.close();

		} catch (Exception e){
			e.printStackTrace();
		} finally{

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
	
	public boolean isFair(int tid1, int tid2){
		//TODO: implement fair trade judge algorithm, compare lots of stats
		int p1pts = getPlayerPoints(tid1);
		int p2pts = getPlayerPoints(tid2);
		
	    double percentDiff = (double) p1pts/((double) p1pts+p2pts);
	    
	    if(percentDiff>0.4 && percentDiff<0.6)
	    	return true;
	    			
		return false;
	}

	public int getPlayerPoints(int pID){
		Connection conn = null;
		Statement stmt = null;
		try{

			Class.forName("com.mysql.jdbc.Driver");
			conn = DbSource.getDataSource().getConnection();
			stmt = conn.createStatement();

			String sql= "select sum(FantasyPointsScore) "
					+ "from players p "
					+ "join weeklyscores ws"
					+ " on p.PlayerID = ws.PLayers_PlayerID"
					+ "where p.PlayerID = "+pID;
			return ((ResultSet) stmt.executeQuery(sql)).getInt(1);


		}catch (Exception e){
			e.printStackTrace();
			return 0;
		}
	}
}