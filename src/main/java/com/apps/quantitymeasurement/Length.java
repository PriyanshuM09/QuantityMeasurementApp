package com.apps.quantitymeasurement;

import java.util.Objects;

public class Length {

    private final double value;
    private final LengthUnit unit;

    // Enum for supported length units (base unit = inches)
    public enum LengthUnit {
        FEET(12.0),
        INCHES(1.0);

        private final double conversionFactorToInches;

        LengthUnit(double conversionFactorToInches) {
            this.conversionFactorToInches = conversionFactorToInches;
        }

        public double getConversionFactorToInches() {
            return conversionFactorToInches;
        }
    }

    // Constructor
    public Length(double value, LengthUnit unit) {
    	
        if (unit == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }
        
        this.value = value;
        this.unit = unit;
    }

    // Convert any length to base unit (inches)
    private double toInches() {
        return value * unit.getConversionFactorToInches();
    }

    // Compare two Length objects by converting to base unit
    public boolean compare(Length that) {
    	
        if (that == null) {
        	return false;
        }
        
        return Double.compare(this.toInches(), that.toInches()) == 0;
    }

    @Override
    public boolean equals(Object o) {
    	
        if (this == o) {
        	return true;
        }
        
        if (o == null || getClass() != o.getClass()) {
        	return false;
        }
        
        Length that = (Length) o;
        return Double.compare(this.toInches(), that.toInches()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(Double.valueOf(toInches()));
    }
}