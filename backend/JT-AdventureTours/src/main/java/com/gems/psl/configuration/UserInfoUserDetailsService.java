package com.gems.psl.configuration;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.gems.psl.model.tour.Email;
import com.gems.psl.model.tour.User;
import com.gems.psl.repo.UserRepository;

@Component
public class UserInfoUserDetailsService implements UserDetailsService {
	 @Autowired
	    private UserRepository repository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Email email =new Email(username);
		 Optional<User> userInfo = repository.findById(email);
		return userInfo.map(UserInfoUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("user not found " + username));
	}

}
