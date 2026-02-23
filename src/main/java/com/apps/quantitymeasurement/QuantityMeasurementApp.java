package com.apps.quantitymeasurement;

import com.apps.quantitymeasurement.Length.LengthUnit;

public class QuantityMeasurementApp {

    public static boolean demonstrateLengthEquality(Length l1, Length l2) {
        return l1.equals(l2);
    }

    public static boolean demonstrateLengthComparison(double v1, LengthUnit u1,
                                                      double v2, LengthUnit u2) {
        Length l1 = new Length(v1, u1);
        Length l2 = new Length(v2, u2);
        return demonstrateLengthEquality(l1, l2);
    }

    // Overload 1: raw values
    public static double demonstrateLengthConversion(double value,
                                                     LengthUnit fromUnit,
                                                     LengthUnit toUnit) {
        return Length.convert(value, fromUnit, toUnit);
    }

    // Overload 2: instance method
    public static Length demonstrateLengthConversion(Length length,
                                                     LengthUnit toUnit) {
        return length.convertTo(toUnit);
    }

    public static void main(String[] args) {
        System.out.println("convert(1.0, FEET, INCHES) = "
                + demonstrateLengthConversion(1.0, LengthUnit.FEET, LengthUnit.INCHES));

        System.out.println("convert(3.0, YARDS, FEET) = "
                + demonstrateLengthConversion(3.0, LengthUnit.YARDS, LengthUnit.FEET));

        System.out.println("convert(36.0, INCHES, YARDS) = "
                + demonstrateLengthConversion(36.0, LengthUnit.INCHES, LengthUnit.YARDS));
    }
}