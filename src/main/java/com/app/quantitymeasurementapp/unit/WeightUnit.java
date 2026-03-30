package com.app.quantitymeasurementapp.unit;

public enum WeightUnit implements IMeasurable{
	KILOGRAM(1.0),
    // 1 Gram = 0.001 Kilograms
    GRAM(0.001),
    // 1 Pound = 0.453592 Kilograms
    POUND(0.453592);
	
	private double conversion;
	
	WeightUnit(double conversion){
		this.conversion = conversion;
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
	public double getConversionFactor() {
		return conversion;
	}
	@Override
	public String getUnitName() {
		return WeightUnit.this.toString();
	}
	
	@Override
	public String getMeasurableType() {
		return this.getClass().getSimpleName();
	}
}