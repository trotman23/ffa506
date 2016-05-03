package com.ffa.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ffa.controllers.DbSource;

public class FreeAgents{
	public FreeAgents(){		
	}
	List<Player> FAs;
	public List<Player> getTopFA(String Position){
		FAs= new ArrayList<Player>();
		// run some sql shit
		 getTopPlayer(Position);
		 return FAs;
	}
	
	public void getTopPlayer(String Position){
		Connection pConn = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			pConn = DbSource.getDataSource().getConnection();
			
			String sql = "SELECT * from players WHERE PlayerID NOT "
					+ "IN(SELECT Players_PlayerID FROM roster) and "
					+ "Position = ? Order By OverallRank;";
					
			System.out.println(sql);
			pStmt = pConn.prepareStatement(sql);
			pStmt.setString(1, Position);
			rs = pStmt.executeQuery();
			while(rs.next()){
				Player r = new Player();
				r.Name = rs.getString(2);
				FAs.add(r);
			}
		} catch (Exception e){
			e.printStackTrace();
		} finally {
	        if (rs != null) try { rs.close(); } catch (SQLException logOrIgnore) {}
	        if (pStmt != null) try { pStmt.close(); } catch (SQLException logOrIgnore) {}
	        if (pConn != null) try { pConn.close(); } catch (SQLException logOrIgnore) {}
	    }
	}
	
}