package com.apps.quantitymeasurement.core;

import java.util.function.Function;

public enum TemperatureUnit implements IMeasurable {

    CELSIUS(
            c -> c,
            c -> c
    ),
    FAHRENHEIT(
            f -> (f - 32) * 5 / 9,
            c -> (c * 9 / 5) + 32
    ),
    KELVIN(
            k -> k - 273.15,
            c -> c + 273.15
    );

    private final Function<Double, Double> toCelsius;
    private final Function<Double, Double> fromCelsius;

    TemperatureUnit(Function<Double, Double> toCelsius,
                    Function<Double, Double> fromCelsius) {

        this.toCelsius = toCelsius;
        this.fromCelsius = fromCelsius;
    }

    @Override
    public double convertToBaseUnit(double value) {
        return toCelsius.apply(value);
    }

    @Override
    public double convertFromBaseUnit(double baseValue) {
        return fromCelsius.apply(baseValue);
    }

    @Override
    public double getConversionFactor() {
        return 1.0;
    }

    @Override
    public String getUnitName() {
        return name();
    }

    @Override
    public SupportsArithmetic supportsArithmetic() {
        return () -> false;
    }

    @Override
    public void validateOperationSupport(String operation) {
        throw new UnsupportedOperationException(
                "Temperature does not support arithmetic operations");
    }
}