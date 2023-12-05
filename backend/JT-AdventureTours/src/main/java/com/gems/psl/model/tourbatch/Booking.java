package com.gems.psl.model.tourbatch;

import java.time.LocalDate;
import java.util.List;
import com.gems.psl.model.user.*;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="booking")
@AllArgsConstructor
@NoArgsConstructor

public class Booking {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate date;
	
	@NotNull
	private Double amount;
	
	@NotNull
	private int batchId;
	
	@NotNull
	private String username;
	
	@ElementCollection
	@CollectionTable(name="bookingTravellers", joinColumns = @JoinColumn(name="bookingId"))
	public List<Traveller> travellers;
}
