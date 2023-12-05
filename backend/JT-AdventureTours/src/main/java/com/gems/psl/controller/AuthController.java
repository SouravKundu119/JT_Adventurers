package com.gems.psl.controller;


import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gems.psl.model.tour.AuthRequest;
import com.gems.psl.service.JwtService;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
@RestController
@RequestMapping("/auth")
public class AuthController {
	 @Autowired
	    private JwtService jwtService;
	 @Autowired
	    private AuthenticationManager authenticationManager;
	 
	 
	 
	 
	 @PostMapping("/login")
	 public ResponseEntity<Map<String, Object>> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
	     Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));
	  
	     if (authentication.isAuthenticated()) {
	         UserDetails userDetails = (UserDetails) authentication.getPrincipal();
	         String token = jwtService.generateToken(userDetails);
	  
	         // Get the first authority as a string
	         String singleAuthority = userDetails.getAuthorities().stream()
	                 .findFirst()
	                 .map(GrantedAuthority::getAuthority)
	                 .orElse("");
	  
	         // Create a response map
	         Map<String, Object> response = new TreeMap<>();
	         
	         
	         response.put("token", token);
	         response.put("role", singleAuthority);
	        
	  
	         return ResponseEntity.ok(response);
	     } else {
	         throw new UsernameNotFoundException("Invalid user request!");
	     }
	 }


}
