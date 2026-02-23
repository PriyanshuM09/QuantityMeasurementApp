package com.apps.quantitymeasurement;

import java.util.Objects;

public class Quantity<U extends IMeasurable> {

    private final double value; // value of quantity
    private final U unit;       // unit type

    public Quantity(double value, U unit) {
        if (unit == null)
            throw new IllegalArgumentException("Unit cannot be null"); // validation
        if (Double.isNaN(value))
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
        return unit.convertToBaseUnit(value); // convert to base
    }

    public Quantity<U> convertTo(U targetUnit) {
        if (targetUnit == null)
            throw new IllegalArgumentException("Target unit cannot be null");

        double baseValue = toBaseUnit(); // step1 base
        double convertedValue = targetUnit.convertFromBaseUnit(baseValue); // step2 target

        return new Quantity<>(convertedValue, targetUnit);
    }

    public Quantity<U> add(Quantity<U> other) {
        if (other == null)
            throw new IllegalArgumentException("Cannot add null quantity");

        double sumBase = this.toBaseUnit() + other.toBaseUnit(); // base addition
        double resultValue = unit.convertFromBaseUnit(sumBase);  // back to this unit

        return new Quantity<>(resultValue, unit);
    }

    public Quantity<U> add(Quantity<U> other, U targetUnit) {
        if (other == null || targetUnit == null)
            throw new IllegalArgumentException("Invalid arguments");

        double sumBase = this.toBaseUnit() + other.toBaseUnit(); // base addition
        double resultValue = targetUnit.convertFromBaseUnit(sumBase); // convert target

        return new Quantity<>(resultValue, targetUnit);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // same ref
        if (obj == null || getClass() != obj.getClass()) return false;

        Quantity<?> other = (Quantity<?>) obj;
        return Math.abs(this.toBaseUnit() - other.toBaseUnit()) < 0.01; // compare base
    }

    @Override
    public int hashCode() {
        return Objects.hash(Math.round(toBaseUnit() * 100.0) / 100.0);
    }
}