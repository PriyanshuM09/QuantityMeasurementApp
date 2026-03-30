package com.app.quantitymeasurementapp.unit;

public enum LengthUnit implements IMeasurable{
	FEET(1.0),
	INCHES(1.0/12.0),
	YARD(3.0),
	CENTIMETERS(0.0328084);
	
	private double conversion;
	
	LengthUnit(double conversion){
		this.conversion = conversion;
	}
	
	@Override
	public double getConversionFactor() {
		return conversion;
	}

	@Override
	public double convertToBaseUnit(double value) {
		return value*this.getConversionFactor();
	}

	@Override
	public double convertFromBaseUnit(double baseValue) {
		double result = baseValue / this.getConversionFactor();
	    // Rounding to 2 decimal places:
	    return Math.round(result * 100.0) / 100.0;
	}

	@Override
	public String getUnitName() {
		return this.name();
	}
	
	@Override
	public String getMeasurableType() {
		return this.getClass().getSimpleName();
	}
}