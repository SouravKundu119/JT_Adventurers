package com.gems.psl.model.user;

import org.springframework.data.annotation.Id;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class Traveller {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long sequence =1L;
	
	@Embedded
	private MobileNumber mobile;
	
	@Embedded
	private Name name;
}