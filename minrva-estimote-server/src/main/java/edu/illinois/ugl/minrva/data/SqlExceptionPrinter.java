package edu.illinois.ugl.minrva.data;

import java.sql.SQLException;

public class SqlExceptionPrinter {
	static void printSQLException(SQLException ex) {
	    ex.printStackTrace(System.err);
	    System.err.println("SQLState: " + ex.getSQLState());
	    System.err.println("Error Code: " + ex.getErrorCode());
	    System.err.println("Message: " + ex.getMessage());
	    System.err.println("Cause: " + ex.getCause());
	}
}
