package com.apps.quantitymeasurement.core;

import java.util.Objects;

public class Quantity<U extends IMeasurable> {

    private final double value;
    private final U unit;

    public Quantity(double value, U unit) {

        if (unit == null)
            throw new IllegalArgumentException("Unit cannot be null");

        if (!Double.isFinite(value))
            throw new IllegalArgumentException("Invalid value");

        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public U getUnit() {
        return unit;
    }

    private double toBaseUnit() {
        return unit.convertToBaseUnit(value);
    }

    public Quantity<U> convertTo(U targetUnit) {

        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit must not be null");
        }

        if (!unit.getClass().equals(targetUnit.getClass())) {
            throw new IllegalArgumentException("Cannot convert between different measurement categories");
        }

        double base = unit.convertToBaseUnit(value);
        double converted = targetUnit.convertFromBaseUnit(base);

        return new Quantity<>(converted, targetUnit);
    }

    public Quantity<U> add(Quantity<U> other) {
        return add(other, this.unit);
    }

    public Quantity<U> add(Quantity<U> other, U targetUnit) {

        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit must not be null");
        }

        validate(other);

        double result = perform(other, ArithmeticOperation.ADD);
        double converted = targetUnit.convertFromBaseUnit(result);

        return new Quantity<>(round(converted), targetUnit);
    }

    public Quantity<U> subtract(Quantity<U> other) {
        return subtract(other, this.unit);
    }

    public Quantity<U> subtract(Quantity<U> other, U targetUnit) {

        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit must not be null");
        }

        validate(other);

        double result = perform(other, ArithmeticOperation.SUBTRACT);
        double converted = targetUnit.convertFromBaseUnit(result);

        return new Quantity<>(round(converted), targetUnit);
    }

    public double divide(Quantity<U> other) {

        validate(other);

        return perform(other, ArithmeticOperation.DIVIDE);
    }

    private void validate(Quantity<U> other) {

        if (other == null)
            throw new IllegalArgumentException("Other quantity cannot be null");

        if (!unit.getClass().equals(other.unit.getClass()))
            throw new IllegalArgumentException("Different measurement types");
    }

    private double perform(Quantity<U> other, ArithmeticOperation op) {

        unit.validateOperationSupport(op.name());
        other.unit.validateOperationSupport(op.name());

        double a = unit.convertToBaseUnit(value);
        double b = other.unit.convertToBaseUnit(other.value);

        return op.compute(a, b);
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;

        if (!(obj instanceof Quantity))
            return false;

        Quantity<?> other = (Quantity<?>) obj;

        return Math.abs(this.toBaseUnit() - other.toBaseUnit()) < 0.01;
    }

    @Override
    public int hashCode() {
        return Objects.hash(Math.round(toBaseUnit() * 100.0) / 100.0);
    }

    @Override
    public String toString() {
        return "Quantity(" + value + ", " + unit.getUnitName() + ")";
    }
}