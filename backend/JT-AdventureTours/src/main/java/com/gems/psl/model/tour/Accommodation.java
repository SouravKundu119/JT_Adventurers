package com.gems.psl.model.tour;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class Accommodation {

	private String hotelName;
	private String address;

	@Enumerated(EnumType.STRING)
	private RoomType roomType;

}