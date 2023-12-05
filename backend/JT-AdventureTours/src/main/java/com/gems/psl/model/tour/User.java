package com.gems.psl.model.tour;



import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

	@Entity
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public class User {
		@Id
		@Embedded
	    //private Email email;
		private Email userName;
	    private String password;
	    @Enumerated(EnumType.STRING)
	    private Role role;
//	   
	    
	}

