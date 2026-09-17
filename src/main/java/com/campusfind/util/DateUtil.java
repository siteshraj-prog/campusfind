package com.campusfind.util;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public final class DateUtil {
	private DateUtil() {
	}

	public static LocalDate parse(String value) {
		if (value == null || value.isBlank()) {
			throw new IllegalArgumentException("Date must use yyyy-MM-dd");
		}
		try {
			return LocalDate.parse(value);
		} catch (DateTimeParseException exception) {
			throw new IllegalArgumentException("Date must use yyyy-MM-dd");
		}
	}
}
