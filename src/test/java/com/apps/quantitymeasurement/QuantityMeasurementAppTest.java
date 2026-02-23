package com.apps.quantitymeasurement;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.apps.quantitymeasurement.Length.LengthUnit;

public class QuantityMeasurementAppTest {

    @Test
    void testEquality_YardToYard_SameValue() {
        Length y1 = new Length(1.0, LengthUnit.YARDS);
        Length y2 = new Length(1.0, LengthUnit.YARDS);
        assertTrue(y1.equals(y2));
    }

    @Test
    void testEquality_YardToYard_DifferentValue() {
        Length y1 = new Length(1.0, LengthUnit.YARDS);
        Length y2 = new Length(2.0, LengthUnit.YARDS);
        assertFalse(y1.equals(y2));
    }

    @Test
    void testEquality_YardToFeet_EquivalentValue() {
        Length yards = new Length(1.0, LengthUnit.YARDS);
        Length feet = new Length(3.0, LengthUnit.FEET);
        assertTrue(yards.equals(feet));
    }

    @Test
    void testEquality_FeetToYard_EquivalentValue() {
        Length feet = new Length(3.0, LengthUnit.FEET);
        Length yards = new Length(1.0, LengthUnit.YARDS);
        assertTrue(feet.equals(yards)); // symmetry
    }

    @Test
    void testEquality_YardToInches_EquivalentValue() {
        Length yards = new Length(1.0, LengthUnit.YARDS);
        Length inches = new Length(36.0, LengthUnit.INCHES);
        assertTrue(yards.equals(inches));
    }

    @Test
    void testEquality_InchesToYard_EquivalentValue() {
        Length inches = new Length(36.0, LengthUnit.INCHES);
        Length yards = new Length(1.0, LengthUnit.YARDS);
        assertTrue(inches.equals(yards)); // symmetry
    }

    @Test
    void testEquality_YardToFeet_NonEquivalentValue() {
        Length yards = new Length(1.0, LengthUnit.YARDS);
        Length feet = new Length(2.0, LengthUnit.FEET);
        assertFalse(yards.equals(feet));
    }

    @Test
    void testEquality_centimetersToInches_EquivalentValue() {
        Length cm = new Length(1.0, LengthUnit.CENTIMETERS);
        Length inches = new Length(0.393701, LengthUnit.INCHES);
        assertTrue(cm.equals(inches));
        assertTrue(inches.equals(cm)); // symmetry
    }

    @Test
    void testEquality_centimetersToFeet_NonEquivalentValue() {
        Length cm = new Length(1.0, LengthUnit.CENTIMETERS);
        Length feet = new Length(1.0, LengthUnit.FEET);
        assertFalse(cm.equals(feet));
    }

    @Test
    void testEquality_MultiUnit_TransitiveProperty() {
        Length yards = new Length(1.0, LengthUnit.YARDS);
        Length feet = new Length(3.0, LengthUnit.FEET);
        Length inches = new Length(36.0, LengthUnit.INCHES);

        assertTrue(yards.equals(feet));
        assertTrue(feet.equals(inches));
        assertTrue(yards.equals(inches)); // transitive
    }

    @Test
    void testEquality_YardWithNullUnit() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Length(1.0, null);
        });
    }

    @Test
    void testEquality_YardSameReference() {
        Length yards = new Length(2.0, LengthUnit.YARDS);
        assertTrue(yards.equals(yards)); // reflexive
    }

    @Test
    void testEquality_YardNullComparison() {
        Length yards = new Length(1.0, LengthUnit.YARDS);
        assertFalse(yards.equals(null));
    }

    @Test
    void testEquality_CentimetersWithNullUnit() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Length(1.0, null);
        });
    }

    @Test
    void testEquality_CentimetersSameReference() {
        Length cm = new Length(2.0, LengthUnit.CENTIMETERS);
        assertTrue(cm.equals(cm)); // reflexive
    }

    @Test
    void testEquality_CentimetersNullComparison() {
        Length cm = new Length(1.0, LengthUnit.CENTIMETERS);
        assertFalse(cm.equals(null));
    }

    @Test
    void testEquality_AllUnits_ComplexScenario() {
        Length yards = new Length(2.0, LengthUnit.YARDS);
        Length feet = new Length(6.0, LengthUnit.FEET);
        Length inches = new Length(72.0, LengthUnit.INCHES);

        assertTrue(yards.equals(feet));
        assertTrue(feet.equals(inches));
        assertTrue(yards.equals(inches)); // transitive across all units
    }
}