package com.gems.psl.model.tour;

import java.util.List;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Embeddable
public class Itinerary {



	@ElementCollection
	@CollectionTable(name = "dayPlan", joinColumns = @JoinColumn(name = "itinerary_id"))
	private List<DayPlan> dayPlans;
	
	public Itinerary(List<DayPlan> dayPlans) {
		this.dayPlans=dayPlans;
	}

}
