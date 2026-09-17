package com.campusfind.cli;

import com.campusfind.model.FoundReport;
import com.campusfind.model.LostReport;
import com.campusfind.model.MatchResult;
import com.campusfind.service.MatchService;
import com.campusfind.service.ReportService;
import com.campusfind.util.DateUtil;
import com.campusfind.util.InputValidator;
import java.util.List;
import java.util.Scanner;

public class ConsoleApp {
	private static final String RESET = "\u001B[0m";
	private static final String CYAN = "\u001B[36m";
	private static final String GREEN = "\u001B[32m";
	private static final String YELLOW = "\u001B[33m";
	private static final String RED = "\u001B[31m";
	private static final String SEPARATOR = "=".repeat(72);
	private static final String THIN_SEPARATOR = "-".repeat(72);
	private static final boolean COLORS_ENABLED = supportsAnsi();

	private final Scanner scanner;
	private final ReportService reports;
	private final MatchService matcher;

	public ConsoleApp() {
		this(new Scanner(System.in), new ReportService());
	}

	public ConsoleApp(Scanner scanner, ReportService reports) {
		this.scanner = scanner;
		this.reports = reports;
		this.matcher = new MatchService();
	}

	public void run() {
		printBanner();
		while (true) {
			printDashboard();
			printMenu();
			String choice = readLine("Enter your choice");
			if (choice == null) return;
			try {
				switch (choice) {
					case "1" -> addLost();
					case "2" -> addFound();
					case "3" -> search();
					case "4" -> showMatches();
					case "0" -> {
						printSuccess("Thanks for using CampusFind. Goodbye!");
						return;
					}
					default -> {
						printError("Invalid choice. Please enter a number from 0 to 4.");
						pause();
					}
				}
			} catch (IllegalArgumentException exception) {
				printError(exception.getMessage());
				pause();
			}
		}
	}

	private void printBanner() {
		System.out.println(color(CYAN, SEPARATOR));
		System.out.println(color(CYAN, "                         CAMPUSFIND"));
		System.out.println("                 Lost and Found Management");
		System.out.println(color(CYAN, SEPARATOR));
		System.out.println("Welcome! Keep campus items connected to their owners.\n");
	}

	private void printDashboard() {
		int lostCount = reports.getLostReports().size();
		int foundCount = reports.getFoundReports().size();
		int matchCount = matcher.findMatches(reports.getLostReports(), reports.getFoundReports()).size();
		printSection("Dashboard");
		System.out.printf("  Lost reports: %-8d Found reports: %-8d Possible matches: %d%n", lostCount, foundCount, matchCount);
		System.out.println();
	}

	private void printMenu() {
		printSection("Main Menu");
		System.out.println("  1  Report lost item   - Save details of something you lost");
		System.out.println("  2  Report found item  - Save details of something you found");
		System.out.println("  3  Search reports     - Find items by keyword");
		System.out.println("  4  Show matches       - View likely lost/found matches");
		System.out.println("  0  Exit               - Close CampusFind safely");
		System.out.println();
	}

	private void addLost() {
		printSection("Report Lost Item");
		LostReport report = new LostReport(read("Your name"), read("Item name"), read("Description"), read("Location"), readDate());
		reports.addLostReport(report);
		printSuccess("Lost report saved successfully.");
		pause();
	}

	private void addFound() {
		printSection("Report Found Item");
		FoundReport report = new FoundReport(read("Your name"), read("Item name"), read("Description"), read("Location"), readDate());
		reports.addFoundReport(report);
		printSuccess("Found report saved successfully.");
		pause();
	}

	private void search() {
		printSection("Search Reports");
		String term = read("Search term").toLowerCase();
		List<LostReport> lostResults = reports.getLostReports().stream().filter(report -> matches(report, term)).toList();
		List<FoundReport> foundResults = reports.getFoundReports().stream().filter(report -> matches(report, term)).toList();
		printTable(lostResults, foundResults);
		if (lostResults.isEmpty() && foundResults.isEmpty()) printWarning("No reports matched your search.");
		pause();
	}

