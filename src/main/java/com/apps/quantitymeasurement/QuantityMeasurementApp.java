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

	public static void main(String[] args) {

		System.out.println("===== UC8 DEMO OUTPUT =====");

		System.out.println(new Length(1, LengthUnit.FEET).convertTo(LengthUnit.INCHES));
		System.out.println(new Length(1, LengthUnit.FEET).add(new Length(12, LengthUnit.INCHES), LengthUnit.FEET));

		System.out.println(new Length(36, LengthUnit.INCHES).equals(new Length(1, LengthUnit.YARDS)));

		System.out.println(new Length(1, LengthUnit.YARDS).add(new Length(3, LengthUnit.FEET), LengthUnit.YARDS));

		System.out.println(new Length(2.54, LengthUnit.CENTIMETERS).convertTo(LengthUnit.INCHES));
	}
}