package com.gems.psl.service;

import java.util.Map;
import org.springframework.stereotype.Service;
import com.gems.psl.model.tour.DifficultyLevel;
import com.gems.psl.model.tour.Mode;
import com.gems.psl.model.tour.RoomType;

@Service
public class MetadataService {

	Map<String, String> difficultyLevel = DifficultyLevel.getPair();

	Map<String, String> roomType = RoomType.getPair();

	Map<String, String> mode = Mode.getPair();

	public Map<String, String> allModes() {

		return mode;
	}

	public Map<String, String> allDifficultylevels() {
		return difficultyLevel;
	}

	public Map<String, String> allRoomTypes() {
		return roomType;
	}
}
