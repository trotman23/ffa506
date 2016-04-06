package com.ffa.services;
import java.util.List;
import com.ffa.models.User;
 
public interface UserService {
     
    User findById(long id);
    User findByEmail(String email);
    void saveUser(User user);
    void updateUser(User user);
    void deleteUserById(long id);
    List<User> findAllUsers(); 
    void deleteAllUsers();
    public boolean isUserExist(User user);
     
}