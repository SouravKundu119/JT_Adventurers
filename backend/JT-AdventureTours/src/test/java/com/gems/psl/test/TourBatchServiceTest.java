package com.gems.psl.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gems.psl.model.tour.Accommodation;
import com.gems.psl.model.tour.DayPlan;
import com.gems.psl.model.tour.DifficultyLevel;
import com.gems.psl.model.tour.GuidedTour;
import com.gems.psl.model.tour.Itinerary;
import com.gems.psl.model.tour.Mode;
import com.gems.psl.model.tour.RoomType;
import com.gems.psl.model.tourbatch.Batchstatuses;
import com.gems.psl.model.tourbatch.TourBatch;
import com.gems.psl.model.user.Guide;
import com.gems.psl.model.user.MobileNumber;
import com.gems.psl.model.user.Name;
import com.gems.psl.model.user.Traveller;
import com.gems.psl.repo.TourBatchRepository;
import com.gems.psl.service.JwtService;
import com.gems.psl.service.TourBatchService;
import com.gems.psl.service.TourService;

@ExtendWith(MockitoExtension.class)
public class TourBatchServiceTest {
	
	@Mock
	private TourBatchRepository batchRepository;
	
	@Mock
	private TourService tourService;
	
	@Mock
	private JwtService jwtservice;

	@InjectMocks
	private TourBatchService batchService;
	
	GuidedTour guidedTour;
	
	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}
	
	public TourBatch makeTourBatch() {
		Accommodation accomodation1 = new Accommodation("The Oyo Rooms", "Zero Mile Road", RoomType.DOUBLE_BED);
		DayPlan dayPlan1 = new DayPlan(0, "Mumbai", 0, "reporting between 5 pm to 7 pm. Introduction, tour briefing and ice breaker",
				accomodation1);
		Accommodation accomodation2 = new Accommodation("The Royal Inns", "M G Road", RoomType.TWIN_BED);
		DayPlan dayPlan2 = new DayPlan(1, "Nasik", 155.0, "Ride on good highway on Mumbai Agra road. Stay and rest",
				accomodation2);
		Accommodation accomodation3 = new Accommodation("The Hill View", "CIty Center More", RoomType.TWIN_BED);
		DayPlan dayPlan3 = new DayPlan(2, "Nasik", 0.0, "Breakfast, Certifiate distribution and tour ends. Participants return home",
				accomodation3);
		List<DayPlan> dayPlanList1 = List.of(dayPlan1, dayPlan2, dayPlan3);
		Itinerary itineraray1 = new Itinerary(dayPlanList1);
		GuidedTour guidedTour1 = new GuidedTour(1L, "Mumbai-Nasik", "Mumbai", "Nasik", 3, Mode.MOTORBIKE,
				DifficultyLevel.MODERATE, itineraray1);
		
		MobileNumber mb1 = new MobileNumber("9062106097");
		Name n1 = new Name("Swarnali","Saha");
		MobileNumber mb2 = new MobileNumber("9900000010");
		Name n2 = new Name("Tushar","Gandhi");
		MobileNumber mb3 = new MobileNumber("9999999222");
		Name n3 = new Name("Bhavana","Pradhan");
		MobileNumber mb4 = new MobileNumber("9999999222");
		Name n4 = new Name("Ramesh","Pradhan");
		
		
		Guide guide = new Guide(mb1,n1);
		
		Traveller t1 = new Traveller(1, mb2, n2);
		Traveller t2 = new Traveller(1, mb3, n3);
		Traveller t3 = new Traveller(1, mb4, n4);
		
		List<Traveller> traveller = new ArrayList<>();
		traveller.add(t1);
		traveller.add(t2);
		traveller.add(t3);
		
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		
		TourBatch batch1 = new TourBatch(1L, LocalDate.parse("29-11-2023", formatter), LocalDate.parse("04-12-2023", formatter), Batchstatuses.ACTIVE , 4, 2, 100000.00, guide,traveller,guidedTour1);
		
		
		return batch1;
	}
	
	@Test
	public void testAllToursBatches() throws Exception {
		TourBatch batch1 = makeTourBatch();
		
		// Making list of guided tour
		List<TourBatch> tourBatchList = List.of(batch1);

		// Mocking the findAll()
		Mockito.when(batchRepository.findAll()).thenReturn(tourBatchList);

		// Testing the result expected and actual
		assertThat(batchService.getAllToursBatch()).isEqualTo(tourBatchList);
	}
	
	@Test
	public void testGetTourBatchById() {
		TourBatch batch1 = makeTourBatch();
		
		Mockito.when(batchRepository.findById(1L)).thenReturn(Optional.of(batch1));
		
		assertThat(batchService.getAllById(batch1.getId())).isEqualTo(batch1);
	}
	
	@Test
	public void testAddTourBatch() {
		TourBatch batch1 = makeTourBatch();
		GuidedTour guidedTour=batch1.getGuidedTour();
		
		Mockito.when(tourService.getAllById(1L)).thenReturn(guidedTour);
		
		// Mocking the save() method
		Mockito.when(batchRepository.save(batch1)).thenReturn(batch1);

		// Testing the result expected and actual
		assertThat(batchService.saveTourBatch(1L, batch1)).isEqualTo(batch1);
	}
	
	
	
}
