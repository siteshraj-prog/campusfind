package com.campusfind.util;

public final class InputValidator {
	private InputValidator() {
	}

	public static String required(String value, String field) {
		if (value == null || value.trim().isEmpty()) {
			throw new IllegalArgumentException(field + " is required");
		}
		return value.trim();
	}
}
