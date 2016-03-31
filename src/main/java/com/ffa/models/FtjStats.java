package com.ffa.models;

import java.util.*;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.qos.logback.core.net.ObjectWriter;
import javafx.util.Pair;

import java.io.File;
import java.io.IOException;
import java.sql.*;



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
	
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost/ffadb";

	//  Database credentials
	static final String USER = "root";
	static final String PASS = "eric2mad";
	
	
	public FtjStats(){
		
	}	
	
	
	public List<FtjStats> LeagueTeams(int LeagueID){
		List<FtjStats> teams = new ArrayList<FtjStats>();
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
		boolean test = tid1>tid2;
		return tid1 > tid2;
	}
}
