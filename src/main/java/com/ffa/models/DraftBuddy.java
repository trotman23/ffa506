package com.ffa.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ffa.controllers.DbSource;

public class DraftBuddy {
	
	public String name;
	public int playerID;
	public String position;
	public int positionRank;
	public int overallRank;
	
	public static List<Player> players = new ArrayList<Player>();
	public static List<Player> wideOuts = new ArrayList<Player>();
	public static List<Player> quartBacks = new ArrayList<Player>();
	public static List<Player> runBacks = new ArrayList<Player>();
	public static List<Player> tightEnds = new ArrayList<Player>();
	public static List<Player> kickers = new ArrayList<Player>();
	
//	public static void main (String [] args){
//		
//		Players();
//	}
//	
	public static List<Player> Players(){
		Connection pConn = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		try{
			pConn = DbSource.getDataSource().getConnection();
			//select on team name, given team id
	
			String sql = "SELECT * FROM players ORDER BY OverallRank ASC;";
			pStmt = pConn.prepareStatement(sql);
			rs = pStmt.executeQuery();
			while(rs.next()){
				Player player = new Player();
				player.OverallRank = rs.getString(7);
				player.Name = rs.getString(2);

				if (rs.getString(4).equals("null")){
					player.NFLTeamName = "Free Agent";
				} else {
					player.NFLTeamName = rs.getString(4);
				}
				
				if (rs.getInt(5) == 0){
					player.Injured = true;
				} else {
					player.Injured = false;
				}
				
				player.Position = rs.getString(3);
				player.PlayerID = rs.getInt(1);
				player.NFLTeam_NFLTeamID = rs.getInt(6);
				players.add(player);
				System.out.println(player);
			}
		} catch (Exception e){
			e.printStackTrace();
		} finally {
	        if (rs != null) try { rs.close(); } catch (SQLException logOrIgnore) {}
	        if (pStmt != null) try { pStmt.close(); } catch (SQLException logOrIgnore) {}
	        if (pConn != null) try { pConn.close(); } catch (SQLException logOrIgnore) {}
	    }
		
		com.fasterxml.jackson.databind.ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		try {
			String json = ow.writeValueAsString(players);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return players;
	}
	
//	public static void selectPlayer(int playerID){
//		//remove player from array
//		
//	}
//	
//	public static void addPlayer(int PlayerID){
//		
//		
//	}

}