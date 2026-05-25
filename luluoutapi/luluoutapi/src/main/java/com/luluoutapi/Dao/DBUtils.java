package com.luluoutapi.Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class DBUtils {
	static ResourceBundle cmnBundle = ResourceBundle.getBundle("sql");

	public static Connection getConnection() {

		Connection connection = null;

		try {
			log.debug("in try block of DBUtils conection");
			Class.forName("oracle.jdbc.OracleDriver");
			log.debug("Class.forNameoracle.jdbc.OracleDriver");
			connection = DriverManager.getConnection(cmnBundle.getString("URL"), cmnBundle.getString("USERNAME"),
					cmnBundle.getString("PASSWORD"));

			log.debug("take a connection DBUtils conection");

		} catch (SQLException se) {
			log.error("DB Connection is problem! Exception occurred while getting connection " + se);
		} catch (Exception e) {
			log.error("DB Connection is problem! Exception occurred while getting connection " + e);
		}
		return connection;
	}

	public static void closeConnection(Connection con) {
		try {
			if (con != null) {
				con.close();

			}
		} catch (SQLException e) {
			log.error("[closeConnection] DB Connecton Closed Exception " + e.getMessage() + " actual Error. " + e);

		}
	}

	public static void closeStatement(Statement st) {
		try {
			if (st != null) {
				st.close();

			}
		} catch (SQLException e) {
			log.error("[closeStatement] DB Statement Closed Exception " + e.getMessage() + " actual Error. " + e);

		}
	}

	public static void closeResultset(ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();

			}
		} catch (SQLException e) {
			log.error("[closeResultset] Result Set closed Exception " + e.getMessage() + " actualError. " + e);

		}
	}

	public static void closePrepareStatement(PreparedStatement ps) {
		try {
			if (ps != null) {
				ps.close();

			}
		} catch (Exception e) {
			log.error("[closePrepareStatement] DB prepare Statement Closed Exception " + e.getMessage()
					+ " actual Error " + e);

		}
	}

	public static void rollback(Connection con) {
		if (con != null) {
			try {
				con.rollback();
				log.debug("[rollback] Rollback Successful");
			} catch (Exception e) {
				log.error("[closePrepareStatement] DB prepare Statement Rollback Exception " + e.getMessage()
						+ " actual Error " + e);

			}
		}
	}

	public static void main(String[] args) {
		System.out.println("con:" + getConnection());
	}
}