	private boolean matches(LostReport report, String term) {
		return matches(term, report.getItemName(), report.getDescription(), report.getLocation());
	}

	private boolean matches(FoundReport report, String term) {
		return matches(term, report.getItemName(), report.getDescription(), report.getLocation());
	}

	private boolean matches(String term, String itemName, String description, String location) {
		return itemName.toLowerCase().contains(term)
				|| description.toLowerCase().contains(term)
				|| location.toLowerCase().contains(term);
	}

	private void showMatches() {
		printSection("Possible Matches");
		List<MatchResult> matches = matcher.findMatches(reports.getLostReports(), reports.getFoundReports());
		if (matches.isEmpty()) {
			printWarning("No likely matches found yet.");
		} else {
			System.out.printf("  %-8s %-24s %-24s %s%n", "Score", "Lost item", "Found item", "Location");
			System.out.println(THIN_SEPARATOR);
			for (MatchResult match : matches) {
				LostReport lost = match.getLostReport();
				FoundReport found = match.getFoundReport();
				System.out.printf("  %-8s %-24s %-24s %s%n", match.getScore() + "%", truncate(lost.getItemName(), 24),
						truncate(found.getItemName(), 24), truncate(lost.getLocation(), 16));
			}
		}
		pause();
	}

	private void printTable(List<LostReport> lostReports, List<FoundReport> foundReports) {
		if (lostReports.isEmpty() && foundReports.isEmpty()) return;
		System.out.printf("  %-7s %-8s %-22s %-22s %-16s %s%n", "Type", "ID", "Item", "Description", "Location", "Date");
		System.out.println(THIN_SEPARATOR);
		for (LostReport report : lostReports) printRow("LOST", report.getId(), report.getItemName(), report.getDescription(), report.getLocation(), report.getDate().toString());
		for (FoundReport report : foundReports) printRow("FOUND", report.getId(), report.getItemName(), report.getDescription(), report.getLocation(), report.getDate().toString());
	}

	private void printRow(String type, long id, String item, String description, String location, String date) {
		System.out.printf("  %-7s %-8d %-22s %-22s %-16s %s%n", type, id, truncate(item, 22), truncate(description, 22), truncate(location, 16), date);
	}

	private void printSection(String title) {
		System.out.println(color(CYAN, "\n" + SEPARATOR));
		System.out.println(color(CYAN, "  " + title));
		System.out.println(color(CYAN, SEPARATOR));
	}

	private void printSuccess(String message) {
		System.out.println(color(GREEN, "[SUCCESS] " + message));
	}

	private void printWarning(String message) {
		System.out.println(color(YELLOW, "[INFO] " + message));
	}

	private void printError(String message) {
		System.out.println(color(RED, "[ERROR] " + message));
	}

	private void pause() {
		System.out.print("\nPress Enter to continue...");
		if (scanner.hasNextLine()) scanner.nextLine();
		System.out.println();
	}

	private String read(String label) {
		return InputValidator.required(readLine(label), label);
	}

	private java.time.LocalDate readDate() {
		String value = readLine("Date (yyyy-MM-dd, blank for today)");
		if (value == null) throw new IllegalArgumentException("Date is required");
		return value.isBlank() ? java.time.LocalDate.now() : DateUtil.parse(value);
	}

	private String readLine(String prompt) {
		System.out.print("  " + prompt + ": ");
		return scanner.hasNextLine() ? scanner.nextLine().trim() : null;
	}

	private String truncate(String value, int width) {
		if (value.length() <= width) return value;
		return value.substring(0, Math.max(0, width - 3)) + "...";
	}

	private String color(String color, String message) {
		return COLORS_ENABLED ? color + message + RESET : message;
	}

	private static boolean supportsAnsi() {
		String term = System.getenv("TERM");
		return System.console() != null && (term == null || !"dumb".equalsIgnoreCase(term));
	}
}
