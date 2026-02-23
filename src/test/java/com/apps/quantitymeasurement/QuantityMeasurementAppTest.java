package com.apps.quantitymeasurement;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementAppTest {

    // LENGTH ENUM TESTS


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

    // WEIGHT ENUM TESTS
    
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

    // QUANTITY EQUALITY TESTS
    // ================================

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

    // CONVERSION TESTS
    // ================================

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

    // ADDITION TESTS
    // ================================

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

    // ================================
    // NULL & INVALID TESTS
    // ================================

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

    // ================================
    // HASHCODE TEST
    // ================================

    @Test
    public void testHashCodeConsistency() {
        Quantity<LengthUnit> q1 =
                new Quantity<>(1, LengthUnit.FEET);
        Quantity<LengthUnit> q2 =
                new Quantity<>(12, LengthUnit.INCHES);

        assertEquals(q1.hashCode(), q2.hashCode());
    }

    // ================================
    // IMMUTABILITY TEST
    // ================================

    @Test
    public void testImmutability() {
        Quantity<LengthUnit> l1 =
                new Quantity<>(1, LengthUnit.FEET);

        Quantity<LengthUnit> result =
                l1.convertTo(LengthUnit.INCHES);

        assertNotEquals(l1.getUnit(), result.getUnit());
    }
    
    
    // Volume Enums Test
    
    @Test
    void testEquality_LitreToMillilitre() {
        Quantity<VolumeUnit> v1 = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> v2 = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
        assertTrue(v1.equals(v2));
    }

    @Test
    void testEquality_LitreToGallon() {
        Quantity<VolumeUnit> v1 = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> v2 = new Quantity<>(0.264172, VolumeUnit.GALLON);
        assertTrue(v1.equals(v2));
    }

    @Test
    void testConversion_LitreToMillilitre() {
        Quantity<VolumeUnit> v = new Quantity<>(1.0, VolumeUnit.LITRE)
                .convertTo(VolumeUnit.MILLILITRE);
        assertEquals(1000.0, v.getValue(), 0.0001);
    }

    @Test
    void testAddition_CrossUnit() {
        Quantity<VolumeUnit> v1 = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> v2 = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);

        Quantity<VolumeUnit> sum = v1.add(v2);

        assertEquals(2.0, sum.getValue(), 0.0001);
        assertEquals(VolumeUnit.LITRE, sum.getUnit());
    }

    @Test
    void testCategoryIsolation_VolumeVsLength() {
        Quantity<VolumeUnit> v = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<LengthUnit> l = new Quantity<>(1.0, LengthUnit.FEET);

        assertFalse(v.equals(l));
    }
    
    
    // Equality Properties
    @Test
    void testEquality_SymmetricProperty() {
        Quantity<VolumeUnit> a = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> b = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);

        assertTrue(a.equals(b));
        assertTrue(b.equals(a));
    }

    @Test
    void testEquality_TransitiveProperty() {
        Quantity<VolumeUnit> a = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> b = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
        Quantity<VolumeUnit> c = new Quantity<>(0.264172, VolumeUnit.GALLON);

        assertTrue(a.equals(b));
        assertTrue(b.equals(c));
        assertTrue(a.equals(c));
    }

    @Test
    void testEquality_ReflexiveProperty() {
        Quantity<VolumeUnit> a = new Quantity<>(2.5, VolumeUnit.GALLON);
        assertTrue(a.equals(a));
    }
    
    
    // Inequality Tests
    @Test
    void testEquality_DifferentValues_SameUnit() {
        Quantity<VolumeUnit> v1 = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> v2 = new Quantity<>(2.0, VolumeUnit.LITRE);

        assertFalse(v1.equals(v2));
    }

    @Test
    void testEquality_DifferentValues_CrossUnit() {
        Quantity<VolumeUnit> v1 = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> v2 = new Quantity<>(500.0, VolumeUnit.MILLILITRE);

        assertFalse(v1.equals(v2));
    }
    
    
    // Precision & Floating-Point Tolerance
    @Test
    void testEquality_FloatingPointTolerance() {
        Quantity<VolumeUnit> v1 = new Quantity<>(3.78541, VolumeUnit.LITRE);
        Quantity<VolumeUnit> v2 = new Quantity<>(1.0, VolumeUnit.GALLON);

        assertTrue(v1.equals(v2)); // within epsilon
    }

    @Test
    void testConversion_RoundTripPrecision() {
        Quantity<VolumeUnit> original = new Quantity<>(1.234567, VolumeUnit.LITRE);
        Quantity<VolumeUnit> roundTrip =
                original.convertTo(VolumeUnit.MILLILITRE).convertTo(VolumeUnit.LITRE);

        assertEquals(original.getValue(), roundTrip.getValue(), 0.000001);
    }
    
    
    // Addition Properties
    
    @Test
    void testAddition_Commutativity() {
        Quantity<VolumeUnit> a = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> b = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);

        Quantity<VolumeUnit> sum1 = a.add(b);
        Quantity<VolumeUnit> sum2 = b.add(a);

        assertTrue(sum1.equals(sum2));
    }

    @Test
    void testAddition_Associativity() {
        Quantity<VolumeUnit> a = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> b = new Quantity<>(500.0, VolumeUnit.MILLILITRE);
        Quantity<VolumeUnit> c = new Quantity<>(0.5, VolumeUnit.LITRE);

        Quantity<VolumeUnit> left = a.add(b).add(c);
        Quantity<VolumeUnit> right = a.add(b.add(c));

        assertTrue(left.equals(right));
    }

    @Test
    void testAddition_IdentityZero() {
        Quantity<VolumeUnit> a = new Quantity<>(2.0, VolumeUnit.GALLON);
        Quantity<VolumeUnit> zero = new Quantity<>(0.0, VolumeUnit.MILLILITRE);

        Quantity<VolumeUnit> result = a.add(zero);

        assertTrue(result.equals(a));
    }
    
    // Edge Cases
    @Test
    void testZeroAcrossUnits() {
        Quantity<VolumeUnit> v1 = new Quantity<>(0.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> v2 = new Quantity<>(0.0, VolumeUnit.GALLON);

        assertTrue(v1.equals(v2));
    }

    @Test
    void testNegativeValuesAcrossUnits() {
        Quantity<VolumeUnit> v1 = new Quantity<>(-1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> v2 = new Quantity<>(-1000.0, VolumeUnit.MILLILITRE);

        assertTrue(v1.equals(v2));
    }

    @Test
    void testLargeValues() {
        Quantity<VolumeUnit> v1 = new Quantity<>(1_000_000.0, VolumeUnit.MILLILITRE);
        Quantity<VolumeUnit> v2 = new Quantity<>(1000.0, VolumeUnit.LITRE);

        assertTrue(v1.equals(v2));
    }

    @Test
    void testSmallValues() {
        Quantity<VolumeUnit> v1 = new Quantity<>(0.001, VolumeUnit.LITRE);
        Quantity<VolumeUnit> v2 = new Quantity<>(1.0, VolumeUnit.MILLILITRE);

        assertTrue(v1.equals(v2));
    }
    
    // Conversion Coverage
    @Test
    void testConvert_MillilitreToGallon() {
        Quantity<VolumeUnit> v = new Quantity<>(1000.0, VolumeUnit.MILLILITRE)
                .convertTo(VolumeUnit.GALLON);

        assertEquals(0.264172, v.getValue(), 0.0001);
    }

    @Test
    void testConvert_SameUnit_NoChange() {
        Quantity<VolumeUnit> v = new Quantity<>(5.0, VolumeUnit.LITRE)
                .convertTo(VolumeUnit.LITRE);

        assertEquals(5.0, v.getValue(), 0.0001);
    }
    
    @Test
    void testAddition_DoesNotMutateOriginals() {
        Quantity<VolumeUnit> a = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> b = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);

        a.add(b);

        assertEquals(1.0, a.getValue(), 0.0001);
        assertEquals(1000.0, b.getValue(), 0.0001);
    }
    
    // Cross-Category Safety
    @Test
    void testVolumeVsWeight_Incompatible() {
        Quantity<VolumeUnit> volume = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<WeightUnit> weight = new Quantity<>(1.0, WeightUnit.KILOGRAM);

        assertFalse(volume.equals(weight));
    }

    @Test
    void testVolumeVsLength_Incompatible() {
        Quantity<VolumeUnit> volume = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<LengthUnit> length = new Quantity<>(1.0, LengthUnit.FEET);

        assertFalse(volume.equals(length));
    }
    
    // HashCode Consistency
    
    @Test
    void testHashCode_EqualVolumesSameHash() {
        Quantity<VolumeUnit> a = new Quantity<>(1.0, VolumeUnit.LITRE);
        Quantity<VolumeUnit> b = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);

        assertEquals(a.hashCode(), b.hashCode());
    }
    
}