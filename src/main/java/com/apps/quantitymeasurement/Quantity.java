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
    
    public Quantity<U> subtract(Quantity<U> other) {
        if (other == null) {
            throw new IllegalArgumentException("Other quantity cannot be null");
        }
        if (this.unit == null || other.unit == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }
        if (!this.unit.getClass().equals(other.unit.getClass())) {
            throw new IllegalArgumentException("Cannot subtract quantities of different measurement categories");
        }
        if (Double.isNaN(this.value) || Double.isNaN(other.value)
                || Double.isInfinite(this.value) || Double.isInfinite(other.value)) {
            throw new IllegalArgumentException("Values must be finite numbers");
        }

        double baseThis = this.unit.convertToBaseUnit(this.value);
        double baseOther = other.unit.convertToBaseUnit(other.value);

        double baseResult = baseThis - baseOther;

        double resultValue = this.unit.convertFromBaseUnit(baseResult);
        resultValue = round(resultValue);

        return new Quantity<>(resultValue, this.unit);
    }

    public Quantity<U> subtract(Quantity<U> other, U targetUnit) {
        if (other == null) {
            throw new IllegalArgumentException("Other quantity cannot be null");
        }
        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null");
        }
        if (this.unit == null || other.unit == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }
        if (!this.unit.getClass().equals(other.unit.getClass())
                || !this.unit.getClass().equals(targetUnit.getClass())) {
            throw new IllegalArgumentException("Cannot subtract quantities of different measurement categories");
        }
        if (Double.isNaN(this.value) || Double.isNaN(other.value)
                || Double.isInfinite(this.value) || Double.isInfinite(other.value)) {
            throw new IllegalArgumentException("Values must be finite numbers");
        }

        double baseThis = this.unit.convertToBaseUnit(this.value);
        double baseOther = other.unit.convertToBaseUnit(other.value);

        double baseResult = baseThis - baseOther;

        double resultValue = targetUnit.convertFromBaseUnit(baseResult);
        resultValue = round(resultValue);

        return new Quantity<>(resultValue, targetUnit);
    }

    public double divide(Quantity<U> other) {
        if (other == null) {
            throw new IllegalArgumentException("Other quantity cannot be null");
        }
        if (this.unit == null || other.unit == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }
        if (!this.unit.getClass().equals(other.unit.getClass())) {
            throw new IllegalArgumentException("Cannot divide quantities of different measurement categories");
        }
        if (Double.isNaN(this.value) || Double.isNaN(other.value)
                || Double.isInfinite(this.value) || Double.isInfinite(other.value)) {
            throw new IllegalArgumentException("Values must be finite numbers");
        }

        double baseThis = this.unit.convertToBaseUnit(this.value);
        double baseOther = other.unit.convertToBaseUnit(other.value);

        if (baseOther == 0.0) {
            throw new ArithmeticException("Division by zero is not allowed");
        }

        return baseThis / baseOther;
    }

    /**
     * Utility method for rounding to 2 decimal places (consistent with UC10/UC11)
     */
    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
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
    
    @Override
    public String toString() {
        return "Quantity(" + value + ", " + unit.getUnitName() + ")";
    }
}