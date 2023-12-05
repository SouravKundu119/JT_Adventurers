package com.gems.psl.test;

import static org.assertj.core.api.Assertions.assertThat;
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
import com.gems.psl.repo.TourRepository;
import com.gems.psl.service.TourService;

@ExtendWith(MockitoExtension.class)
public class TourServiceTest {

	@Mock
	private TourRepository tourRepository;

	@InjectMocks
	private TourService tourService;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	public GuidedTour makeTour() {
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
		return guidedTour1;
	}

	@Test
	public void testAllTours() throws Exception {
		// GuidedTour1
		GuidedTour guidedTour1 = makeTour();

		// GuidedTour2
		Accommodation accomodation4 = new Accommodation("The Maharaja Rooms", "Chandani Chowk", RoomType.DOUBLE_BED);
		DayPlan dayPlan4 = new DayPlan(0, "Nagpur", 0, "reporting between 5 pm to 7 pm. Introduction, tour briefing and ice breaker",
				accomodation4);
		Accommodation accomodation5 = new Accommodation("Sun Rise Hotel", "Madwadi Tola", RoomType.TWIN_BED);
		DayPlan dayPlan5 = new DayPlan(1, "Aurangabad", 474, "Ride on Samruddhi Highway.",
				accomodation5);
		Accommodation accomodation6 = new Accommodation("Ganga Kinare Hotel", "Ganga Road", RoomType.TWIN_BED);
		DayPlan dayPlan6 = new DayPlan(2, "Aurangabad", 0, "Ride on Mumbai Agra road. Visit Gurudwara at Dhule",
				accomodation6);
		List<DayPlan> dayPlanList2 = List.of(dayPlan4, dayPlan5, dayPlan6);
		Itinerary itineraray2 = new Itinerary(dayPlanList2);
		GuidedTour guidedTour2 = new GuidedTour(2L, "Nagpur-Aurangabad", "Nagpur", "Aurangabad", 3, Mode.MOTORBIKE,
				DifficultyLevel.MODERATE, itineraray2);

		// Making list of guided tour
		List<GuidedTour> guidedTourList = List.of(guidedTour1, guidedTour2);

		// Mocking the findAll()
		Mockito.when(tourRepository.findAll()).thenReturn(guidedTourList);

		// Testing the result expected and actual
		assertThat(tourService.getAllTours()).isEqualTo(guidedTourList);
	}

	@Test
	public void testGetTourById() {
		GuidedTour guidedTour1 = makeTour();

		// Mocking the method findById()
		Mockito.when(tourRepository.findById(1L)).thenReturn(Optional.of(guidedTour1));

		// Testing the result expected and actual
		assertThat(tourService.getAllById(guidedTour1.getId())).isEqualTo(guidedTour1);
	}

	@Test
	public void testAddTour() {
		GuidedTour guidedTour1 = makeTour();

		// Mocking the save() method
		Mockito.when(tourRepository.save(guidedTour1)).thenReturn(guidedTour1);

		// Testing the result expected and actual
		assertThat(tourService.saveTour(guidedTour1)).isEqualTo(guidedTour1);
	}

//	@Test
//	public void testUpdateTour() {
//		GuidedTour guidedTour1=makeTour();
//		GuidedTour guidedTour2=new GuidedTour();
//		
//		//Mimicking the behaviour of findById()
//		Mockito.when(tourRepository.findById(1L)).thenReturn(Optional.of(guidedTour1));
//		guidedTour1.setName("Manali-Sissu");
//		guidedTour1.setStartAt("Manali");
//		guidedTour1.setEndAt("Sissu");
//		guidedTour1.setDays(3);
//		guidedTour1.setMode(Mode.WALK);
//		guidedTour1.setDifficulty(DifficultyLevel.HIGH);
//		guidedTour1.setItinerary(guidedTour1.getItinerary());
//		Mockito.when(tourRepository.save(guidedTour1)).thenReturn(guidedTour1);
//		//Testing the result expected and actual result obtained
//		assertThat(tourService.updateTour(guidedTour1)).isEqualTo(guidedTour1);	
//	}

//	@Test
//	public void testDeleteById() {
//		GuidedTour guidedTour1=makeTour();
//		Mockito.when(tourRepository.findById(1L)).thenReturn(Optional.of(guidedTour1));
//		assertThat(tourRepository.findById(1L)).
//	}

}
