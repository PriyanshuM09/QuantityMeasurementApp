package com.apps.quantitymeasurement;

import java.util.Objects;

public class Length {

    private final double value;
    private final LengthUnit unit;

    // Base unit = inches
    public enum LengthUnit {
    	
        FEET(12.0),          // 1 ft = 12 in
        INCHES(1.0),         // 1 in = 1 in
        YARDS(36.0),         // 1 yard = 36 in (3 ft)
        CENTIMETERS(0.393701); // 1 cm = 0.393701 in

        private final double toInchesFactor;

        LengthUnit(double toInchesFactor) {
            this.toInchesFactor = toInchesFactor;
        }

        public double toInches(double value) {
            return value * toInchesFactor;
        }
    }

    public Length(double value, LengthUnit unit) {
        if (unit == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }
        this.value = value;
        this.unit = unit;
    }

    private double toBaseInches() {
        return unit.toInches(value);
    }

    public boolean compare(Length that) {
        if (that == null) return false;
        return Double.compare(this.toBaseInches(), that.toBaseInches()) == 0;
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
        return Double.compare(this.toBaseInches(), that.toBaseInches()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(Double.valueOf(toBaseInches()));
    }

    @Override
    public String toString() {
        return "Quantity(" + value + ", " + unit + ")";
    }
}