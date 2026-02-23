package com.apps.quantitymeasurement;

public class QuantityMeasurementApp {
	
	public static <U extends IMeasurable> Quantity<U> demonstrateSubtraction(
	        Quantity<U> quantity1, Quantity<U> quantity2) {

	    if (quantity1 == null || quantity2 == null) {
	        throw new IllegalArgumentException("Quantities cannot be null");
	    }

	    return quantity1.subtract(quantity2);
	}

	public static <U extends IMeasurable> Quantity<U> demonstrateSubtraction(
	        Quantity<U> quantity1, Quantity<U> quantity2, U targetUnit) {

	    if (quantity1 == null || quantity2 == null) {
	        throw new IllegalArgumentException("Quantities cannot be null");
	    }
	    if (targetUnit == null) {
	        throw new IllegalArgumentException("Target unit cannot be null");
	    }

	    return quantity1.subtract(quantity2, targetUnit);
	}

	public static <U extends IMeasurable> double demonstrateDivision(
	        Quantity<U> quantity1, Quantity<U> quantity2) {

	    if (quantity1 == null || quantity2 == null) {
	        throw new IllegalArgumentException("Quantities cannot be null");
	    }

	    return quantity1.divide(quantity2);
	}
	
	

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
        
        // VolumeUnit
        Quantity<VolumeUnit> v1 = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> v2 = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
        Quantity<VolumeUnit> v3 = new Quantity<>(1.0, VolumeUnit.GALLON);

        System.out.println(v1.equals(v2)); 
        System.out.println(v1.convertTo(VolumeUnit.MILLILITRE)); 
        System.out.println(v1.add(v2)); 
        System.out.println(v1.add(v3, VolumeUnit.MILLILITRE)); 
        
        
        // Length examples
        Quantity<LengthUnit> l1 = new Quantity<>(10.0, LengthUnit.FEET);
        Quantity<LengthUnit> l2 = new Quantity<>(6.0, LengthUnit.INCHES);

        System.out.println(demonstrateSubtraction(l1, l2)); 
        System.out.println(demonstrateSubtraction(l1, l2, LengthUnit.INCHES));
        System.out.println(demonstrateDivision(l1, new Quantity<>(2.0, LengthUnit.FEET)));

        // Weight examples
        Quantity<WeightUnit> w1 = new Quantity<>(10.0, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> w2 = new Quantity<>(5000.0, WeightUnit.GRAM);

        System.out.println(demonstrateSubtraction(w1, w2));
        System.out.println(demonstrateDivision(w1, new Quantity<>(5.0, WeightUnit.KILOGRAM)));

        // Volume examples
        Quantity<VolumeUnit> v4 = new Quantity<>(5.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> v5 = new Quantity<>(500.0, VolumeUnit.MILLILITRE);

        System.out.println(demonstrateSubtraction(v4, v5));
        System.out.println(demonstrateDivision(v4, new Quantity<>(10.0, VolumeUnit.LITRE)));
    }
}