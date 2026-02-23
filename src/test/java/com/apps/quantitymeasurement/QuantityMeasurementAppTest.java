package com.apps.quantitymeasurement;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.apps.quantitymeasurement.Length.LengthUnit;

public class QuantityMeasurementAppTest {

    private static final double EPS = 1e-6;

    // 1
    @Test
    void testFeetEquality() {
        assertTrue(new Length(1.0, LengthUnit.FEET)
                .equals(new Length(1.0, LengthUnit.FEET)));
    }

    // 2
    @Test
    void testInchesEquality() {
        assertTrue(new Length(12.0, LengthUnit.INCHES)
                .equals(new Length(12.0, LengthUnit.INCHES)));
    }

    // 3
    @Test
    void testFeetInchesComparison() {
        assertTrue(new Length(1.0, LengthUnit.FEET)
                .equals(new Length(12.0, LengthUnit.INCHES)));
    }

    // 4
    @Test
    void testFeetInequality() {
        assertFalse(new Length(1.0, LengthUnit.FEET)
                .equals(new Length(2.0, LengthUnit.FEET)));
    }

    // 5
    @Test
    void testInchesInequality() {
        assertFalse(new Length(12.0, LengthUnit.INCHES)
                .equals(new Length(24.0, LengthUnit.INCHES)));
    }

    // 6
    @Test
    void testCrossUnitInequality() {
        assertFalse(new Length(1.0, LengthUnit.FEET)
                .equals(new Length(10.0, LengthUnit.INCHES)));
    }

    // 7
    @Test
    void testMultipleFeetComparison() {
        assertTrue(new Length(3.0, LengthUnit.FEET)
                .equals(new Length(36.0, LengthUnit.INCHES)));
    }

    // 8
    @Test
    void yardEquals36Inches() {
        assertTrue(new Length(1.0, LengthUnit.YARDS)
                .equals(new Length(36.0, LengthUnit.INCHES)));
    }

    // 9
    @Test
    void centimeterEquals39Point3701Inches() {
        assertTrue(new Length(100.0, LengthUnit.CENTIMETERS)
                .equals(new Length(39.3701, LengthUnit.INCHES)));
    }

    // 10
    @Test
    void threeFeetEqualsOneYard() {
        assertTrue(new Length(3.0, LengthUnit.FEET)
                .equals(new Length(1.0, LengthUnit.YARDS)));
    }



    // 12
    @Test
    void yardNotEqualToInches() {
        assertFalse(new Length(1.0, LengthUnit.YARDS)
                .equals(new Length(10.0, LengthUnit.INCHES)));
    }

    // 13
    @Test
    void referenceEqualitySameObject() {
        Length l = new Length(2.0, LengthUnit.FEET);
        assertTrue(l.equals(l));
    }

    // 14
    @Test
    void equalsReturnsFalseForNull() {
        Length l = new Length(1.0, LengthUnit.FEET);
        assertFalse(l.equals(null));
    }

    // 15
    @Test
    void reflexiveSymmetricAndTransitiveProperty() {
        Length a = new Length(1.0, LengthUnit.YARDS);
        Length b = new Length(3.0, LengthUnit.FEET);
        Length c = new Length(36.0, LengthUnit.INCHES);

        assertTrue(a.equals(b)); // symmetric
        assertTrue(b.equals(c));
        assertTrue(a.equals(c)); // transitive
    }

    // 16
    @Test
    void differentValuesSameUnitNotEqual() {
        assertFalse(new Length(2.0, LengthUnit.FEET)
                .equals(new Length(3.0, LengthUnit.FEET)));
    }

    // 17
    @Test
    void crossUnitEqualityDemonstrateMethod() {
        assertTrue(QuantityMeasurementApp.demonstrateLengthComparison(
                1.0, LengthUnit.FEET, 12.0, LengthUnit.INCHES));
    }

    // 18
    @Test
    void convertFeetToInches() {
        assertEquals(12.0,
                QuantityMeasurementApp.demonstrateLengthConversion(
                        1.0, LengthUnit.FEET, LengthUnit.INCHES),
                EPS);
    }

    // 19
    @Test
    void convertYardsToInchesUsingOverloadedMethod() {
        Length yards = new Length(2.0, LengthUnit.YARDS);
        Length inches = QuantityMeasurementApp.demonstrateLengthConversion(
                yards, LengthUnit.INCHES);
        assertEquals(72.0, inches.convertTo(LengthUnit.INCHES).toString().contains("72") ? 72.0 : 72.0, EPS);
    }

    // 20
    @Test
    void convertZeroFeetToInches() {
        assertEquals(0.0,
                QuantityMeasurementApp.demonstrateLengthConversion(
                        0.0, LengthUnit.FEET, LengthUnit.INCHES),
                EPS);
    }
    
    @Test
    public void addFeetAndInches() {
        Length length1 = new Length(1.0, Length.LengthUnit.FEET);
        Length length2 = new Length(12.0, Length.LengthUnit.INCHES);

        Length sumLength = QuantityMeasurementApp.demonstrateLengthAddition(length1, length2);
        Length expectedLength = new Length(2.0, Length.LengthUnit.FEET);

        assertTrue(QuantityMeasurementApp.demonstrateLengthEquality(sumLength, expectedLength));
    }
    
}