package com.gems.psl.test;
//import static com.fasterxml.jackson.datatype;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.hibernate.engine.jdbc.batch.spi.Batch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gems.psl.controller.TourBatchController;
import com.gems.psl.model.tourbatch.*;
import com.gems.psl.model.user.Guide;
import com.gems.psl.model.user.MobileNumber;
import com.gems.psl.model.user.Name;
import com.gems.psl.model.user.Traveller;
import com.gems.psl.model.tour.*;
import com.gems.psl.service.TourBatchService;
import com.gems.psl.service.TourService;

@ExtendWith(MockitoExtension.class)
public class TourBatchControllerTest {
	
	@Mock
	TourBatchService batchService;
	
	@Mock
	TourService tourService;

	@InjectMocks
	TourBatchController batchController;
	
	MockMvc mockMvc;
	
	
	ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
	ObjectWriter objectWriter = objectMapper.writer();
	
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(batchController).build();
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
		
		TourBatch batch1 = new TourBatch(1L, LocalDate.parse("29-11-2023", formatter), LocalDate.parse("04-12-2023", formatter), Batchstatuses.ACTIVE , 4, 2, 100000.00, guide,traveller);
		
		return batch1;
	}
	
	@Test
	public void testAllToursBatches() throws Exception {
		TourBatch batch1 = makeTourBatch();
		
		List<TourBatch> batchList = List.of(batch1);
		Mockito.when(batchService.getAllToursBatch()).thenReturn(batchList);
		
		// Mocking the get request.
				mockMvc.perform(MockMvcRequestBuilders.get("/batches").contentType(MediaType.APPLICATION_JSON))
						.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))
						.andExpect(jsonPath("$[0].capacity", is(4)))
						.andExpect(jsonPath("$[0].perParticipantCost", is(100000.0)));
	}
	
	
	@Test
	public void testGetTourBatchById() throws Exception {
		TourBatch batch1 = makeTourBatch();
		Mockito.when(batchService.getAllById(1L)).thenReturn(batch1);
		
		// Mocking the get request.
				mockMvc.perform(MockMvcRequestBuilders.get("/batches/1").contentType(MediaType.APPLICATION_JSON))
						.andExpect(status().isOk()).andExpect(jsonPath("$", notNullValue()))
						.andExpect(jsonPath("$.status", is("ACTIVE")))
						.andExpect(jsonPath("$.capacity", is(4)));
	}
	
	@Test
	public void testAddTourBatch()throws Exception {
		TourBatch tourBatch1=makeTourBatch();
		String content=objectWriter.writeValueAsString(tourBatch1);

		Mockito.when(batchService.saveTourBatch(1L,tourBatch1)).thenReturn(tourBatch1);
		
		//Mocking the get request.
		mockMvc.perform(MockMvcRequestBuilders
				.post("/batches/tours/1")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(content))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", notNullValue()))
		.andExpect(jsonPath("$.capacity", is(4)))
		.andExpect(jsonPath("$.perParticipantCost", is(100000.0)));	
	}
}
