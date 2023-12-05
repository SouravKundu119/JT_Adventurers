package com.gems.psl.model.tourbatch;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gems.psl.model.tour.GuidedTour;
import com.gems.psl.model.user.Guide;
import com.gems.psl.model.user.Traveller;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Entity
@Table(name="tourBatch")
@NoArgsConstructor
public class TourBatch {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate startDate;
	
	@NotNull
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate endDate;
	
	@Enumerated(EnumType.STRING)
	private Batchstatuses status;
	
	@NotNull
	private int capacity;
	
	@NotNull
	private int availableSeats;
	
	@NotNull
	private double perParticipantCost;
	
	@Embedded
	private Guide guide;
	
	@ElementCollection
	@CollectionTable(name="batchTravellers", joinColumns = @JoinColumn(name="tourBatchId"))
	public List<Traveller> batch_travellers;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="guidedTour_id")
	private GuidedTour guidedTour;
	
	public void setGuidedTour(GuidedTour gd) {
		this.guidedTour = gd;
	}

	public TourBatch(Long id, @NotNull LocalDate startDate, @NotNull LocalDate endDate, Batchstatuses status,
			@NotNull int capacity, @NotNull int availableSeats, @NotNull double perParticipantCost, Guide guide,
			List<Traveller> batch_travellers) {
		super();
		this.id = id;
		this.startDate = startDate;
		this.endDate = endDate;
		this.status = status;
		this.capacity = capacity;
		this.availableSeats = availableSeats;
		this.perParticipantCost = perParticipantCost;
		this.guide = guide;
		this.batch_travellers = batch_travellers;
	}

	public TourBatch(Long id, @NotNull LocalDate startDate, @NotNull LocalDate endDate, Batchstatuses status,
			@NotNull int capacity, @NotNull int availableSeats, @NotNull double perParticipantCost, Guide guide,
			List<Traveller> batch_travellers, GuidedTour guidedTour) {
		super();
		this.id = id;
		this.startDate = startDate;
		this.endDate = endDate;
		this.status = status;
		this.capacity = capacity;
		this.availableSeats = availableSeats;
		this.perParticipantCost = perParticipantCost;
		this.guide = guide;
		this.batch_travellers = batch_travellers;
		this.guidedTour = guidedTour;
	}
	
}
