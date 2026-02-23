package com.apps.quantitymeasurement;

public class QuantityMeasurementApp {

    public static void main(String[] args) {

        // ----- LENGTH DEMO -----
        Quantity<LengthUnit> length1 = new Quantity<>(1, LengthUnit.FEET);
        Quantity<LengthUnit> length2 = new Quantity<>(12, LengthUnit.INCHES);

        // equality check
        System.out.println("1 Feet equals 12 Inches : " + length1.equals(length2));

        // conversion
        Quantity<LengthUnit> convertedLength = length1.convertTo(LengthUnit.INCHES);
        System.out.println("1 Feet in Inches : " + convertedLength.getValue());

        // addition
        Quantity<LengthUnit> addedLength = length1.add(length2);
        System.out.println("1 Feet + 12 Inches in Feet : " + addedLength.getValue());

        // ----- WEIGHT DEMO -----
        Quantity<WeightUnit> weight1 = new Quantity<>(1, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> weight2 = new Quantity<>(1000, WeightUnit.GRAM);

        // equality check
        System.out.println("1 Kg equals 1000 g : " + weight1.equals(weight2));

        // conversion
        Quantity<WeightUnit> convertedWeight = weight1.convertTo(WeightUnit.GRAM);
        System.out.println("1 Kg in grams : " + convertedWeight.getValue());

        // addition
        Quantity<WeightUnit> addedWeight = weight1.add(weight2);
        System.out.println("1 Kg + 1000 g in Kg : " + addedWeight.getValue());
    }
}