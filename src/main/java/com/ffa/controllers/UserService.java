package com.ffa.controllers;
import java.util.List;
import com.ffa.models.User;
 
public interface UserService {
     
    User findById(int id);
    User findByEmail(String email);
    void saveUser(User user);
    void updateUser(User user);
    void deleteUserById(int id);
    List<User> findAllUsers(); 
    void deleteAllUsers();
    public boolean isUserExist(User user);
     
}