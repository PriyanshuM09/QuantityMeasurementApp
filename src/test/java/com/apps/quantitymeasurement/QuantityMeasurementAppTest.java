package com.apps.quantitymeasurement;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementAppTest {

  
    @Test
    public void testLengthUnitConversionFactor() {
        assertEquals(12.0, LengthUnit.FEET.getConversionFactor(), 0.0001);
        assertEquals(1.0, LengthUnit.INCHES.getConversionFactor(), 0.0001);
        assertEquals(36.0, LengthUnit.YARDS.getConversionFactor(), 0.0001);
        assertEquals(0.393701, LengthUnit.CENTIMETERS.getConversionFactor(), 0.0001);
    }

    @Test
    public void testLengthConvertToBase() {
        assertEquals(12.0, LengthUnit.FEET.convertToBaseUnit(1), 0.01);
        assertEquals(12.0, LengthUnit.INCHES.convertToBaseUnit(12), 0.01);
        assertEquals(36.0, LengthUnit.YARDS.convertToBaseUnit(1), 0.01);
        assertEquals(12.0, LengthUnit.CENTIMETERS.convertToBaseUnit(30.48), 0.5);
    }

    @Test
    public void testLengthConvertFromBase() {
        assertEquals(1.0, LengthUnit.FEET.convertFromBaseUnit(12), 0.01);
        assertEquals(12.0, LengthUnit.INCHES.convertFromBaseUnit(12), 0.01);
        assertEquals(1.0, LengthUnit.YARDS.convertFromBaseUnit(36), 0.01);
    }



    @Test
    public void testWeightUnitConversionFactor() {
        assertEquals(0.001, WeightUnit.MILLIGRAM.getConversionFactor(), 0.0001);
        assertEquals(1.0, WeightUnit.GRAM.getConversionFactor(), 0.0001);
        assertEquals(1000.0, WeightUnit.KILOGRAM.getConversionFactor(), 0.0001);
        assertEquals(453.592, WeightUnit.POUND.getConversionFactor(), 0.0001);
        assertEquals(1_000_000.0, WeightUnit.TONNE.getConversionFactor(), 0.0001);
    }

    @Test
    public void testWeightConvertToBase() {
        assertEquals(1000.0, WeightUnit.KILOGRAM.convertToBaseUnit(1), 0.01);
        assertEquals(1000.0, WeightUnit.GRAM.convertToBaseUnit(1000), 0.01);
    }

    @Test
    public void testWeightConvertFromBase() {
        assertEquals(1.0, WeightUnit.KILOGRAM.convertFromBaseUnit(1000), 0.01);
    }


    @Test
    public void testLengthEquality() {
        assertTrue(new Quantity<>(1, LengthUnit.FEET)
                .equals(new Quantity<>(12, LengthUnit.INCHES)));
    }

    @Test
    public void testWeightEquality() {
        assertTrue(new Quantity<>(1, WeightUnit.KILOGRAM)
                .equals(new Quantity<>(1000, WeightUnit.GRAM)));
    }

    @Test
    public void testNotEqualDifferentValues() {
        assertFalse(new Quantity<>(1, LengthUnit.FEET)
                .equals(new Quantity<>(10, LengthUnit.INCHES)));
    }

    @Test
    public void testEqualsNull() {
        assertFalse(new Quantity<>(1, LengthUnit.FEET).equals(null));
    }

    @Test
    public void testEqualsDifferentType() {
        assertFalse(new Quantity<>(1, LengthUnit.FEET).equals("test"));
    }

    @Test
    public void testSameReference() {
        Quantity<LengthUnit> q = new Quantity<>(1, LengthUnit.FEET);
        assertTrue(q.equals(q));
    }



    @Test
    public void testLengthConversion() {
        Quantity<LengthUnit> result =
                new Quantity<>(1, LengthUnit.FEET).convertTo(LengthUnit.INCHES);

        assertEquals(12, result.getValue(), 0.01);
    }

    @Test
    public void testWeightConversion() {
        Quantity<WeightUnit> result =
                new Quantity<>(1, WeightUnit.KILOGRAM).convertTo(WeightUnit.GRAM);

        assertEquals(1000, result.getValue(), 0.01);
    }

    @Test
    public void testRoundTripConversion() {
        Quantity<LengthUnit> original = new Quantity<>(5, LengthUnit.FEET);

        Quantity<LengthUnit> result =
                original.convertTo(LengthUnit.INCHES)
                        .convertTo(LengthUnit.FEET);

        assertTrue(original.equals(result));
    }



    @Test
    public void testLengthAddition() {
        Quantity<LengthUnit> result =
                new Quantity<>(1, LengthUnit.FEET)
                        .add(new Quantity<>(12, LengthUnit.INCHES));

        assertTrue(result.equals(new Quantity<>(2, LengthUnit.FEET)));
    }

    @Test
    public void testLengthAdditionTargetUnit() {
        Quantity<LengthUnit> result =
                new Quantity<>(1, LengthUnit.FEET)
                        .add(new Quantity<>(12, LengthUnit.INCHES),
                                LengthUnit.INCHES);

        assertTrue(result.equals(new Quantity<>(24, LengthUnit.INCHES)));
    }

    @Test
    public void testWeightAddition() {
        Quantity<WeightUnit> result =
                new Quantity<>(1, WeightUnit.KILOGRAM)
                        .add(new Quantity<>(1000, WeightUnit.GRAM));

        assertTrue(result.equals(new Quantity<>(2, WeightUnit.KILOGRAM)));
    }



    @Test
    public void testConstructorNullUnit() {
        assertThrows(IllegalArgumentException.class,
                () -> new Quantity<>(1, null));
    }

    @Test
    public void testConstructorNaN() {
        assertThrows(IllegalArgumentException.class,
                () -> new Quantity<>(Double.NaN, LengthUnit.FEET));
    }

    @Test
    public void testConvertToNull() {
        Quantity<LengthUnit> length =
                new Quantity<>(1, LengthUnit.FEET);

        assertThrows(IllegalArgumentException.class,
                () -> length.convertTo(null));
    }

    @Test
    public void testAddNull() {
        Quantity<LengthUnit> length =
                new Quantity<>(1, LengthUnit.FEET);

        assertThrows(IllegalArgumentException.class,
                () -> length.add(null));
    }

    @Test
    public void testAddNullTarget() {
        Quantity<LengthUnit> l1 =
                new Quantity<>(1, LengthUnit.FEET);
        Quantity<LengthUnit> l2 =
                new Quantity<>(1, LengthUnit.FEET);

        assertThrows(IllegalArgumentException.class,
                () -> l1.add(l2, null));
    }


    @Test
    public void testHashCodeConsistency() {
        Quantity<LengthUnit> q1 =
                new Quantity<>(1, LengthUnit.FEET);
        Quantity<LengthUnit> q2 =
                new Quantity<>(12, LengthUnit.INCHES);

        assertEquals(q1.hashCode(), q2.hashCode());
    }


    @Test
    public void testImmutability() {
        Quantity<LengthUnit> l1 =
                new Quantity<>(1, LengthUnit.FEET);

        Quantity<LengthUnit> result =
                l1.convertTo(LengthUnit.INCHES);

        assertNotEquals(l1.getUnit(), result.getUnit());
    }
}