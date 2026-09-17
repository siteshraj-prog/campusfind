package com.campusfind.service;

import com.campusfind.model.FoundReport;
import com.campusfind.model.LostReport;
import com.campusfind.repository.FoundReportRepository;
import com.campusfind.repository.LostReportRepository;
import com.campusfind.util.InputValidator;
import java.util.List;

public class ReportService {
	private final LostReportRepository lostReports;
	private final FoundReportRepository foundReports;

	public ReportService() {
		this(new LostReportRepository(), new FoundReportRepository());
	}

	public ReportService(LostReportRepository lostReports, FoundReportRepository foundReports) {
		this.lostReports = lostReports;
		this.foundReports = foundReports;
	}

	public LostReport addLostReport(LostReport report) {
		validate(report);
		return lostReports.save(report);
	}

	public FoundReport addFoundReport(FoundReport report) {
		validate(report);
		return foundReports.save(report);
	}

	public List<LostReport> getLostReports() {
		return lostReports.findAll();
	}

	public List<FoundReport> getFoundReports() {
		return foundReports.findAll();
	}

	private void validate(LostReport report) {
		if (report == null) throw new IllegalArgumentException("Report is required");
		validateFields(report.getReporter(), report.getItemName(), report.getDescription(), report.getLocation(), report.getDate());
	}

	private void validate(FoundReport report) {
		if (report == null) throw new IllegalArgumentException("Report is required");
		validateFields(report.getReporter(), report.getItemName(), report.getDescription(), report.getLocation(), report.getDate());
	}

	private void validateFields(String reporter, String itemName, String description, String location, java.time.LocalDate date) {
		InputValidator.required(reporter, "Your name");
		InputValidator.required(itemName, "Item name");
		InputValidator.required(description, "Description");
		InputValidator.required(location, "Location");
		if (date == null) throw new IllegalArgumentException("Date is required");
	}
}
