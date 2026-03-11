package com.apps.quantitymeasurement.core;

public interface IMeasurable {

    double getConversionFactor();

    double convertToBaseUnit(double value);

    double convertFromBaseUnit(double baseValue);

    String getUnitName();


    /**
     * Default arithmetic support
     */
    default SupportsArithmetic supportsArithmetic() {
        return () -> true;
    }


    /**
     * Validate if arithmetic operation is allowed
     */
    default void validateOperationSupport(String operation) {
        if (!supportsArithmetic().isSupported()) {
            throw new UnsupportedOperationException(
                    "Operation not supported for unit: " + getUnitName());
        }
    }


    /**
     * Measurement type
     */
    default String getMeasurementType() {
        return this.getClass().getSimpleName();
    }
}