package com.apps.quantitymeasurement;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.apps.quantitymeasurement.Length.LengthUnit;

public class QuantityMeasurementAppTest {

	// FEET TESTS (converted from UC1)
		@Test
		public void testQuantity_Feet_SameValue() {
			Length q1 = new Length(1.0, LengthUnit.FEET);
			Length q2 = new Length(1.0, LengthUnit.FEET);
			assertTrue(q1.equals(q2));
		}

		@Test
		public void testQuantity_Feet_DifferentValue() {
			Length q1 = new Length(1.0, LengthUnit.FEET);
			Length q2 = new Length(2.0, LengthUnit.FEET);
			assertFalse(q1.equals(q2));
		}

		@Test
		public void testQuantity_Feet_NullComparison() {
			Length q1 = new Length(1.0, LengthUnit.FEET);
			assertFalse(q1.equals(null));
		}

		@Test
		public void testQuantity_Feet_DifferentClass() {
			Length q1 = new Length(1.0, LengthUnit.FEET);
			String other = "NotQuantity";
			assertFalse(q1.equals(other));
		}

		@Test
		public void testQuantity_Feet_SameReference() {
			Length q1 = new Length(1.0, LengthUnit.FEET);
			assertTrue(q1.equals(q1));
		}

		// INCH TESTS (converted from UC2)
		@Test
		public void testQuantity_Inch_SameValue() {
			Length q1 = new Length(1.0, LengthUnit.INCHES);
			Length q2 = new Length(1.0, LengthUnit.INCHES);
			assertTrue(q1.equals(q2));
		}

		@Test
		public void testQuantity_Inch_DifferentValue() {
			Length q1 = new Length(1.0, LengthUnit.INCHES);
			Length q2 = new Length(2.0, LengthUnit.INCHES);
			assertFalse(q1.equals(q2));
		}

		@Test
		public void testQuantity_Inch_NullComparison() {
			Length q1 = new Length(1.0, LengthUnit.INCHES);
			assertFalse(q1.equals(null));
		}

		@Test
		public void testQuantity_Inch_DifferentClass() {
			Length q1 = new Length(1.0, LengthUnit.INCHES);
			String other = "NotQuantity";
			assertFalse(q1.equals(other));
		}

		@Test
		public void testQuantity_Inch_SameReference() {
			Length q1 = new Length(1.0, LengthUnit.INCHES);
			assertTrue(q1.equals(q1));
		}

		// test for UC3

		@Test
		public void testQuantity_SameValueSameUnit() {
			Length q1 = new Length(1.0, LengthUnit.FEET);
			Length q2 = new Length(1.0, LengthUnit.FEET);
			assertTrue(q1.equals(q2));
		}

		@Test
		public void testQuantity_DifferentValue() {
			Length q1 = new Length(1.0, LengthUnit.FEET);
			Length q2 = new Length(2.0, LengthUnit.FEET);
			assertFalse(q1.equals(q2));
		}

		@Test
		public void testQuantity_NullComparison() {
			Length q1 = new Length(1.0, LengthUnit.FEET);
			assertFalse(q1.equals(null));
		}

		@Test
		public void testQuantity_DifferentClass() {
			Length q1 = new Length(1.0, LengthUnit.FEET);
			String other = "NotQuantity";
			assertFalse(q1.equals(other));
		}

		@Test
		public void testQuantity_SameReference() {
			Length q1 = new Length(1.0, LengthUnit.FEET);
			assertTrue(q1.equals(q1));
		}

}