package com.ffa.models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
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
	
	public static List<List<Player>> players = new ArrayList<List<Player>>();
	public static List<Player> wideOuts = new ArrayList<Player>();
	public static List<Player> quartBacks = new ArrayList<Player>();
	public static List<Player> runBacks = new ArrayList<Player>();
	public static List<Player> tightEnds = new ArrayList<Player>();
	public static List<Player> kickers = new ArrayList<Player>();
	
	public static List<List<Player>> Players(){
		Connection pConn = null;
		Statement pStmt = null;
		try{
	
			Class.forName("com.mysql.jdbc.Driver");
			pConn = DbSource.getDataSource().getConnection();
			pStmt = pConn.createStatement();
			//select on team name, given team id
	
			String sql = "SELECT * FROM players ORDER BY position desc";
			ResultSet rs = pStmt.executeQuery(sql);
			while(rs.next()){
				Player player = new Player();
				player.Name = rs.getString(2);
				player.NFLTeamName = rs.getString(4);
				if (rs.getInt(5) == 0){
					player.Injured = true;
				} else {
					player.Injured = false;
				}
				
				player.Position = rs.getString(3);
				player.PlayerID = rs.getInt(1);
				player.NFLTeam_NFLTeamID = rs.getInt(6);
//				players.add(player);
				
				//add to respective arrays
				switch(player.Position){
				case "WR":
					wideOuts.add(player);
					break;
				case "TE":
					tightEnds.add(player);
					break;
				case "QB":
					quartBacks.add(player);
					break;
				case "K":
					kickers.add(player);
					break;
				case "RB":
					runBacks.add(player);
					break;
				default:
					break;
				}
			}
			
			players.add(wideOuts);
			players.add(tightEnds);
			players.add(quartBacks);
			players.add(kickers);
			players.add(runBacks);
			
			pConn.close();
			rs.close();
		} catch (Exception e){
			e.printStackTrace();
		} finally{
	
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
	
	public static void selectPlayer(int playerID){
		//remove player from array
	
		
	}
	
	public static void addPlayer(int PlayerID){
		
		
	}
	
//    public static void main(String[] args) {
//    	Players();
//    }

}