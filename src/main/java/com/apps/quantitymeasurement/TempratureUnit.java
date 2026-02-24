package com.apps.quantitymeasurement;

import java.util.function.Function;

enum TemperatureUnit implements IMeasurable {

    CELSIUS(
            c -> c,                       // Celsius to Celsius (base)
            c -> c                        // Celsius to Celsius
    ),
    FAHRENHEIT(
            f -> (f - 32) * 5 / 9,         // Fahrenheit to Celsius
            c -> (c * 9 / 5) + 32          // Celsius to Fahrenheit
    ),
    KELVIN(
            k -> k - 273.15,               // Kelvin to Celsius
            c -> c + 273.15                // Celsius to Kelvin
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
        return toCelsius.apply(value); // Base unit = Celsius
    }

    @Override
    public double convertFromBaseUnit(double baseValue) {
        return fromCelsius.apply(baseValue);
    }

    @Override
    public double getConversionFactor() {
        return 1.0; // Not meaningful for temperature (non-linear), dummy value
    }

    @Override
    public String getUnitName() {
        return name(); // CELSIUS, FAHRENHEIT, KELVIN
    }
}