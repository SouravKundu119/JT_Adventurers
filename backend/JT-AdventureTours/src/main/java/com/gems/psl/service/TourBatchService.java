package com.gems.psl.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gems.psl.model.tour.Email;
import com.gems.psl.model.tour.GuidedTour;
import com.gems.psl.model.tour.User;
import com.gems.psl.model.tourbatch.Batchstatuses;
import com.gems.psl.model.tourbatch.Booking;
import com.gems.psl.model.tourbatch.TourBatch;
import com.gems.psl.model.user.Traveller;
import com.gems.psl.repo.BookingRepository;
import com.gems.psl.repo.TourBatchRepository;
import com.gems.psl.repo.UserRepository;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Service
public class TourBatchService {

	@Autowired
	private TourBatchRepository trepo;
	
	@Autowired
	private UserRepository userrepository;
	
	@Autowired
	private JwtService jwtservice; 
	
	@Autowired
	private TourService tourService;
	
	@Autowired
	private BookingRepository bookingRepo;
	
	@Enumerated(EnumType.STRING)
	private Batchstatuses status;

	public List<TourBatch> getAllToursBatch() {
		return (List<TourBatch>) trepo.findAll();
	}
	
	public String getRole(String token) {
		int n = token.length();
		String str = "";
		for (int i = 7; i < n; i++) {
			str += token.charAt(i);
		}
		String s = jwtservice.extractUsername(str);
		Email userName = new Email(s);
		Optional<User> user = userrepository.findByUserName(userName);
		String role = user.get().getRole().toString();
		return role;
	}

	public TourBatch getAllById(long numb) {
		if (trepo.findById(numb).get() != null) {
			return trepo.findById(numb).get();
		} else {
			return null;
		}
	}
	
	public LocalDate calculateEndDate(GuidedTour gt, LocalDate startDate, TourBatch tb) {
		int duration = gt.getDays();
		LocalDate endDate = startDate.plusDays(duration);
		tb.setEndDate(endDate);
		return endDate;
	}

	public TourBatch saveTourBatch(Long id,TourBatch tourBatch) {
		GuidedTour gt = tourService.getAllById(id);
		tourBatch.setGuidedTour(gt);
		tourBatch.setEndDate(calculateEndDate(gt, tourBatch.getStartDate(), tourBatch ));
		tourBatch.setAvailableSeats(tourBatch.getCapacity());
		tourBatch.setStatus(Batchstatuses.ACTIVE);
		updateStatusOfBatch();
		return trepo.save(tourBatch);
	}

	public TourBatch updateTourBatch(TourBatch tour) {

		return trepo.save(tour);

	}
	
	public TourBatch editTourBatch(TourBatch tourBatch) {
		Optional<TourBatch> tb = trepo.findById(tourBatch.getId());
		if(tb.isPresent()) {
			GuidedTour gt = tb.get().getGuidedTour();
			tourBatch.setGuidedTour(gt);
			tourBatch.setEndDate(calculateEndDate(gt, tourBatch.getStartDate(), tourBatch ));
			return trepo.save(tourBatch);
		}else {
			return null;
		}

	}

	public boolean deleteTourBatch(Long id) {
		if (trepo.existsById(id)) {
			trepo.deleteById(id);
			return true;
		} else {
			return false;
		}
	}



	public List<TourBatch> getTourBatchAfterStartingDate(LocalDate minDate) {
		return trepo.findByStartDateAfter(minDate);
	}

	public List<TourBatch> getTourBatchBeforeStartingDate(LocalDate maxDate) {
		return trepo.findByStartDateBefore(maxDate);
	}

	public List<TourBatch> getTourBatchOnStartingDate(LocalDate date) {
		return trepo.findByStartDate(date);
	}

	public List<TourBatch> getTourBatchBetweenDates(LocalDate minDate, LocalDate maxDate) {
		return trepo.findByStartDateBetween(minDate, maxDate);
	}

	public Booking updateBooking(Booking booking) {
		TourBatch batch = trepo.findById((long)booking.getBatchId()).get();
		if(batch != null) {
			int availSeats=(int)batch.getAvailableSeats();
			List<Traveller> travellers = booking.getTravellers();
			if(availSeats >= travellers.size()) {
				availSeats = availSeats - travellers.size();
				batch.setAvailableSeats(availSeats);
			}else {
				return null;
			}
			if(batch.getAvailableSeats()==0) {
				batch.setStatus(status.FULL);
			}
			
			int participants = batch.getCapacity() - batch.getAvailableSeats();
			
			for(Traveller traveller:travellers) {
				participants=participants+1;
				traveller.setSequence(participants);
			}
			
			booking.setTravellers(travellers);
			List<Traveller> addTravellers=batch.getBatch_travellers();
			addTravellers.addAll(travellers);
			
			batch.setBatch_travellers(addTravellers);
			trepo.save(batch);
			return bookingRepo.save(booking);
		}
		return null;
	}
	
	public void updateStatusOfBatch()
	{
		LocalDate currentDate = LocalDate.now();
		List<TourBatch> tourList = getAllToursBatch();
		
		for(TourBatch TBatch : tourList) {
			if((TBatch.getAvailableSeats() == 0)){ 
				TBatch.setStatus(Batchstatuses.FULL);
				updateTourBatch(TBatch);
			}else if(((currentDate.isAfter(TBatch.getStartDate()))  && (currentDate.isBefore(TBatch.getEndDate()))) && (TBatch.getAvailableSeats() < TBatch.getCapacity())) { // replace 20 by Available Seats
				TBatch.setStatus(Batchstatuses.IN_PROGRESS); 
				updateTourBatch(TBatch);
			}else if((currentDate.isAfter(TBatch.getStartDate()) || currentDate.isEqual( TBatch.getStartDate()))&& TBatch.getAvailableSeats() == TBatch.getCapacity()) { // Replace 20 by available Seats
				TBatch.setStatus(Batchstatuses.CANCELLED);  
				updateTourBatch(TBatch);
			}else if(currentDate.isAfter(TBatch.getEndDate())) {
				TBatch.setStatus(Batchstatuses.COMPLETED);	
				updateTourBatch(TBatch);
			}else {
				TBatch.setStatus(Batchstatuses.ACTIVE);
				updateTourBatch(TBatch);
			}
		}	
	}
		
	
}
