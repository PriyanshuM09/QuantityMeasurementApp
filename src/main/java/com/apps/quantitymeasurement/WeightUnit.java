package com.apps.quantitymeasurement;
public enum WeightUnit {

    MILLIGRAM(0.001),
    GRAM(1.0),
    KILOGRAM(1000.0),
    POUND(453.592),
    TONNE(1_000_000.0);

    private final double conversionFactor;

    WeightUnit(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    public double getConversionFactor() {
        return conversionFactor;
    }

    // Convert this unit → base unit (grams)
    public double convertToBaseUnit(double value) {
        if (!Double.isFinite(value))
            throw new IllegalArgumentException("Invalid value");
        return Math.round(value * conversionFactor * 100.0) / 100.0;
    }

    // Convert base unit (grams) → this unit
    public double convertFromBaseUnit(double baseValue) {
        if (!Double.isFinite(baseValue))
            throw new IllegalArgumentException("Invalid value");
        return Math.round((baseValue / conversionFactor) * 100.0) / 100.0;
    }
}