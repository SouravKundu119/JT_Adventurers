package com.gems.psl.model.tour;

import java.util.Map;
import java.util.TreeMap;

public enum DifficultyLevel {
	HIGH("High"), MODERATE("Moderate"), EASY("Easy");

	private String difficultyLevel;

	DifficultyLevel(String difficultyLevel) {
		this.difficultyLevel = difficultyLevel;
	}


	@Override
	public String toString() {
		return this.difficultyLevel;
	}

	public static Map<String, String> getPair() {
		Map<String, String> difficultyLevels = new TreeMap<>();
		for (DifficultyLevel difficultyLevel : DifficultyLevel.values()) {
			difficultyLevels.put(difficultyLevel.toString().toUpperCase(), difficultyLevel.toString());
		}
		return difficultyLevels;
	}
}
