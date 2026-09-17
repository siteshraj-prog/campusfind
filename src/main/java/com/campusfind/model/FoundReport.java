package com.campusfind.model;

import java.time.LocalDate;

public class FoundReport {
	private final long id;
	private final String reporter;
	private final String itemName;
	private final String description;
	private final String location;
	private final LocalDate date;

	public FoundReport(long id, String reporter, String itemName, String description, String location, LocalDate date) {
		this.id = id;
		this.reporter = reporter;
		this.itemName = itemName;
		this.description = description;
		this.location = location;
		this.date = date;
	}

	public FoundReport(String reporter, String itemName, String description, String location, LocalDate date) {
		this(0, reporter, itemName, description, location, date);
	}

	public long getId() {
		return id;
	}

	public String getReporter() {
		return reporter;
	}

	public String getItemName() {
		return itemName;
	}

	public String getDescription() {
		return description;
	}

	public String getLocation() {
		return location;
	}

	public LocalDate getDate() {
		return date;
	}

	@Override
	public String toString() {
		return id + ": " + itemName + " - " + description + " at " + location + " (" + date + ")";
	}
}
