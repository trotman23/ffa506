package com.ffa.controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ffa.models.User;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService{

	private static List<User> users = new ArrayList<User>();

	public List<User> findAllUsers() {
		users.clear();
		users = populateUsersFromDB();
		return users;
	}

	public User findById(int userId) {
		Connection pConn = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;

		User user = new User();
		int id = 0;
		String name = "";
		String email = "";
		String password = "";

		try{
			pConn = DbSource.getDataSource().getConnection();
			//select on team name, given team id

			String sql = "SELECT * FROM users WHERE UserID = ?;";
			System.out.println(sql);
			pStmt = pConn.prepareStatement(sql);
			pStmt.setInt(1, userId);
			rs = pStmt.executeQuery();
			if (!rs.next()) {
				return null;
			}
			else {
				id = rs.getInt(1);
				name = rs.getString(2);
				email = rs.getString(3);
				password = rs.getString(4);
			}

		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if (rs != null) try { rs.close(); } catch (SQLException logOrIgnore) {}
			if (pStmt != null) try { pStmt.close(); } catch (SQLException logOrIgnore) {}
			if (pConn != null) try { pConn.close(); } catch (SQLException logOrIgnore) {}
		}
		user.setId(id);
		user.setName(name);
		user.setPassword(password);
		user.setEmail(email);

		return user;
	}

	public User findByEmail(String email) {
		Connection pConn = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;

		User user = new User();
		int id = 0;
		String name = "";
		String emailCopy = "";
		String password = "";

		try{
			pConn = DbSource.getDataSource().getConnection();
			//select on team name, given team id

			String sql = "SELECT * FROM users WHERE Email = ?;";
			System.out.println(sql);
			pStmt = pConn.prepareStatement(sql);
			pStmt.setString(1, email);
			rs = pStmt.executeQuery();
			if (!rs.next()) {
				return null;
			}
			else {
				id = rs.getInt(1);
				name = rs.getString(2);
				emailCopy = rs.getString(3);
				password = rs.getString(4);
			}

		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if (rs != null) try { rs.close(); } catch (SQLException logOrIgnore) {}
			if (pStmt != null) try { pStmt.close(); } catch (SQLException logOrIgnore) {}
			if (pConn != null) try { pConn.close(); } catch (SQLException logOrIgnore) {}
		}
		user.setId(id);
		user.setName(name);
		user.setPassword(password);
		user.setEmail(emailCopy);

		return user;
	}

	public void saveUser(User user) {
		insertUser(user);
	}
	/*
	 * 
	 * UPDATE Customers
		SET ContactName='Alfred Schmidt', City='Hamburg'
		WHERE CustomerName='Alfreds Futterkiste';
	 */

	public void updateUser(User user) {
		Connection pConn = null;
		PreparedStatement pStmt = null;
		try{
			pConn = DbSource.getDataSource().getConnection();

			String sql = "UPDATE users SET " +
					"Name = ?, Email = ?, Password = ? WHERE UserID = ?;";
			System.out.println(sql);
			pStmt = pConn.prepareStatement(sql);
			pStmt.setString(1, user.getName());
			pStmt.setString(2, user.getEmail());
			pStmt.setString(3, user.getPassword());
			pStmt.setInt(4, user.getUserId());
			pStmt.executeUpdate();

		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if (pStmt != null) try { pStmt.close(); } catch (SQLException logOrIgnore) {}
			if (pConn != null) try { pConn.close(); } catch (SQLException logOrIgnore) {}
		}
	}

	public void deleteUserById(int userId) {

		for (Iterator<User> iterator = users.iterator(); iterator.hasNext(); ) {
			User user = iterator.next();
			if (user.getUserId() == userId) {
				iterator.remove();
			}
		}
	}

	public boolean isUserExist(User user) {
		return findByEmail(user.getEmail())!=null;
	}

	public void deleteAllUsers(){
		users.clear();
	}

	private static List<User> populateUsersFromDB() {
		Connection pConn = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		try{
			pConn = DbSource.getDataSource().getConnection();
			//select on team name, given team id

			String sql = "SELECT * FROM users;";
			System.out.println(sql);
			pStmt = pConn.prepareStatement(sql);
			rs = pStmt.executeQuery();
			while(rs.next()){
				int id = rs.getInt("userid");
				String name = rs.getString("name");
				String email = rs.getString("email");
				String password = rs.getString("password");

				//Assuming you have a user object
				User user = new User(id, name, email, password);
				users.add(user);
			}
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if (rs != null) try { rs.close(); } catch (SQLException logOrIgnore) {}
			if (pStmt != null) try { pStmt.close(); } catch (SQLException logOrIgnore) {}
			if (pConn != null) try { pConn.close(); } catch (SQLException logOrIgnore) {}
		}

		return users;
	}

	private static void insertUser(User user) {
		Connection pConn = null;
		PreparedStatement pStmt = null;
		try{
			pConn = DbSource.getDataSource().getConnection();

			String sql = "INSERT INTO users " +
					"(name, email, password) VALUES (?, ?, ?)";
			System.out.println(sql);
			pStmt = pConn.prepareStatement(sql);
			pStmt.setString(1, user.getName());
			pStmt.setString(2, user.getEmail());
			pStmt.setString(3, user.getPassword());
			pStmt.executeUpdate();

		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if (pStmt != null) try { pStmt.close(); } catch (SQLException logOrIgnore) {}
			if (pConn != null) try { pConn.close(); } catch (SQLException logOrIgnore) {}
			users.add(user);
		}
	}

	public static String getLeagueIDFromUser(int UserID) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String LeagueID = "";
		try{
			conn = DbSource.getDataSource().getConnection();
			String sql = "SELECT DISTINCT ESPNLeagueID FROM users u, leagues_has_users lhu, leagues l " +
					"WHERE u.UserID = ?;";
			System.out.println(sql);
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, UserID);
			rs = stmt.executeQuery();
			while(rs.next()){
				int temp = rs.getInt(1);
				LeagueID = Integer.toString(temp);
			}
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if (rs != null) try { rs.close(); } catch (SQLException logOrIgnore) {}
			if (stmt != null) try { stmt.close(); } catch (SQLException logOrIgnore) {}
			if (conn != null) try { conn.close(); } catch (SQLException logOrIgnore) {}
		}
		return LeagueID;
	}
}