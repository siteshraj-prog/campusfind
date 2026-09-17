package com.campusfind.repository;

import com.campusfind.model.LostReport;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LostReportRepository {
	private static final String INSERT_SQL = """
			INSERT INTO lost_reports (reporter, item_name, description, location, report_date)
			VALUES (?, ?, ?, ?, ?)""";
	private static final String FIND_ALL_SQL = "SELECT * FROM lost_reports ORDER BY id DESC";

	public LostReport save(LostReport report) {
		try (var connection = DatabaseManager.getConnection();
			 var statement = connection.prepareStatement(INSERT_SQL, java.sql.Statement.RETURN_GENERATED_KEYS)) {
			setReportValues(statement, report);
			statement.executeUpdate();
			try (var keys = statement.getGeneratedKeys()) {
				if (!keys.next()) throw new SQLException("The database did not return a report id");
				return new LostReport(keys.getLong(1), report.getReporter(), report.getItemName(), report.getDescription(), report.getLocation(), report.getDate());
			}
		} catch (SQLException exception) {
			throw new IllegalStateException("Could not save lost report", exception);
		}
	}

	public List<LostReport> findAll() {
		List<LostReport> reports = new ArrayList<>();
		try (var connection = DatabaseManager.getConnection();
			 var statement = connection.prepareStatement(FIND_ALL_SQL);
			 var rows = statement.executeQuery()) {
			while (rows.next()) reports.add(toReport(rows));
			return reports;
		} catch (SQLException exception) {
			throw new IllegalStateException("Could not read lost reports", exception);
		}
	}

	private void setReportValues(java.sql.PreparedStatement statement, LostReport report) throws SQLException {
		statement.setString(1, report.getReporter());
		statement.setString(2, report.getItemName());
		statement.setString(3, report.getDescription());
		statement.setString(4, report.getLocation());
		statement.setString(5, report.getDate().toString());
	}

	private LostReport toReport(java.sql.ResultSet row) throws SQLException {
		return new LostReport(row.getLong("id"), row.getString("reporter"), row.getString("item_name"), row.getString("description"), row.getString("location"), LocalDate.parse(row.getString("report_date")));
	}
}
