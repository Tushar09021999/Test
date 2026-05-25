package com.luluoutapi.Dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class DBPool {

	private static DataSource datasource = null;
	static ResourceBundle cmnBundle = ResourceBundle.getBundle("api-config");

	public static Connection getConnection() throws SQLException {
		Connection connection = null;

		try {
			InitialContext context = new InitialContext();
			datasource = (DataSource) context.lookup(cmnBundle.getString("DBJNDI"));
			if (datasource != null) {
				connection = datasource.getConnection();
			}

		} catch (SQLException se) {
			log.error("[getConnection] [SQLException] " + se);
			throw new SQLException("DB Connection is problem!");
		} catch (Exception e) {
			log.error("[getConnection] [Exception] " + e);
		}
		return connection;
	}

	public static void closeCallable(CallableStatement callableStatement) {
		try {
			if (callableStatement != null) {
				callableStatement.close();

			}
		} catch (Exception e) {
			e.getMessage();
		}
	}

	public static void closePrepareStatement(PreparedStatement ps) {
		try {
			if (ps != null) {
				ps.close();

			}
		} catch (Exception e) {
			e.getMessage();
		}

	}
}