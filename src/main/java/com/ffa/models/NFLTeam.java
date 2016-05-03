package com.ffa.models;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ffa.controllers.DbSource;

import java.sql.*;
import java.util.*;

public class NFLTeam {
	public int NFLTeamID;
	public String NFLTeamName;
	public int Wins;
	public int Losses;
	public int Ties;
	
	public NFLTeam(int id){
		this.NFLTeamID = id;
		Connection pConn = null;
		Statement pStmt = null;
		ResultSet rs = null;
		try{
			pConn = DbSource.getDataSource().getConnection();
			pStmt = pConn.createStatement();
			//select on team name, given team id

			String sql = "SELECT * FROM nflteam WHERE NFLTeamID = " + id + ";";
			System.out.println(sql);
			rs = pStmt.executeQuery(sql);
			while(rs.next()){
				this.NFLTeamName = rs.getString(2);
				this.Wins = rs.getInt(3);
				this.Losses = rs.getInt(4);
				this.Ties = rs.getInt(5);
			}
		} catch (Exception e){
			e.printStackTrace();
		} finally {
	        if (rs != null) try { rs.close(); } catch (SQLException logOrIgnore) {}
	        if (pStmt != null) try { pStmt.close(); } catch (SQLException logOrIgnore) {}
	        if (pConn != null) try { pConn.close(); } catch (SQLException logOrIgnore) {}
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

