package com.ffa.models;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.sql.*;
import java.util.*;

public class NFLTeam {
	public int NFLTeamID;
	public String NFLTeamName;
	public int Wins;
	public int Losses;
	public int Ties;
	
	// djdbc driver name and database
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost/ffadb";

	//  Database credentials
	static final String USER = "root";
	static final String PASS = "eric2mad";
	
	
	public NFLTeam(int id){
		this.NFLTeamID = id;
		Connection pConn = null;
		Statement pStmt = null;
		try{

			Class.forName("com.mysql.jdbc.Driver");
			pConn = DriverManager.getConnection(DB_URL,USER,PASS);
			pStmt = pConn.createStatement();
			//select on team name, given team id

			String sql = "SELECT * FROM nflteam WHERE NFLTeamID = " + id + ";";
			System.out.println(sql);
			ResultSet rs = pStmt.executeQuery(sql);
			while(rs.next()){
				this.NFLTeamName = rs.getString(2);
				this.Wins = rs.getInt(3);
				this.Losses = rs.getInt(4);
				this.Ties = rs.getInt(5);
			}
			pConn.close();
			rs.close();

		} catch (Exception e){
			e.printStackTrace();
		} finally{

		}
	}
	public NFLTeam(){
		
	}
	
	@Override
	public String toString(){
		String teamString = "";
		teamString += "NFLTeamID: " + new Integer(this.NFLTeamID).toString() + "\n";
		teamString += "NFLTeamName: " + this.NFLTeamName + "\n";
		teamString += "Wins: " + new Integer(this.Wins).toString() + "\n";
		teamString += "Losses: " + new Integer(this.Losses).toString() + "\n";
		teamString += "Ties: " + new Integer(this.Ties).toString() + "\n";
		return teamString;
	}
}


