package com.gems.psl.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.gems.psl.model.tour.GuidedTour;
import java.util.List;

@Repository
public interface TourRepository extends CrudRepository<GuidedTour, Long> {
	List<GuidedTour> findByDaysGreaterThan(int maxDays);

	List<GuidedTour> findByDaysLessThan(int minDays);

	List<GuidedTour> findByDaysEquals(int eqDays);
	
	List<GuidedTour> findByDaysBetween(int maxDays, int minDays);
}