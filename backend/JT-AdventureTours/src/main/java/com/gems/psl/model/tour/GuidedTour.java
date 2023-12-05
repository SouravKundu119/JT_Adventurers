package com.gems.psl.model.tour;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gems.psl.model.tourbatch.TourBatch;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "guidedTour")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuidedTour {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private String name;

	@NotNull
	private String startAt;

	@NotNull
	private String endAt;

	@NotNull
	private int days;

	@NotNull
	private int nights;

	@Enumerated(EnumType.STRING)
	private Mode mode;

	@Enumerated(EnumType.STRING)
	private DifficultyLevel difficultyLevel;

	@Embedded
	private Itinerary itinerary;

	@JsonIgnore
	@OneToMany(mappedBy = "guidedTour")
	private List<TourBatch> tourBatch;

	public GuidedTour(Long id, @NotNull String name, @NotNull String startAt, @NotNull String endAt,
			@NotNull Integer days, Mode mode, DifficultyLevel difficulty, Itinerary itinerary) {
		super();
		this.id = id;
		this.name = name;
		this.startAt = startAt;
		this.endAt = endAt;
		this.days = days;
		this.mode = mode;
		this.difficultyLevel = difficulty;
		this.itinerary = itinerary;
	}
	
	
	
}