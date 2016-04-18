package com.ffa.models;

import java.sql.Connection;
import java.sql.ResultSet;
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
		Statement pStmt = null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			pConn = DbSource.getDataSource().getConnection();
			pStmt = pConn.createStatement();
			
			String sql = "SELECT * from players WHERE PlayerID NOT "
					+ "IN(SELECT Players_PlayerID FROM roster) and "
					+ "Position = \"" +  Position+ "\" Order By OverallRank;";
					
			System.out.println(sql);
			ResultSet rs = pStmt.executeQuery(sql);
			while(rs.next()){
				Player r = new Player();
				r.Name = rs.getString(2);
				FAs.add(r);
			}
			pConn.close();
			rs.close();

		} catch (Exception e){
			e.printStackTrace();
		} finally{

		}
	}
	
}