package com.apps.quantitymeasurement;

public class QuantityMeasurementApp {

	public static boolean demonstrateLengthEquality(Length l1, Length l2) {
		return l1.equals(l2);
	}

	public static boolean demonstrateLengthComparison(double v1, LengthUnit u1, double v2, LengthUnit u2) {
		return new Length(v1, u1).equals(new Length(v2, u2));
	}

	public static Length demonstrateLengthConversion(double value, LengthUnit fromUnit, LengthUnit toUnit) {
		return new Length(value, fromUnit).convertTo(toUnit);
	}

	public static Length demonstrateLengthConversion(Length length, LengthUnit toUnit) {
		return length.convertTo(toUnit);
	}

	public static Length demonstrateLengthAddition(Length l1, Length l2) {
		return l1.add(l2);
	}

	public static Length demonstrateLengthAddition(Length l1, Length l2, LengthUnit target) {
		return l1.add(l2, target);
	}
	
	
	// ---------- WEIGHT METHODS (UC9) ----------

		public static boolean demonstrateWeightEquality(Weight w1, Weight w2) {
			return w1.equals(w2);
		}

		public static boolean demonstrateWeightComparison(double v1, WeightUnit u1, double v2, WeightUnit u2) {
			return new Weight(v1, u1).equals(new Weight(v2, u2));
		}

		public static Weight demonstrateWeightConversion(double value, WeightUnit from, WeightUnit to) {
			return new Weight(value, from).convertTo(to);
		}

		public static Weight demonstrateWeightConversion(Weight weight, WeightUnit toUnit) {
			return weight.convertTo(toUnit);
		}

		public static Weight demonstrateWeightAddition(Weight w1, Weight w2) {
			return w1.add(w2);
		}

		public static Weight demonstrateWeightAddition(Weight w1, Weight w2, WeightUnit targetUnit) {
			return w1.add(w2, targetUnit);
		}

	public static void main(String[] args) {

		System.out.println("===== LENGTH DEMO =====");
		Length l1 = new Length(1, LengthUnit.FEET);
		Length l2 = new Length(12, LengthUnit.INCHES);
		System.out.println(l1.add(l2)); // 2 FEET

		System.out.println("\n===== WEIGHT DEMO =====");

		Weight w1 = new Weight(1, WeightUnit.KILOGRAM);
		Weight w2 = new Weight(1000, WeightUnit.GRAM);

		System.out.println("Equality: " + w1.equals(w2));

		System.out.println("Conversion:");
		System.out.println(new Weight(2, WeightUnit.POUND).convertTo(WeightUnit.KILOGRAM));

		System.out.println("Addition (implicit):");
		System.out.println(new Weight(500, WeightUnit.GRAM).add(new Weight(0.5, WeightUnit.KILOGRAM)));

		System.out.println("Addition (explicit target):");
		System.out.println(
				new Weight(1, WeightUnit.KILOGRAM).add(new Weight(453.592, WeightUnit.GRAM), WeightUnit.POUND));
	}
}