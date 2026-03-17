package com.app.model;

import com.app.unit.IMeasurable;

public class QuantityModel<U extends IMeasurable> {
    private double value;
    private U unit;
    
    public QuantityModel(double value,U unit) {
    	this.value = value;
    	this.unit = unit;
    }

	public double getValue() {
		return value;
	}

	public U getUnit() {
		return unit;
	}

	@Override
	public String toString() {
		return "QuantityModel [value=" + value + ", unit=" + unit + "]";
	}
}