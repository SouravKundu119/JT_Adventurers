package com.gems.psl.model.tour;

import java.util.Map;
import java.util.TreeMap;

public enum RoomType {
	TWIN_BED("Twin Bed"), DOUBLE_BED("Double Bed");

	private String roomType;

	RoomType(String roomType) {
		this.roomType = roomType;
	}

	@Override
	public String toString() {
		return this.roomType;
	}

	public static Map<String, String> getPair() {
		Map<String, String> roomTypes = new TreeMap<>();
		for (RoomType roomType : RoomType.values()) {
			roomTypes.put(roomType.toString().toUpperCase(), roomType.toString());
		}
		return roomTypes;
	}
}