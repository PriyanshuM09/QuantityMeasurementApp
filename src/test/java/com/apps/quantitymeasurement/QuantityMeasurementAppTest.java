package com.apps.quantitymeasurement;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementAppTest {

		// ---------- LengthUnit ENUM TESTS ----------
	
		@Test
		public void testLengthUnitEnum_FeetConstant() {
			assertEquals(1.0, LengthUnit.FEET.getConversionFactor(), 0.0001);
		}
	
		@Test
		public void testLengthUnitEnum_InchesConstant() {
			assertEquals(1.0 / 12.0, LengthUnit.INCHES.getConversionFactor(), 0.0001);
		}
	
		@Test
		public void testLengthUnitEnum_YardsConstant() {
			assertEquals(3.0, LengthUnit.YARDS.getConversionFactor(), 0.0001);
		}
	
		@Test
		public void testLengthUnitEnum_CentimetersConstant() {
			assertEquals(1.0 / 30.48, LengthUnit.CENTIMETERS.getConversionFactor(), 0.0001);
		}
	
		// ---------- TO BASE UNIT ----------
	
		@Test
		public void testConvertToBaseUnit_FeetToFeet() {
			assertEquals(5.0, LengthUnit.FEET.convertToBaseUnit(5), 0.01);
		}
	
		@Test
		public void testConvertToBaseUnit_InchesToFeet() {
			assertEquals(1.0, LengthUnit.INCHES.convertToBaseUnit(12), 0.01);
		}
	
		@Test
		public void testConvertToBaseUnit_YardsToFeet() {
			assertEquals(3.0, LengthUnit.YARDS.convertToBaseUnit(1), 0.01);
		}
	
		@Test
		public void testConvertToBaseUnit_CentimetersToFeet() {
			assertEquals(1.0, LengthUnit.CENTIMETERS.convertToBaseUnit(30.48), 0.01);
		}
	
		// ---------- FROM BASE UNIT ----------
	
		@Test
		public void testConvertFromBaseUnit_FeetToFeet() {
			assertEquals(2.0, LengthUnit.FEET.convertFromBaseUnit(2), 0.01);
		}
	
		@Test
		public void testConvertFromBaseUnit_FeetToInches() {
			assertEquals(12.0, LengthUnit.INCHES.convertFromBaseUnit(1), 0.01);
		}
	
		@Test
		public void testConvertFromBaseUnit_FeetToYards() {
			assertEquals(1.0, LengthUnit.YARDS.convertFromBaseUnit(3), 0.01);
		}
	
		@Test
		public void testConvertFromBaseUnit_FeetToCentimeters() {
			assertEquals(30.48, LengthUnit.CENTIMETERS.convertFromBaseUnit(1), 0.01);
		}
	
		// ---------- EQUALITY ----------
	
		@Test
		public void testQuantityLengthRefactored_Equality() {
			assertTrue(new Length(1, LengthUnit.FEET).equals(new Length(12, LengthUnit.INCHES)));
		}
	
		@Test
		public void testQuantityLengthRefactored_NotEqual() {
			assertFalse(new Length(1, LengthUnit.FEET).equals(new Length(10, LengthUnit.INCHES)));
		}
	
		// ---------- CONVERSION ----------
	
		@Test
		public void testQuantityLengthRefactored_ConvertTo() {
			Length result = new Length(1, LengthUnit.FEET).convertTo(LengthUnit.INCHES);
			assertTrue(result.equals(new Length(12, LengthUnit.INCHES)));
		}
	
		// ---------- ADDITION ----------
	
		@Test
		public void testQuantityLengthRefactored_Add() {
			Length result = new Length(1, LengthUnit.FEET).add(new Length(12, LengthUnit.INCHES));
			assertTrue(result.equals(new Length(2, LengthUnit.FEET)));
		}
	
		@Test
		public void testQuantityLengthRefactored_AddWithTargetUnit() {
			Length result = new Length(1, LengthUnit.FEET).add(new Length(12, LengthUnit.INCHES), LengthUnit.YARDS);
			assertTrue(result.equals(new Length(0.67, LengthUnit.YARDS)));
		}
	
		// ---------- NULL & INVALID ----------
	
		@Test
		public void testQuantityLengthRefactored_NullUnit() {
			assertThrows(IllegalArgumentException.class, () -> new Length(1, null));
		}
	
		@Test
		public void testQuantityLengthRefactored_InvalidValue() {
			assertThrows(IllegalArgumentException.class, () -> new Length(Double.NaN, LengthUnit.FEET));
		}
	
		// ---------- ROUND TRIP ----------
	
		@Test
		public void testRoundTripConversion_RefactoredDesign() {
			Length l = new Length(5, LengthUnit.FEET);
			Length result = l.convertTo(LengthUnit.INCHES).convertTo(LengthUnit.FEET);
			assertTrue(l.equals(result));
		}
	
		// ---------- BACKWARD COMPATIBILITY ----------
	
		@Test
		public void testBackwardCompatibility_UC1Equality() {
			assertTrue(new Length(3, LengthUnit.FEET).equals(new Length(36, LengthUnit.INCHES)));
		}
	
		@Test
		public void testBackwardCompatibility_UC5Conversion() {
			Length result = new Length(3, LengthUnit.FEET).convertTo(LengthUnit.INCHES);
			assertTrue(result.equals(new Length(36, LengthUnit.INCHES)));
		}
	
		@Test
		public void testBackwardCompatibility_UC6Addition() {
			Length result = new Length(1, LengthUnit.FEET).add(new Length(12, LengthUnit.INCHES));
			assertTrue(result.equals(new Length(2, LengthUnit.FEET)));
		}
	
		@Test
		public void testBackwardCompatibility_UC7AdditionWithTarget() {
			Length result = new Length(1, LengthUnit.FEET).add(new Length(12, LengthUnit.INCHES), LengthUnit.INCHES);
			assertTrue(result.equals(new Length(24, LengthUnit.INCHES)));
		}
	
		// ---------- IMMUTABILITY ----------
	
		@Test
		public void testUnitImmutability() {
			assertNotNull(LengthUnit.valueOf("FEET"));
		}
	
		@Test
		public void testEqualsReturnsFalseForNull() {
			Length length = new Length(1, LengthUnit.FEET);
			assertFalse(length.equals(null));
		}
	
		@Test
		public void testAddNullThrowsException() {
			Length length = new Length(1, LengthUnit.FEET);
			assertThrows(IllegalArgumentException.class, () -> length.add(null));
		}
	
		@Test
		public void testAddNullWithTargetUnitThrowsException() {
			Length length = new Length(1, LengthUnit.FEET);
			assertThrows(IllegalArgumentException.class, () -> length.add(null, LengthUnit.FEET));
		}
	
		@Test
		public void testAddWithNullTargetUnitThrowsException() {
			Length l1 = new Length(1, LengthUnit.FEET);
			Length l2 = new Length(1, LengthUnit.FEET);
	
			assertThrows(IllegalArgumentException.class, () -> l1.add(l2, null));
		}
	
		@Test
		public void testMathCorrectnessAcrossUnits() {
			Length result1 = new Length(1, LengthUnit.FEET).add(new Length(12, LengthUnit.INCHES), LengthUnit.FEET);
	
			Length result2 = new Length(12, LengthUnit.INCHES).add(new Length(1, LengthUnit.FEET), LengthUnit.INCHES);
	
			assertTrue(result1.equals(new Length(2, LengthUnit.FEET)));
			assertTrue(result2.equals(new Length(24, LengthUnit.INCHES)));
		}
	
		@Test
		public void testEnumAccessibility() {
			assertNotNull(LengthUnit.FEET);
			assertNotNull(LengthUnit.INCHES);
			assertNotNull(LengthUnit.YARDS);
			assertNotNull(LengthUnit.CENTIMETERS);
		}
	
	    // ================= WEIGHT EQUALITY =================

	    @Test
	    public void testEquality_KilogramToKilogram_SameValue() {
	        assertTrue(new Weight(1.0, WeightUnit.KILOGRAM)
	                .equals(new Weight(1.0, WeightUnit.KILOGRAM)));
	    }

	    @Test
	    public void testEquality_KilogramToKilogram_DifferentValue() {
	        assertFalse(new Weight(1.0, WeightUnit.KILOGRAM)
	                .equals(new Weight(2.0, WeightUnit.KILOGRAM)));
	    }

	    @Test
	    public void testEquality_KilogramToGram_EquivalentValue() {
	        assertTrue(new Weight(1.0, WeightUnit.KILOGRAM)
	                .equals(new Weight(1000.0, WeightUnit.GRAM)));
	    }

	    @Test
	    public void testEquality_GramToKilogram_EquivalentValue() {
	        assertTrue(new Weight(1000.0, WeightUnit.GRAM)
	                .equals(new Weight(1.0, WeightUnit.KILOGRAM)));
	    }

	    @Test
	    public void testEquality_WeightVsLength_Incompatible() {
	        assertFalse(new Weight(1.0, WeightUnit.KILOGRAM)
	                .equals(new Length(1.0, LengthUnit.FEET)));
	    }

	    @Test
	    public void testEquality_NullComparison() {
	        assertFalse(new Weight(1.0, WeightUnit.KILOGRAM).equals(null));
	    }

	    @Test
	    public void testEquality_SameReference() {
	        Weight w = new Weight(1.0, WeightUnit.KILOGRAM);
	        assertTrue(w.equals(w));
	    }

	    @Test
	    public void testEquality_ZeroValue() {
	        assertTrue(new Weight(0.0, WeightUnit.KILOGRAM)
	                .equals(new Weight(0.0, WeightUnit.GRAM)));
	    }

	    @Test
	    public void testEquality_NegativeWeight() {
	        assertTrue(new Weight(-1.0, WeightUnit.KILOGRAM)
	                .equals(new Weight(-1000.0, WeightUnit.GRAM)));
	    }

	    @Test
	    public void testEquality_LargeWeightValue() {
	        assertTrue(new Weight(1_000_000.0, WeightUnit.GRAM)
	                .equals(new Weight(1000.0, WeightUnit.KILOGRAM)));
	    }

	    @Test
	    public void testEquality_SmallWeightValue() {
	        assertTrue(new Weight(0.001, WeightUnit.KILOGRAM)
	                .equals(new Weight(1.0, WeightUnit.GRAM)));
	    }

	    // ================= CONVERSION =================

	    @Test
	    public void testConversion_PoundToKilogram() {
	        Weight result = new Weight(2.20462, WeightUnit.POUND)
	                .convertTo(WeightUnit.KILOGRAM);

	        assertEquals(1.0, result.getValue(), 0.01);
	    }

	    @Test
	    public void testConversion_KilogramToPound() {
	        Weight result = new Weight(1.0, WeightUnit.KILOGRAM)
	                .convertTo(WeightUnit.POUND);

	        assertEquals(2.20462, result.getValue(), 0.01);
	    }

	    @Test
	    public void testConversion_SameUnit() {
	        Weight result = new Weight(5.0, WeightUnit.KILOGRAM)
	                .convertTo(WeightUnit.KILOGRAM);

	        assertEquals(5.0, result.getValue(), 0.01);
	    }

	    @Test
	    public void testConversion_ZeroValue() {
	        Weight result = new Weight(0.0, WeightUnit.KILOGRAM)
	                .convertTo(WeightUnit.GRAM);

	        assertEquals(0.0, result.getValue(), 0.01);
	    }

	    @Test
	    public void testConversion_NegativeValue() {
	        Weight result = new Weight(-1.0, WeightUnit.KILOGRAM)
	                .convertTo(WeightUnit.GRAM);

	        assertEquals(-1000.0, result.getValue(), 0.01);
	    }

	    @Test
	    public void testConversion_RoundTrip() {
	        Weight w = new Weight(1.5, WeightUnit.KILOGRAM);
	        Weight result = w.convertTo(WeightUnit.GRAM).convertTo(WeightUnit.KILOGRAM);

	        assertEquals(w.getValue(), result.getValue(), 0.01);
	    }

	    // ================= ADDITION =================

	    @Test
	    public void testAddition_SameUnit_KilogramPlusKilogram() {
	        Weight result = new Weight(1.0, WeightUnit.KILOGRAM)
	                .add(new Weight(2.0, WeightUnit.KILOGRAM));

	        assertEquals(3.0, result.getValue(), 0.01);
	    }

	    @Test
	    public void testAddition_CrossUnit_KilogramPlusGram() {
	        Weight result = new Weight(1.0, WeightUnit.KILOGRAM)
	                .add(new Weight(1000.0, WeightUnit.GRAM));

	        assertEquals(2.0, result.getValue(), 0.01);
	    }

	    @Test
	    public void testAddition_CrossUnit_PoundPlusKilogram() {
	        Weight result = new Weight(2.20462, WeightUnit.POUND)
	                .add(new Weight(1.0, WeightUnit.KILOGRAM));

	        assertEquals(4.40924, result.getValue(), 0.01);
	    }

	    @Test
	    public void testAddition_ExplicitTargetUnit() {
	        Weight result = new Weight(1.0, WeightUnit.KILOGRAM)
	                .add(new Weight(1000.0, WeightUnit.GRAM), WeightUnit.GRAM);

	        assertEquals(2000.0, result.getValue(), 0.01);
	    }

	    @Test
	    public void testAddition_Commutativity() {
	        Weight a = new Weight(1.0, WeightUnit.KILOGRAM);
	        Weight b = new Weight(1000.0, WeightUnit.GRAM);

	        assertTrue(a.add(b).equals(b.add(a)));
	    }

	    @Test
	    public void testAddition_WithZero() {
	        Weight result = new Weight(5.0, WeightUnit.KILOGRAM)
	                .add(new Weight(0.0, WeightUnit.GRAM));

	        assertEquals(5.0, result.getValue(), 0.01);
	    }

	    @Test
	    public void testAddition_NegativeValues() {
	        Weight result = new Weight(5.0, WeightUnit.KILOGRAM)
	                .add(new Weight(-2000.0, WeightUnit.GRAM));

	        assertEquals(3.0, result.getValue(), 0.01);
	    }

	    @Test
	    public void testAddition_LargeValues() {
	        Weight result = new Weight(1e6, WeightUnit.KILOGRAM)
	                .add(new Weight(1e6, WeightUnit.KILOGRAM));

	        assertEquals(2e6, result.getValue(), 0.01);
	    }

	   

	    @Test
	    public void testConstructor_NullUnitThrows() {
	        assertThrows(IllegalArgumentException.class,
	                () -> new Weight(1.0, null));
	    }

	    @Test
	    public void testConstructor_InvalidValueThrows() {
	        assertThrows(IllegalArgumentException.class,
	                () -> new Weight(Double.NaN, WeightUnit.KILOGRAM));
	    }

	
}