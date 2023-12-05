package com.gems.psl.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gems.psl.model.tour.GuidedTour;
import com.gems.psl.model.tourbatch.Batchstatuses;
import com.gems.psl.model.tourbatch.Booking;
import com.gems.psl.model.tourbatch.TourBatch;
import com.gems.psl.repo.TourBatchRepository;
import com.gems.psl.service.BookingService;
import com.gems.psl.service.TourBatchService;
import com.gems.psl.service.TourService;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
@RequestMapping("/batches")
public class TourBatchController {
	@Autowired
	private TourBatchRepository tourRepo;

	@Autowired
	private TourBatchService tours;

	@Autowired
	private TourService tourService;
	
	@Autowired
	private BookingService bookingService;



	
	@GetMapping
	public ResponseEntity<List<TourBatch>> allTourBatches(
			@RequestHeader(value = "Authorization", required = false) String token) {
 
		List<TourBatch> gt = tours.getAllToursBatch();
		
		if (token == null) {
			List<TourBatch> active = new ArrayList<>();
			for (TourBatch batch : gt) {
				if (batch.getStatus().equals(Batchstatuses.ACTIVE)) {
					tours.updateStatusOfBatch();
					active.add(batch);
				}
			}
			return ResponseEntity.of(Optional.of(active));
		}
		String currRole = tours.getRole(token);
		if (gt.size() <= 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} else {
			if (currRole.equals("ADMIN")) {
				return ResponseEntity.of(Optional.of(gt));
			}
 
			else {
				List<TourBatch> active = new ArrayList<>();
				for (TourBatch batch : gt) {
					if (batch.getStatus().equals(Batchstatuses.ACTIVE)) {
						active.add(batch);
					}
				}
				return ResponseEntity.of(Optional.of(active));
			}
		}
	}

	
	@GetMapping("/{id}")
	public ResponseEntity<TourBatch> getTourBatchById(@PathVariable Long id) {
		
		try {
			tours.updateStatusOfBatch();
			TourBatch tourBatch = tours.getAllById(id);
			return ResponseEntity.of(Optional.of(tourBatch));
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
	}

	@PostMapping("/tours/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<TourBatch> newTourBatch(@PathVariable Long id, @RequestBody TourBatch tourBatch ) {
		try{
			
			TourBatch tBatch = tours.saveTourBatch(id,tourBatch);
			return ResponseEntity.of(Optional.of(tBatch));
			
		}catch(Exception e){
			e.printStackTrace();
			
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
 
	}

	@PutMapping
	public ResponseEntity<TourBatch> editTourBatch(@RequestBody TourBatch tourBatch) {
		try {
			TourBatch tb = tours.editTourBatch(tourBatch);
			return ResponseEntity.of(Optional.of(tb));
			
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteTour(@PathVariable Long id) {
		Boolean deleteStatus =  tours.deleteTourBatch(id);
		if(deleteStatus == false) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}else {
			return ResponseEntity.of(Optional.of(deleteStatus));
		}
	}

	@GetMapping("/startDate")
	public List<TourBatch> getTourBatchsByStartDate(
			@RequestParam(name = "minDate", required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate minDate,
			@RequestParam(name = "maxDate", required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate maxDate) {
		List<TourBatch> filteredTourBatch = new ArrayList<>();

		if (minDate != null && maxDate != null) {
			filteredTourBatch.addAll(tours.getTourBatchBetweenDates(minDate, maxDate));
		} else if (minDate == maxDate) {
			filteredTourBatch.addAll(tours.getTourBatchOnStartingDate(minDate));
		}

		else if (minDate != null) {
			filteredTourBatch.addAll(tours.getTourBatchAfterStartingDate(minDate));
			filteredTourBatch.addAll(tours.getTourBatchOnStartingDate(minDate));

		}

		else if (maxDate != null) {
			filteredTourBatch.addAll(tours.getTourBatchBeforeStartingDate(maxDate));
			filteredTourBatch.addAll(tours.getTourBatchOnStartingDate(minDate));
		} else {
			IllegalArgumentException("Invalid condition: ");
		}

		return filteredTourBatch;

	}
	
	@PostMapping("/book")
	public Booking newBooking(@RequestBody Booking booking) {
		return tours.updateBooking(booking);
	}
	


	private void IllegalArgumentException(String string) {
		// TODO Auto-generated method stub
		
	}



}
