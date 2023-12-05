package com.gems.psl.model.user;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class Guide {
	
	@Embedded
	private MobileNumber mobile;
	
	@Embedded
	private Name name;

}
