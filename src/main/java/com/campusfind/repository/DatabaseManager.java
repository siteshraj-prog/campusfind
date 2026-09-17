package com.campusfind.repository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseManager {
	private static final String URL = "jdbc:sqlite:data/campusfind.db";
	private static final String CREATE_LOST_REPORTS = """
			CREATE TABLE IF NOT EXISTS lost_reports (
				id INTEGER PRIMARY KEY AUTOINCREMENT,
				reporter TEXT NOT NULL,
				item_name TEXT NOT NULL,
				description TEXT NOT NULL,
				location TEXT NOT NULL,
				report_date TEXT NOT NULL
			)""";
	private static final String CREATE_FOUND_REPORTS = """
			CREATE TABLE IF NOT EXISTS found_reports (
				id INTEGER PRIMARY KEY AUTOINCREMENT,
				reporter TEXT NOT NULL,
				item_name TEXT NOT NULL,
				description TEXT NOT NULL,
				location TEXT NOT NULL,
				report_date TEXT NOT NULL
			)""";

	private DatabaseManager() { }

	public static Connection getConnection() {
		Connection connection = null;
		try {
			Files.createDirectories(Path.of("data"));
			connection = DriverManager.getConnection(URL);
			initialize(connection);
			return connection;
		} catch (Exception exception) {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException closeException) {
					exception.addSuppressed(closeException);
				}
			}
			throw new IllegalStateException("Could not initialize database", exception);
		}
	}

	private static void initialize(Connection connection) throws SQLException {
		try (var statement = connection.createStatement()) {
			statement.executeUpdate(CREATE_LOST_REPORTS);
			statement.executeUpdate(CREATE_FOUND_REPORTS);
		}
	}
}
