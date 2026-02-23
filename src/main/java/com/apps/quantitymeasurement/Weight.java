package com.apps.quantitymeasurement;

public class Weight {

	private final double value;
	private final WeightUnit unit;

	public Weight(double value, WeightUnit unit) {
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

	public WeightUnit getUnit() {
		return unit;
	}

	// ---------- Equality ----------
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;

		Weight other = (Weight) obj;
		return Double.compare(convertToBaseUnit(), other.convertToBaseUnit()) == 0;
	}

	// ---------- Conversion ----------
	public Weight convertTo(WeightUnit targetUnit) {
		if (targetUnit == null)
			throw new IllegalArgumentException("Target unit cannot be null");

		double base = convertToBaseUnit();
		double converted = targetUnit.convertFromBaseUnit(base);
		return new Weight(converted, targetUnit);
	}

	// ---------- Addition (implicit target = first operand) ----------
	public Weight add(Weight other) {
		if (other == null)
			throw new IllegalArgumentException("Weight cannot be null");

		double sumBase = convertToBaseUnit() + other.convertToBaseUnit();
		double result = unit.convertFromBaseUnit(sumBase);
		return new Weight(result, unit);
	}

	// ---------- Addition with explicit target unit ----------
	public Weight add(Weight other, WeightUnit targetUnit) {
		if (other == null || targetUnit == null)
			throw new IllegalArgumentException("Invalid input");

		double sumBase = convertToBaseUnit() + other.convertToBaseUnit();
		double result = targetUnit.convertFromBaseUnit(sumBase);
		return new Weight(result, targetUnit);
	}

	private double convertToBaseUnit() {
		return unit.convertToBaseUnit(value);
	}

	@Override
	public String toString() {
		return "Quantity(" + value + ", " + unit + ")";
	}
}