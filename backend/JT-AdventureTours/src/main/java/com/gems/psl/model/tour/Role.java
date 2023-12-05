package com.gems.psl.model.tour;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum Role {
	
	ADMIN("admin"), CUSTOMER("customer");
 
	
	private String role;
 
	public String displayString() {
		return this.role;
	}
}