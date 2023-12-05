package com.gems.psl.test;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.gems.psl.controller.TourController;
import com.gems.psl.model.tour.Accommodation;
import com.gems.psl.model.tour.DayPlan;
import com.gems.psl.model.tour.DifficultyLevel;
import com.gems.psl.model.tour.GuidedTour;
import com.gems.psl.model.tour.Itinerary;
import com.gems.psl.model.tour.Mode;
import com.gems.psl.model.tour.RoomType;
import com.gems.psl.service.TourService;

import static org.hamcrest.Matchers.*;

@ExtendWith(MockitoExtension.class)
public class TourControllerTest {

	@Mock
	TourService tourService;

	@InjectMocks
	TourController tourController;

	MockMvc mockMvc;

	ObjectMapper objectMapper = new ObjectMapper();
	ObjectWriter objectWriter = objectMapper.writer();

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(tourController).build();
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

		// Mocking tourService.allTours()
		Mockito.when(tourService.getAllTours()).thenReturn(guidedTourList);

		// Mocking the get request.
		mockMvc.perform(MockMvcRequestBuilders.get("/tours").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[1].name", is("Nagpur-Aurangabad")))
				.andExpect(jsonPath("$[1].itinerary.dayPlans[0].place", is("Nagpur")));
	}

	@Test
	public void testGetTourById() throws Exception {
		GuidedTour guidedTour1 = makeTour();

		// Mimicking the behaviour of findById()
		Mockito.when(tourService.getAllById(1L)).thenReturn(guidedTour1);

		// Mocking the get request.
		mockMvc.perform(MockMvcRequestBuilders.get("/tours/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$", notNullValue()))
				.andExpect(jsonPath("$.name", is("Mumbai-Nasik")))
				.andExpect(jsonPath("$.itinerary.dayPlans[0].place", is("Mumbai")));
	}

	@Test
	@WithMockUser(authorities = "ADMIN")
	public void testAddTour() throws Exception {
		GuidedTour guidedTour1 = makeTour();
		String content = objectWriter.writeValueAsString(guidedTour1);

		Mockito.when(tourService.saveTour(guidedTour1)).thenReturn(guidedTour1);
		ResponseEntity<GuidedTour> responseEntity = tourController.newTour(guidedTour1);
		
		// Mocking the get request.
		mockMvc.perform(MockMvcRequestBuilders.post("/tours").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(content))
				.andExpect(status().isOk());
	}
}