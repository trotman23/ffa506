package com.ffa.models;

import java.util.*;

import javafx.util.Pair;

import java.sql.*;



public class FtjStats {

	public String name;
	public int playerID;
	public int avgPoints;
	public int totalPoints;
	public String position;
	public int positionRank;
	public int overallRank;
	
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost/ffadb";

	//  Database credentials
	static final String USER = "root";
	static final String PASS = "eric2mad";
	
	
	public FtjStats(){
		
	}
	
	
	
	public Map<Integer, String> LeagueTeams(int LeagueID){
		Map<Integer, String> teams = new HashMap<Integer, String>();
		Connection conn = null;
		Statement stmt = null;
		try{

			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			stmt = conn.createStatement();
			//select on team name, given team id

			String sql = "SELECT * FROM teams t JOIN leagues l ON t.Leagues_LeagueID = l.LeagueID WHERE l.ESPNLeagueID = " + LeagueID + ";";
			System.out.println(sql);
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				teams.put(rs.getInt(1), rs.getString(5));
			}
			conn.close();
			rs.close();

		} catch (Exception e){
			e.printStackTrace();
		} finally{

		}
		
		return teams;
	}
}
