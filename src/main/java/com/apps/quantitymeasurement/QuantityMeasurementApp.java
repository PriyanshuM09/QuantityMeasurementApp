package com.apps.quantitymeasurement;

import com.apps.quantitymeasurement.Length.LengthUnit;

public class QuantityMeasurementApp {

    // UC1–UC4: Equality
    public static boolean demonstrateLengthEquality(Length l1, Length l2) {
        return l1.equals(l2);
    }

    public static boolean demonstrateLengthComparison(double v1, LengthUnit u1,
                                                      double v2, LengthUnit u2) {
        return demonstrateLengthEquality(new Length(v1, u1), new Length(v2, u2));
    }

    // UC5: Conversion (overloaded)
    public static double demonstrateLengthConversion(double value,
                                                     LengthUnit fromUnit,
                                                     LengthUnit toUnit) {
        return Length.convert(value, fromUnit, toUnit);
    }

    public static Length demonstrateLengthConversion(Length length, LengthUnit toUnit) {
        return length.convertTo(toUnit);
    }

    // UC6: Addition
    public static Length demonstrateLengthAddition(Length length1, Length length2) {
        return length1.add(length2);
    }
    
 // UC7: Target addition
    public static Length demonstrateLengthAddition(Length l1, Length l2, Length.LengthUnit targetUnit) {
        return l1.add(l2, targetUnit); // UC7
    }

    public static void main(String[] args) {
        System.out.println("1 ft == 12 in ? " +
                demonstrateLengthComparison(1.0, LengthUnit.FEET, 12.0, LengthUnit.INCHES));

        System.out.println("convert(3 yards -> feet) = " +
                demonstrateLengthConversion(3.0, LengthUnit.YARDS, LengthUnit.FEET));

        Length sum = demonstrateLengthAddition(
                new Length(1.0, LengthUnit.FEET),
                new Length(12.0, LengthUnit.INCHES)
        );
        System.out.println("1 ft + 12 in = " + sum);
    }
}