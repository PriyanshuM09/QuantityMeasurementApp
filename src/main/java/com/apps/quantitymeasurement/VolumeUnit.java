package com.apps.quantitymeasurement;


public enum VolumeUnit implements IMeasurable {
	
    LITRE(1.0, "Litre"),
    MILLILITRE(0.001, "Millilitre"),
    GALLON(3.78541, "Gallon");

    private final double conversionFactorToBase; // base = litre
    private final String unitName;

    VolumeUnit(double conversionFactorToBase, String unitName) {
        this.conversionFactorToBase = conversionFactorToBase;
        this.unitName = unitName;
    }

    @Override
    public double getConversionFactor() {
        return conversionFactorToBase;
    }

    @Override
    public double convertToBaseUnit(double value) {
        return value * conversionFactorToBase;
    }

    @Override
    public double convertFromBaseUnit(double baseValue) {
        return baseValue / conversionFactorToBase;
    }

    @Override
    public String getUnitName() {
        return unitName;
    }
}