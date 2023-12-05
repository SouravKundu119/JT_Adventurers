package com.gems.psl.model.tourbatch;

import java.util.Map;
import java.util.TreeMap;

public enum Batchstatuses {
	ACTIVE("Active"), FULL("Full"),IN_PROGRESS("In Progress"),CANCELLED("Cancelled"), COMPLETED("Completed");
	
	private String status;

	Batchstatuses(String statuses) {
		this.status = statuses;
	}

	public static Map<String, String> getPair() {
		Map<String, String> status = new TreeMap<>();
		for (Batchstatuses statuses : Batchstatuses.values()) {
			status.put(statuses.toString().toUpperCase(), statuses.toString());
		}
		return status;
	}
	
}
