package com.ffa.controllers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
 
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ffa.models.User;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService{
     
    private static final AtomicLong counter = new AtomicLong();
    private static List<User> users = new ArrayList<User>();
    
    public List<User> findAllUsers() {
    	users = populateUsersFromDB();
    	return users;
    }
     
    public User findById(int userId) {
        for(User user : users){
            if(user.getUserId() == userId){
                return user;
            }
        }
        return null;
    }
     
    public User findByEmail(String email) {
        for(User user : users){
            if(user.getEmail().equalsIgnoreCase(email)){
                return user;
            }
        }
        return null;
    }
     
    public void saveUser(User user) {
        user.setId((int)counter.incrementAndGet());
        users.add(user);
    }
 
    public void updateUser(User user) {
        int index = users.indexOf(user);
        users.set(index, user);
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
		Statement pStmt = null;
		try{
			pConn = DbSource.getDataSource().getConnection();
			pStmt = pConn.createStatement();
			//select on team name, given team id

			String sql = "SELECT * FROM users;";
			System.out.println(sql);
			ResultSet rs = pStmt.executeQuery(sql);
			while(rs.next()){
				  int id = rs.getInt("userid");
				  String name = rs.getString("name");
				  String email = rs.getString("email");
				  String password = rs.getString("password");
				  
				  System.out.print(id + " " + name + " " + email + " " + password);
				  System.out.println();

				  //Assuming you have a user object
				  User user = new User(id, name, email, password);
				  users.add(user);
			}
			pConn.close();
			rs.close();

		} catch (Exception e){
			e.printStackTrace();
		} finally{

		}
		return users;
    }
}