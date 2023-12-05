package com.gems.psl.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.gems.psl.model.tour.GuidedTour;
import com.gems.psl.model.tourbatch.TourBatch;
import com.gems.psl.service.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
@RequestMapping("/tours")
public class TourController {
	@Autowired
	private TourService tour;

	@GetMapping
	public ResponseEntity<List<GuidedTour>> getAllTours() {
		List<GuidedTour> gTour = tour.getAllTours();
		if (gTour.size() <= 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} else {

			return ResponseEntity.of(Optional.of(gTour));
		}

	}

	@GetMapping("/{id}")
	public ResponseEntity<GuidedTour> getTourById(@PathVariable long id) {

		try {
			GuidedTour gTour = tour.getAllById(id);
			return ResponseEntity.of(Optional.of(gTour));

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

		}

	}

	@PostMapping
	public ResponseEntity<GuidedTour> newTour(@RequestBody GuidedTour guidedTour) {

		try {
			guidedTour.setDays(tour.calculateDays(guidedTour.getItinerary().getDayPlans()));
			guidedTour.setNights(tour.calculateNights(guidedTour.getDays()));
			GuidedTour gdTour = tour.saveTour(guidedTour);
			return ResponseEntity.of(Optional.of(gdTour));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

	}

	@PutMapping
	public ResponseEntity<GuidedTour> editTour(@RequestBody GuidedTour guidedTour) {
		try {
			guidedTour.setNights(tour.calculateNights(guidedTour.getDays()));
			guidedTour.setDays(tour.calculateDays(guidedTour.getItinerary().getDayPlans()));
			GuidedTour gTour = tour.updateTour(guidedTour);
			return ResponseEntity.of(Optional.of(gTour));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteTour(@PathVariable long id) {
		boolean dTour = tour.deleteTour(id);
		if (dTour == false) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} else {
			return ResponseEntity.of(Optional.of(dTour));
		}
	}

	@GetMapping("/{id}/batches")
	public ResponseEntity<List<TourBatch>> getAllBatches(@PathVariable Long id) {
		List<TourBatch> tBatches = tour.getAllBatches(id);
		if (tBatches.size() <= 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} else {
			return ResponseEntity.of(Optional.of(tBatches));
		}
	}

 
	@GetMapping("/days")
	public ResponseEntity<?> durationFilter(@RequestParam(name="maxDays",required = false) Integer maxDays,
			@RequestParam(name="minDays",required = false) Integer minDays){
		try {
			return new ResponseEntity<>(tour.getTourByDays(maxDays, minDays), HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}