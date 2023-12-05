package com.gems.psl.model.tour;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class DayPlan {

	private int dayCount;
	private String place;
	private double distance;
	private String activity;

	@Embedded
	private Accommodation accomodation;

}