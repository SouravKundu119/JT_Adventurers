package com.gems.psl.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gems.psl.model.tour.User;
import com.gems.psl.service.UserService;



@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
@RestController
@RequestMapping("/auth")
public class UserController {
	@Autowired
	private UserService userService;
	
	  @PostMapping("/register")
	    public String addNewUser(@RequestBody User userInfo){
	        return userService.addUser(userInfo);
	    }
	  
	  @GetMapping("/showUser")
	 @PreAuthorize("hasAuthority('ADMIN')")
		public List<User> allUser() {
			return userService.getAllUser();
		}
}
