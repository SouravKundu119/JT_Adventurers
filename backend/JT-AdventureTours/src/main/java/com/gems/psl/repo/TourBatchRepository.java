package com.gems.psl.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.gems.psl.model.tourbatch.TourBatch;

public interface TourBatchRepository extends CrudRepository<TourBatch, Long> {
	List<TourBatch> findByStartDateAfter(LocalDate startDate);

	List<TourBatch> findByStartDateBefore(LocalDate startDate);

	List<TourBatch> findByStartDate(LocalDate startDate);

	List<TourBatch> findByStartDateBetween(LocalDate startDate, LocalDate endDate);
}