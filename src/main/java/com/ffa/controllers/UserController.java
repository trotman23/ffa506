package com.ffa.controllers;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
 
import com.ffa.models.User;

@RestController
public class UserController {
  
    @Autowired
    UserService userService;  //Service which will do all data retrieval/manipulation work
  
     
    //-------------------Retrieve All Users--------------------------------------------------------
      
    @RequestMapping(value = "/user/", method = RequestMethod.GET)
    public ResponseEntity<List<User>> listAllUsers() {
        List<User> users = userService.findAllUsers();
        if(users.isEmpty()){
            return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }
  
  
     
    //-------------------Retrieve Single User--------------------------------------------------------
      
    @RequestMapping(value = "/user", params = "userID", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUser(@RequestParam("userID") int id) {
        System.out.println("Fetching User with id " + id);
        User user = userService.findById(id);
        if (user == null) {
            System.out.println("User with id " + id + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }
    
  //-------------------Retrieve Single User By Email--------------------------------------------------------
    
    @RequestMapping(value = "/user", params = "emailID", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUserByEmail(@RequestParam("emailID") String email) {
        System.out.println("Fetching User with email " + email);
        User user = userService.findByEmail(email);
        if (user == null) {
            System.out.println("User with email " + email + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }
  
  //-------------------Authenticate User (without encryption)--------------------------------------------------------
    
    @RequestMapping(value = "/user/authenticate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> authenticateUser(@RequestParam("emailID") String email, @RequestParam("passwordID") String password) {
    	System.out.println("Autheticating User with email " + email);
        User user = userService.findByEmail(email);
        if (user == null) {
            System.out.println("User with email " + email + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        
        if (!user.getPassword().equals(password)) {
        	return new ResponseEntity<User>(HttpStatus.UNAUTHORIZED);
        }
        else {
        	HttpHeaders headers = new HttpHeaders();
        	return new ResponseEntity<User>(user, HttpStatus.CREATED);
        }
    }
      
      
    //-------------------Create a User--------------------------------------------------------
      
    @RequestMapping(value = "/user/", method = RequestMethod.POST)
    public ResponseEntity<Void> createUser(@RequestBody User user,    UriComponentsBuilder ucBuilder) {
        System.out.println("Creating User " + user.getEmail());
  
        if (userService.isUserExist(user)) {
            System.out.println("A User with name " + user.getEmail() + " already exists");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
  
        userService.saveUser(user);
  
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(user.getUserId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }
  
     
      
    //-------------------Update a User--------------------------------------------------------
      
    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    public ResponseEntity<User> updateUser(@PathVariable("id") int id, @RequestBody User user) {
        System.out.println("Updating User " + id);
          
        User currentUser = userService.findById(id);
          
        if (currentUser==null) {
            System.out.println("User with id " + id + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
  
        currentUser.setEmail(user.getEmail());
          
        userService.updateUser(currentUser);
        return new ResponseEntity<User>(currentUser, HttpStatus.OK);
    }
  
     
     
    //-------------------Delete a User--------------------------------------------------------
      
    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteUser(@PathVariable("id") int id) {
        System.out.println("Fetching & Deleting User with id " + id);
  
        User user = userService.findById(id);
        if (user == null) {
            System.out.println("Unable to delete. User with id " + id + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
  
        userService.deleteUserById(id);
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }
  
      
     
    //-------------------Delete All Users--------------------------------------------------------
      
    @RequestMapping(value = "/user/", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteAllUsers() {
        System.out.println("Deleting All Users");
  
        userService.deleteAllUsers();
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }
    
    // ------------------------- Get leaugeID from user id------------------------
    
    @RequestMapping(value = "/user/getLeagueIDFromUser", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getLeagueIDFromUser(@RequestParam (value="UserID") String UserID ){
    	System.out.println("asdasda");
    	return "[{\"LeagueID\": \"" + UserServiceImpl.getLeagueIDFromUser(Integer.parseInt(UserID)) + "\"}]";
    }
  
}