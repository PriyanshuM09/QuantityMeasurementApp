package com.apps.quantitymeasurement;

import java.util.Objects;

public class Length {

    private final double value;
    private final LengthUnit unit;
    private static final double EPS = 1e-6;

    // Base unit = INCHES
    public enum LengthUnit {
        FEET(12.0),
        INCHES(1.0),
        YARDS(36.0),
        CENTIMETERS(0.393701);

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
        if (unit == null) throw new IllegalArgumentException("Unit cannot be null");
        if (!Double.isFinite(value)) throw new IllegalArgumentException("Value must be finite");
        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public LengthUnit getUnit() {
        return unit;
    }

    private double toBaseInches() {
        return unit.toInches(value);
    }

    // UC5: Convert instance to target unit
    public Length convertTo(LengthUnit targetUnit) {
        if (targetUnit == null) throw new IllegalArgumentException("Target unit cannot be null");
        double inches = toBaseInches();
        double converted = targetUnit.fromInches(inches);
        return new Length(converted, targetUnit);
    }

    // UC5: Static convert API
    public static double convert(double value, LengthUnit source, LengthUnit target) {
        if (source == null || target == null)
            throw new IllegalArgumentException("Source/Target unit cannot be null");
        if (!Double.isFinite(value))
            throw new IllegalArgumentException("Value must be finite");

        double inches = source.toInches(value);
        return target.fromInches(inches);
    }

    // UC6: Addition (result in first operand unit)
    public Length add(Length that) {
        if (that == null) throw new IllegalArgumentException("Length to add cannot be null");

        double thisInches = this.toBaseInches();
        double thatInches = that.toBaseInches();

        double sumInches = thisInches + thatInches;
        double resultValue = this.unit.fromInches(sumInches);

        return new Length(resultValue, this.unit);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Length that = (Length) o;
        return Math.abs(this.toBaseInches() - that.toBaseInches()) < EPS;
    }

    @Override
    public int hashCode() {
        return Objects.hash(Math.round(toBaseInches() / EPS));
    }

    @Override
    public String toString() {
        return String.format("Quantity(%.4f, %s)", value, unit);
    }
}