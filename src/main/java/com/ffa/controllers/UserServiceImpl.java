package com.ffa.controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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
    private static List<User> users;
    
    //Use this for testing with dummy data
    /*
    static{
        users= populateDummyUsers();
    }*/
 
    public List<User> findAllUsers() {
		Connection pConn = null;
		Statement pStmt = null;
		try{

			Class.forName("com.mysql.jdbc.Driver");
			pConn = DbSource.getDataSource().getConnection();
			pStmt = pConn.createStatement();
			//select on team name, given team id

			String sql = "SELECT * FROM users;";
			System.out.println(sql);
			ResultSet rs = pStmt.executeQuery(sql);
			while(rs.next()){
				  Long id = rs.getLong("userid");
				  String email = rs.getString("email");
				  String password = rs.getString("password");
				  
				  System.out.print(id + " " + email + " " + password);
				  System.out.println();

				  //Assuming you have a user object
				  User user = new User(id, email, password, null, null, null);
				  //users.add(user);
			}
			pConn.close();
			rs.close();

		} catch (Exception e){
			e.printStackTrace();
		} finally{

		}
		
		return users;
    }
     
    public User findById(long userId) {
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
    	//commenting out because errors with git
        //user.setId(counter.incrementAndGet());
        //users.add(user);
       // insertUser(user);
    }
 
    public void updateUser(User user) {
        int index = users.indexOf(user);
        users.set(index, user);
    }
 
    public void deleteUserById(long userId) {
         
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
 //Dummy Data Method
 /*
    private static List<User> populateDummyUsers(){
        List<User> users = new ArrayList<User>();
        users.add(new User(counter.incrementAndGet(),"Sam", "NY", "sam@abc.com"));
        users.add(new User(counter.incrementAndGet(),"Tomy", "ALBAMA", "tomy@abc.com"));
        users.add(new User(counter.incrementAndGet(),"Kelly", "NEBRASKA", "kelly@abc.com"));
        return users;
    }
<<<<<<< HEAD
 */
 
//=======
    private static void insertUser(User user) {
		Connection pConn = null;
		PreparedStatement pStmt = null;
		try{
			pConn = DbSource.getDataSource().getConnection();
			
			String sql = "INSERT INTO users " +
					"(name, email, password) VALUES (?, ?, ?)";
			System.out.println(sql);
			pStmt = pConn.prepareStatement(sql);
			pStmt.setInt(1, (int)counter.incrementAndGet());
		    pStmt.setString(2, user.getEmail());
		    pStmt.setString(3, user.getPassword());
		    
			pConn.close();

		} catch (Exception e){
			e.printStackTrace();
		} finally{
			users.add(user);
		}
    }
}