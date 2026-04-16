package com.placement.measurementservice.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Service to handle quantity measurements and conversions.
 * Supports Length, Weight, Temperature, and Volume.
 */
@Service
public class MeasurementService {

    // Conversion factors relative to a base unit for each type
    private static final Map<String, Double> LENGTH_CONVERSIONS = new HashMap<>();
    private static final Map<String, Double> WEIGHT_CONVERSIONS = new HashMap<>();
    private static final Map<String, Double> VOLUME_CONVERSIONS = new HashMap<>();

    static {
        // Length (Base: Millimeter)
        LENGTH_CONVERSIONS.put("Millimeter", 1.0);
        LENGTH_CONVERSIONS.put("Centimeter", 10.0);
        LENGTH_CONVERSIONS.put("Meter", 1000.0);
        LENGTH_CONVERSIONS.put("Kilometer", 1000000.0);
        LENGTH_CONVERSIONS.put("Mile", 1609344.0);
        LENGTH_CONVERSIONS.put("Yard", 914.4);
        LENGTH_CONVERSIONS.put("Foot", 304.8);
        LENGTH_CONVERSIONS.put("Inch", 25.4);

        // Weight (Base: Milligram)
        WEIGHT_CONVERSIONS.put("Milligram", 1.0);
        WEIGHT_CONVERSIONS.put("Gram", 1000.0);
        WEIGHT_CONVERSIONS.put("Kilogram", 1000000.0);
        WEIGHT_CONVERSIONS.put("Pound", 453592.37);
        WEIGHT_CONVERSIONS.put("Ounce", 28349.52);
        WEIGHT_CONVERSIONS.put("Ton", 1000000000.0);

        // Volume (Base: Millilitre)
        VOLUME_CONVERSIONS.put("Milliliter", 1.0);
        VOLUME_CONVERSIONS.put("Liter", 1000.0);
        VOLUME_CONVERSIONS.put("Gallon", 3785.41);
        VOLUME_CONVERSIONS.put("Pint", 473.176);
        VOLUME_CONVERSIONS.put("Cup", 236.588);
    }

    /**
     * Converts a value from one unit to another within the same measurement type.
     */
    public double convert(String type, String fromUnit, String toUnit, double value) {
        String normalizedType = normalizeType(type);
        String normalizedFromUnit = normalizeUnit(fromUnit);
        String normalizedToUnit = normalizeUnit(toUnit);

        if (normalizedType.equals("temperature")) {
            return convertTemperature(normalizedFromUnit, normalizedToUnit, value);
        }

        Map<String, Double> factors;
        switch (normalizedType) {
            case "length": factors = LENGTH_CONVERSIONS; break;
            case "weight": factors = WEIGHT_CONVERSIONS; break;
            case "volume": factors = VOLUME_CONVERSIONS; break;
            default: throw new IllegalArgumentException("Unknown measurement type: " + type);
        }

        Double fromFactor = factors.get(normalizedFromUnit);
        Double toFactor = factors.get(normalizedToUnit);

        if (fromFactor == null || toFactor == null) {
            throw new IllegalArgumentException("Unknown unit for type " + type);
        }

        // Formula: (value * fromFactor) / toFactor
        return (value * fromFactor) / toFactor;
    }

    /**
     * Handles temperature conversions between Celsius, Fahrenheit, and Kelvin.
     */
    private double convertTemperature(String fromUnit, String toUnit, double value) {
        double celsius;

        // Step 1: Convert to Celsius first
        switch (fromUnit) {
            case "Celsius": celsius = value; break;
            case "Fahrenheit": celsius = (value - 32) * 5 / 9; break;
            case "Kelvin": celsius = value - 273.15; break;
            default: throw new IllegalArgumentException("Unknown temperature unit: " + fromUnit);
        }

        // Step 2: Convert from Celsius to target unit
        switch (toUnit) {
            case "Celsius": return celsius;
            case "Fahrenheit": return (celsius * 9 / 5) + 32;
            case "Kelvin": return celsius + 273.15;
            default: throw new IllegalArgumentException("Unknown temperature unit: " + toUnit);
        }
    }

