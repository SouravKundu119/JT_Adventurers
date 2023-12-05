package com.gems.psl.model.tour;

import java.util.Map;
import java.util.TreeMap;

public enum Mode {
	WALK("Walk"), BICYCLE("Bicycle"), MOTORBIKE("Motorbike");

	private String mode;

	Mode(String mode) {
		this.mode = mode;
	}



	@Override
	public String toString() {
		return this.mode;
	}

	public static Map<String, String> getPair() {
		Map<String, String> modes = new TreeMap<>();
		for (Mode mode : Mode.values()) {
			modes.put(mode.toString().toUpperCase(), mode.toString());
		}
		return modes;
	}
}