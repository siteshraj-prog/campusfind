package com.campusfind;

import com.campusfind.model.FoundReport;
import com.campusfind.model.LostReport;
import com.campusfind.repository.DatabaseManager;
import com.campusfind.service.MatchService;
import com.campusfind.service.ReportService;
import com.campusfind.util.DateUtil;
import java.sql.Connection;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CampusFindVerificationTest {
	@Test
	void databaseInitializesBothReportTables() throws Exception {
		try (Connection connection = DatabaseManager.getConnection()) {
			assertTrue(hasTable(connection, "lost_reports"));
			assertTrue(hasTable(connection, "found_reports"));
		}
	}

	@Test
	void lostAndFoundReportsPersistAndCanBeReadBack() {
		ReportService reports = new ReportService();
		String suffix = UUID.randomUUID().toString();
		LocalDate date = LocalDate.of(2026, 9, 17);
		LostReport lost = reports.addLostReport(new LostReport("Lost " + suffix, "Wallet " + suffix, "Blue leather", "Library", date));
		FoundReport found = reports.addFoundReport(new FoundReport("Found " + suffix, "Keys " + suffix, "Silver keys", "Gym", date));

		assertTrue(lost.getId() > 0);
		assertTrue(found.getId() > 0);
		assertTrue(reports.getLostReports().stream().anyMatch(report -> report.getId() == lost.getId()));
		assertTrue(reports.getFoundReports().stream().anyMatch(report -> report.getId() == found.getId()));
	}

	@Test
	void invalidReportsAreRejectedBeforePersistence() {
		ReportService reports = new ReportService();
		LostReport invalid = new LostReport("", "Phone", "Black", "Library", LocalDate.now());

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> reports.addLostReport(invalid));
		assertEquals("Your name is required", exception.getMessage());
		assertThrows(IllegalArgumentException.class, () -> reports.addFoundReport(null));
	}

	@Test
	void dateParsingRejectsMissingValuesAndAcceptsIsoDates() {
		assertEquals(LocalDate.of(2026, 9, 17), DateUtil.parse("2026-09-17"));
		assertThrows(IllegalArgumentException.class, () -> DateUtil.parse(null));
		assertThrows(IllegalArgumentException.class, () -> DateUtil.parse("not-a-date"));
	}

	@Test
	void emptyMatchSearchReturnsNoResults() {
		assertNotNull(new MatchService().findMatches(List.of(), List.of()));
		assertFalse(new MatchService().findMatches(List.of(), List.of()).iterator().hasNext());
	}

	private boolean hasTable(Connection connection, String tableName) throws Exception {
		try (ResultSet tables = connection.getMetaData().getTables(null, null, tableName, new String[] { "TABLE" })) {
			return tables.next();
		}
	}
}
