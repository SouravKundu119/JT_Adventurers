package com.gems.psl.configuration;





import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.gems.psl.model.tour.User;




public class UserInfoUserDetails implements UserDetails{

	private String name;
    private String password;
 //   private Role authorities;
    private String authorities;
    
     UserInfoUserDetails(User userInfo) {
       //name=userInfo.getEmail().getId();
    	 name=userInfo.getUserName().getId();
        password=userInfo.getPassword();
        authorities= userInfo.getRole().name();
    }
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singleton(new SimpleGrantedAuthority(authorities));
	
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
