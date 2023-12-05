package com.gems.psl.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gems.psl.model.tourbatch.Booking;
import com.gems.psl.repo.BookingRepository;

@Service
public class BookingService {
	@Autowired
	private BookingRepository repo;
	
	public List<Booking> getAllBooking(){
		return (List<Booking>) repo.findAll();
	}
	
	public Booking saveBooking(Booking book) {
		return repo.save(book);
	}
}
