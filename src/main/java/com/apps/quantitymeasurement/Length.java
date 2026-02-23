package com.apps.quantitymeasurement;

import java.util.Objects;

public class Length {

    private final double value;
    private final LengthUnit unit;

    // Base unit = INCHES
    public enum LengthUnit {
        FEET(12.0),             // 1 ft = 12 in
        INCHES(1.0),            // base
        YARDS(36.0),            // 1 yard = 36 in
        CENTIMETERS(0.393701); // 1 cm = 0.393701 in

        private final double toInchesFactor;

        LengthUnit(double toInchesFactor) {
            this.toInchesFactor = toInchesFactor;
        }

        public double toInches(double value) {
            return value * toInchesFactor;
        }

        public double fromInches(double inches) {
            return inches / toInchesFactor;
        }
    }

    public Length(double value, LengthUnit unit) {
        if (unit == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Value must be finite");
        }
        this.value = value;
        this.unit = unit;
    }

    private double toBaseInches() {
        return unit.toInches(value);
    }

    public Length convertTo(LengthUnit targetUnit) {
        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null");
        }
        double inches = this.toBaseInches();
        double converted = targetUnit.fromInches(inches);
        return new Length(converted, targetUnit);
    }

    public static double convert(double value, LengthUnit source, LengthUnit target) {
        if (source == null || target == null) {
            throw new IllegalArgumentException("Source/Target unit cannot be null");
        }
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Value must be finite");
        }
        double inches = source.toInches(value);
        return target.fromInches(inches);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Length that = (Length) o;
        return Double.compare(this.toBaseInches(), that.toBaseInches()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(Double.valueOf(toBaseInches()));
    }

    @Override
    public String toString() {
        return String.format("%.2f %s", value, unit);
    }
}