    /**
     * Performs arithmetic operations between two values of different units.
     * Both values are converted to a base unit, the operation is performed,
     * and the result is converted to the target unit.
     */
    public double calculate(String type, String op, double val1, String unit1, double val2, String unit2, String targetUnit) {
        String normalizedType = normalizeType(type);
        double val1InBase;
        double val2InBase;

        if (normalizedType.equals("temperature")) {
            val1InBase = convertTemperature(normalizeUnit(unit1), "Celsius", val1);
            val2InBase = convertTemperature(normalizeUnit(unit2), "Celsius", val2);
        } else {
            val1InBase = convert(normalizedType, unit1, getBaseUnit(normalizedType), val1);
            // In multiplication/division, val2 is usually a scalar, but for add/sub it has a unit
            if (op.equalsIgnoreCase("add") || op.equalsIgnoreCase("subtract")) {
                val2InBase = convert(normalizedType, unit2, getBaseUnit(normalizedType), val2);
            } else {
                val2InBase = val2;
            }
        }

        double resultInBase;
        switch (op.toLowerCase()) {
            case "add": resultInBase = val1InBase + val2InBase; break;
            case "subtract": resultInBase = val1InBase - val2InBase; break;
            case "multiply": resultInBase = val1InBase * val2InBase; break;
            case "divide": 
                if (val2InBase == 0) throw new ArithmeticException("Division by zero");
                resultInBase = val1InBase / val2InBase; 
                break;
            default: throw new IllegalArgumentException("Unknown operation: " + op);
        }

        if (normalizedType.equals("temperature")) {
            return convertTemperature("Celsius", normalizeUnit(targetUnit), resultInBase);
        } else {
            return convert(normalizedType, getBaseUnit(normalizedType), targetUnit, resultInBase);
        }
    }

    /**
     * Compares two quantities of the same type but potentially different units.
     */
    public boolean compare(String type, double val1, String unit1, double val2, String unit2) {
        double result1 = convert(type, unit1, getBaseUnit(type), val1);
        double result2 = convert(type, unit2, getBaseUnit(type), val2);
        // Use a small epsilon for double comparison
        return Math.abs(result1 - result2) < 0.0001;
    }

    private String getBaseUnit(String type) {
        return switch (type.toLowerCase()) {
            case "length" -> "Millimeter";
            case "weight" -> "Milligram";
            case "volume" -> "Milliliter";
            case "temperature" -> "Celsius";
            default -> throw new IllegalArgumentException("Unknown type: " + type);
        };
    }

    private String normalizeType(String type) {
        if (type == null || type.isBlank()) {
            throw new IllegalArgumentException("Measurement type is required");
        }

        String normalized = type.trim().toLowerCase();
        return switch (normalized) {
            case "temp" -> "temperature";
            default -> normalized;
        };
    }

    private String normalizeUnit(String unit) {
        if (unit == null || unit.isBlank()) {
            throw new IllegalArgumentException("Unit is required");
        }

        String normalized = unit.trim().toLowerCase();
        return switch (normalized) {
            case "millimeter", "millimeters" -> "Millimeter";
            case "centimeter", "centimeters" -> "Centimeter";
            case "meter", "meters", "metre", "metres" -> "Meter";
            case "kilometer", "kilometers", "kilometre", "kilometres" -> "Kilometer";
            case "mile", "miles" -> "Mile";
            case "yard", "yards" -> "Yard";
            case "foot", "feet" -> "Foot";
            case "inch", "inches" -> "Inch";
            case "milligram", "milligrams" -> "Milligram";
            case "gram", "grams" -> "Gram";
            case "kilogram", "kilograms" -> "Kilogram";
            case "pound", "pounds", "lb", "lbs" -> "Pound";
            case "ounce", "ounces", "oz" -> "Ounce";
            case "ton", "tons", "tonne", "tonnes" -> "Ton";
            case "milliliter", "milliliters", "millilitre", "millilitres", "ml" -> "Milliliter";
            case "liter", "liters", "litre", "litres", "l" -> "Liter";
            case "gallon", "gallons" -> "Gallon";
            case "pint", "pints" -> "Pint";
            case "cup", "cups" -> "Cup";
            case "celsius", "celcius", "c" -> "Celsius";
            case "fahrenheit", "f" -> "Fahrenheit";
            case "kelvin", "k" -> "Kelvin";
            default -> Character.toUpperCase(normalized.charAt(0)) + normalized.substring(1);
        };
    }
}
