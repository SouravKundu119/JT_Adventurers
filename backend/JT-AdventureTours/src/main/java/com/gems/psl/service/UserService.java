package com.gems.psl.service;






import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gems.psl.model.tour.User;
import com.gems.psl.repo.UserRepository;

//import com.JWTAuthentication.Model.UserInfo;
//import com.JWTAuthentication.repo.UserInfoRpository;
@Service
public class UserService {
@Autowired
private UserRepository repo;
@Autowired
private PasswordEncoder passwordEncoder;

public String addUser(User userInfo) {
    userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
    repo.save(userInfo);
    return "user added";
}
     public List<User> getAllUser(){
    	 return (List<User>)repo.findAll();
     }

}
