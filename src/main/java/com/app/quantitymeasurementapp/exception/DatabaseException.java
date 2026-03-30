package com.app.quantitymeasurementapp.exception;

public class DatabaseException extends QuantityMeasurementException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DatabaseException(String msg) {
		super(msg);
	}
	
	public DatabaseException(String message, Throwable cause) {
		super(message, cause);
	}

	public static DatabaseException connectionFailed(String details, Throwable cause) {
		return new DatabaseException("Database connection failed: " + details, cause);

	}

	public static DatabaseException queryFailed(String query, Throwable cause) {
		return new DatabaseException("Query execution failed: " + query, cause);

	}

	public static DatabaseException transactionFailed(String operation, Throwable cause) {
		return new DatabaseException("Transaction failed during " + operation, cause);
	}
}