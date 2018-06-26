package com.bitstd.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ThreadLocalConnection {
	private static ThreadLocal<Connection> connectionHolder = new ThreadLocal<Connection>();
	private static String jdbcurl = "jdbc:oracle:thin:@192.168.3.77:1521:ora11g";
	private static String username = "daren";
	private static String password = "daren";

	public static Connection getConnection() {

		Connection connection = connectionHolder.get();

		if (connection == null) {
			try {
				connection = DriverManager.getConnection(jdbcurl, username, password);
				connectionHolder.set(connection);

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return connection;
	}

	/**
	 * close Connection
	 */
	public static void closeConnection() {
		Connection connection = connectionHolder.get();

		if (connection != null) {
			try {
				connection.close();
				connectionHolder.remove();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * close Statement
	 */
	public static void closeStatement(Statement statement) {

		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * close ResultSet
	 */
	public static void closeResultSet(ResultSet resultSet) {

		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * manualCommitTransaction
	 */
	public static void manualCommitTransaction() {
		Connection connection = getConnection();

		if (connection != null) {
			try {
				if (connection.getAutoCommit()) {
					connection.setAutoCommit(false);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * commitTransaction
	 */
	public static void commitTransaction() {
		Connection connection = connectionHolder.get();

		if (connection != null) {
			try {
				if (!connection.getAutoCommit()) {
					connection.commit();
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * rollbackTransaction
	 */
	public static void rollbackTransaction() {
		Connection connection = connectionHolder.get();

		if (connection != null) {
			try {
				if (!connection.getAutoCommit()) {
					connection.rollback();
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * resetConnection
	 */
	public static void resetConnection() {
		Connection connection = connectionHolder.get();
		if (connection != null) {
			try {
				if (connection.getAutoCommit()) {
					connection.setAutoCommit(false);

				} else {
					connection.setAutoCommit(true);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
