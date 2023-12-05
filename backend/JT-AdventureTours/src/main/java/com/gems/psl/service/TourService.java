package com.gems.psl.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gems.psl.model.tour.DayPlan;
import com.gems.psl.model.tour.GuidedTour;
import com.gems.psl.model.tourbatch.TourBatch;
import com.gems.psl.repo.*;

@Service
public class TourService {
	@Autowired
	private TourRepository repo;

	public List<TourBatch> getAllBatches(Long id) {
		if (repo.findById(id).isPresent()) {
			return repo.findById(id).get().getTourBatch();
		} else
			return null;
	}

	public List<GuidedTour> getAllTours() {
		return (List<GuidedTour>) repo.findAll();
	}

	public GuidedTour getAllById(long numb) {
		return repo.findById(numb).get();

	}

	public GuidedTour saveTour(GuidedTour tour) {
		return repo.save(tour);

	}

	public GuidedTour updateTour(GuidedTour tour) {
		return repo.save(tour);
	}

	public boolean deleteTour(long id) {
		if (repo.existsById(id)) {
			repo.deleteById(id);
			return true;
		} else {
			return false;
		}
	}
	
	public Integer calculateDays(List<DayPlan> dayplans) {
		int days = dayplans.size();
		return days;
	}

	public Integer calculateNights(Integer days) {
		int night = days - 1;
		return night;
	}

	public List<GuidedTour> getTourByDays(Integer maxDays, Integer minDays){
		List<GuidedTour> filterTours = new ArrayList<>();
		
		validateDays(maxDays, minDays);
		
		if(maxDays != null && minDays != null && maxDays == minDays) {
			filterTours.addAll(repo.findByDaysEquals(maxDays));
		}else if (maxDays != null && minDays != null && minDays < maxDays) {
			filterTours.addAll(repo.findByDaysBetween(minDays, maxDays));
		}else if(maxDays != null) {
			filterTours.addAll(repo.findByDaysLessThan(maxDays));
			filterTours.addAll(repo.findByDaysEquals(maxDays));
		}
		else if(minDays != null) {
			filterTours.addAll(repo.findByDaysGreaterThan(minDays));
			filterTours.addAll(repo.findByDaysEquals(minDays));
		}
		return filterTours;
	}
	
	private static void validateDays(Integer maxDays, Integer minDays) {
		if(maxDays == null && minDays == null) {
			throw new RuntimeException("Invalid Days");
		}
		if(maxDays != null && minDays != null && maxDays < minDays) {
			throw new RuntimeException("Invalid Days");
		}
	}
}