package com.ffa.models;

import java.util.*;

import com.ffa.controllers.DbSource;

import java.sql.*;

public class Roster {
	
	public int Players_PlayerID;
	public int Teams_FFATeamID;
	public int Teams_Users_UserID;
	public int Teams_Leagues_LeagueID;
	public String InjuryCode;
	public String Slot;
	public int WeekID;
	public boolean Starter;
	public int SeasonID;
	public int PlayerID;
	public String Name;
	public String Position;
	public String NFLTeamName;
	public int NFLTeam_NFLTeamID;
	
	public Roster(){
	};	
	public List<Roster> getRoster(String LeagueID, String TeamID, String Week, String Year){
		List<Roster> lr = new ArrayList<Roster>();
		Connection pConn = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		try{
			pConn = DbSource.getDataSource().getConnection();
			//select on team name, given team id

			/* sql code to try out, getting the roster of a team for a given week and year
			 * 
			 *  SELECT * 
				FROM Roster r
				JOIN Players p 
				ON r.Players_PlayerID = p.PlayerID
				WHERE r.WeekID = 1 and r.SeasonID = 2015 and r.Teams_FFATeamID = 1 and Teams_Leagues_LeagueID = 1682132;
			 * 
			 * 
			 */
			String sql = "SELECT * FROM roster r JOIN players p ON r.Players_PlayerID = p.PlayerID WHERE " + 
					"r.weekID = ? and " + 
					"r.SeasonID = ? and " + 
					"r.Teams_FFATeamID = ? and " + 
					"Teams_Leagues_LeagueID = ?;";
			System.out.println(sql);

			pStmt = pConn.prepareStatement(sql);
			pStmt.setString(1, Week);
			pStmt.setString(2, Year);
			pStmt.setString(3, TeamID);
			pStmt.setString(4, LeagueID);
			
			rs = pStmt.executeQuery();
			while(rs.next()){
				Roster r = new Roster();
				r.Players_PlayerID = rs.getInt(1);
				r.Teams_FFATeamID = rs.getInt(2);
				r.Teams_Users_UserID = rs.getInt(3);
				r.Teams_Leagues_LeagueID = rs.getInt(4);
				r.InjuryCode = rs.getString(5);
				r.Slot = rs.getString(6);
				r.WeekID = rs.getInt(7);
				r.Starter = rs.getBoolean(8);
				r.SeasonID = rs.getInt(9);
				r.PlayerID = rs.getInt(10);
				r.Name = rs.getString(11);
				r.Position = rs.getString(12);
				r.NFLTeamName = rs.getString(13);
				r.NFLTeam_NFLTeamID = rs.getInt(15);
				lr.add(r);
			}
		} catch (Exception e){
			e.printStackTrace();
		} finally {
	        if (rs != null) try { rs.close(); } catch (SQLException logOrIgnore) {}
	        if (pStmt != null) try { pStmt.close(); } catch (SQLException logOrIgnore) {}
	        if (pConn != null) try { pConn.close(); } catch (SQLException logOrIgnore) {}
	    }
		return lr;
	}
}
