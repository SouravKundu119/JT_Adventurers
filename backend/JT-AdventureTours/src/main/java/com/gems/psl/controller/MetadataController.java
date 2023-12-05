package com.gems.psl.controller;
import java.util.Map;
import java.util.Optional;
 
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.gems.psl.service.MetadataService;
 
@RestController
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
@RequestMapping("/metadata")
public class MetadataController {
 
	MetadataService serv = new MetadataService();
 
	@GetMapping("/modes")
	public ResponseEntity<Map<String, String>> getModes() {
		
		Map<String, String> modes =  serv.allModes();
		if(modes.size() <= 0 ) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}else {
			return ResponseEntity.of(Optional.of(modes));
		}
	}
 
	@GetMapping("/difficultyLevels")
	public ResponseEntity<Map<String, String>> getDifficultyLevels() {
		Map<String, String> difficulty = serv.allDifficultylevels();
		
		if(difficulty.size() <= 0 ) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}else {
			return ResponseEntity.of(Optional.of(difficulty));
		}
	
	}
 
	@GetMapping("/roomTypes")
	public ResponseEntity<Map<String, String>> getRoomTypes() {
		
		Map<String, String> rooms = serv.allRoomTypes();
		if(rooms.size() <= 0 ) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}else {
			return ResponseEntity.of(Optional.of(rooms));
		}
	}
}
 