package com.campusfind.repository;

import com.campusfind.model.FoundReport;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FoundReportRepository {
	private static final String INSERT_SQL = """
			INSERT INTO found_reports (reporter, item_name, description, location, report_date)
			VALUES (?, ?, ?, ?, ?)""";
	private static final String FIND_ALL_SQL = "SELECT * FROM found_reports ORDER BY id DESC";

	public FoundReport save(FoundReport report) {
		try (var connection = DatabaseManager.getConnection();
			 var statement = connection.prepareStatement(INSERT_SQL, java.sql.Statement.RETURN_GENERATED_KEYS)) {
			setReportValues(statement, report);
			statement.executeUpdate();
			try (var keys = statement.getGeneratedKeys()) {
				if (!keys.next()) throw new SQLException("The database did not return a report id");
				return new FoundReport(keys.getLong(1), report.getReporter(), report.getItemName(), report.getDescription(), report.getLocation(), report.getDate());
			}
		} catch (SQLException exception) {
			throw new IllegalStateException("Could not save found report", exception);
		}
	}

	public List<FoundReport> findAll() {
		List<FoundReport> reports = new ArrayList<>();
		try (var connection = DatabaseManager.getConnection();
			 var statement = connection.prepareStatement(FIND_ALL_SQL);
			 var rows = statement.executeQuery()) {
			while (rows.next()) reports.add(toReport(rows));
			return reports;
		} catch (SQLException exception) {
			throw new IllegalStateException("Could not read found reports", exception);
		}
	}

	private void setReportValues(java.sql.PreparedStatement statement, FoundReport report) throws SQLException {
		statement.setString(1, report.getReporter());
		statement.setString(2, report.getItemName());
		statement.setString(3, report.getDescription());
		statement.setString(4, report.getLocation());
		statement.setString(5, report.getDate().toString());
	}

	private FoundReport toReport(java.sql.ResultSet row) throws SQLException {
		return new FoundReport(row.getLong("id"), row.getString("reporter"), row.getString("item_name"), row.getString("description"), row.getString("location"), LocalDate.parse(row.getString("report_date")));
	}
}
