package com.app.exception;

public class QuantityMeasurementException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public QuantityMeasurementException(String str) {
		super(str);
	}
	
	public QuantityMeasurementException(String str,Throwable cause) {
		super(str,cause);
	}
}
