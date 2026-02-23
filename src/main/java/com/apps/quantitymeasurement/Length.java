
	
	
	package com.apps.quantitymeasurement;
	
	
	public class Length {

		private double value;
		private LengthUnit unit;

		public Length(double value, LengthUnit unit) {
			if (unit == null || !Double.isFinite(value))
				throw new IllegalArgumentException("Invalid input");
			this.value = value;
			this.unit = unit;
		}

		private double convertToBaseUnit() {
			return unit.convertToBaseUnit(value);
		}

		private double convertFromBaseToTargetUnit(double baseValue, LengthUnit targetUnit) {
			return targetUnit.convertFromBaseUnit(baseValue);
		}

		private boolean compare(Length that) {
			return Math.abs(this.convertToBaseUnit() - that.convertToBaseUnit()) < 0.01;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (!(obj instanceof Length))
				return false;
			Length that = (Length) obj;
			return compare(that);
		}

		public Length convertTo(LengthUnit targetUnit) {
			if (targetUnit == null)
				throw new IllegalArgumentException("Target unit cannot be null");

			double baseValue = convertToBaseUnit();
			double converted = convertFromBaseToTargetUnit(baseValue, targetUnit);
			return new Length(converted, targetUnit);
		}

		public Length add(Length thatLength) {
			if (thatLength == null)
				throw new IllegalArgumentException("Null length");

			double sumBase = this.convertToBaseUnit() + thatLength.convertToBaseUnit();
			double result = convertFromBaseToTargetUnit(sumBase, this.unit);
			return new Length(result, this.unit);
		}

		public Length add(Length thatLength, LengthUnit targetUnit) {
			if (thatLength == null || targetUnit == null)
				throw new IllegalArgumentException("Invalid input");

			double sumBase = this.convertToBaseUnit() + thatLength.convertToBaseUnit();
			double result = convertFromBaseToTargetUnit(sumBase, targetUnit);
			return new Length(result, targetUnit);
		}

		@Override
		public String toString() {
			return "Quantity(" + value + ", " + unit + ")";
		}
	}