package com.app.exception;

public class DatabaseException extends QuantityMeasurementException{

	public DatabaseException(String str) {
		super(str);
	}
	
    public DatabaseException(String str,Throwable cause) {
	   super(str,cause);
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
   	public static void main(String[] args) {
   		try {
   			throw connectionFailed( "Unable to connect to the database",new RuntimeException( "Connection timeout") );
   			} catch (DatabaseException ex) {
   			System.out.println("Caught DatabaseException: " + ex.getMessage());
   			ex.printStackTrace();

   			}
	}
}