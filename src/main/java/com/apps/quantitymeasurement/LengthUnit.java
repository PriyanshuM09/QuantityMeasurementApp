package com.apps.quantitymeasurement;
public enum LengthUnit {

    FEET(1.0),
    INCHES(1.0 / 12.0),
    YARDS(3.0),
    CENTIMETERS(1.0 / 30.48);

    private final double conversionFactor;

    LengthUnit(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    public double getConversionFactor() {
        return conversionFactor;
    }

    public double convertToBaseUnit(double value) {
        if (!Double.isFinite(value))
            throw new IllegalArgumentException("Invalid value");
        return Math.round(value * conversionFactor * 100.0) / 100.0;
    }

    public double convertFromBaseUnit(double baseValue) {
        if (!Double.isFinite(baseValue))
            throw new IllegalArgumentException("Invalid value");
        return Math.round((baseValue / conversionFactor) * 100.0) / 100.0;
    }
}