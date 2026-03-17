
package com.apps.quantitymeasurement;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.app.QuantityMeasurementApp;
import com.app.controller.QuantityMeasurementController;
import com.app.entity.QuantityDTO;
import com.app.exception.InvalidUnitMeasurementException;
import com.app.model.Quantity;
import com.app.unit.LengthUnit;
import com.app.unit.Temperature;
import com.app.unit.VolumneUnit;
import com.app.unit.WeightUnit;



public class TestQuantityMeasurementApp {
		Quantity<LengthUnit> len1;
		Quantity<LengthUnit> len2;
		
		Quantity<WeightUnit> w1;
		Quantity<WeightUnit> w2;
		Quantity<WeightUnit> val1;
		Quantity<WeightUnit> val2;
		
		Quantity<VolumneUnit> v1;
		Quantity<VolumneUnit> v2;
		
		private static final QuantityMeasurementController controllers = QuantityMeasurementApp.getInstance().controller;
		
		@Test
		public void testMeasurableInterfaceLengthUnitImplementation() {
			len1 = new Quantity<LengthUnit>(12.0,LengthUnit.INCHES);
			len2 = new Quantity<LengthUnit>(1.0, LengthUnit.FEET);
			
			assertTrue(len1.equals(len2));
			assertEquals(0.084,len1.getUnit().getConversionFactor(),0.001);
			assertEquals(12.0,len1.getUnit().convertFromBaseUnit(1.0));
			assertEquals(1.0,len1.getUnit().convertToBaseUnit(len1.getValue()));
		}
		
		@Test
		public void testMasurableInterfaceWeightUnitImplements() {
			w1 = new Quantity<WeightUnit>(1.0,WeightUnit.KG);
			w2 = new Quantity<WeightUnit>(1000.0,WeightUnit.GRAM);
			
			assertEquals(0.001,w2.getUnit().getConversionFactor());
			assertEquals(1000,w2.getUnit().convertFromBaseUnit(1.0));
			assertEquals(1.0,w2.getUnit().convertToBaseUnit(w2.getValue()));
		}
		
		@Test
		public void testGenericQuantityLengthOperationEquality() {
			len1 = new Quantity<LengthUnit>(12.0,LengthUnit.INCHES);
			len2 = new Quantity<LengthUnit>(1.0, LengthUnit.FEET);
			
			assertTrue(len1.equals(len2));
		}
		
		@Test
		public void testGenericQuantityWeightOperationEquality() {
			w1 = new Quantity<WeightUnit>(1.0,WeightUnit.KG);
			w2 = new Quantity<WeightUnit>(1000.0,WeightUnit.GRAM);
			
			assertTrue(w1.equals(w2));
		}
		
		@Test
		public void testGenericQuantityLengthOperationConversion() {
			len2 = new Quantity<LengthUnit>(1.0, LengthUnit.FEET);
			
			assertEquals(12.0, len2.convertTo(LengthUnit.INCHES).getValue());
		}
		
		@Test
		public void testGenericQuantityWeightOperationsConversion() {
			w1 = new Quantity<WeightUnit>(1.0, WeightUnit.KG);
			
			assertEquals(1000.0,w1.convertTo(WeightUnit.GRAM).getValue());
		}
		
		@Test
		public void testGenericQuantityLengthOperationAddition() {
			len1 = new Quantity<LengthUnit>(12.0,LengthUnit.INCHES);
			len2 = new Quantity<LengthUnit>(1.0, LengthUnit.FEET);
			
			assertEquals(2.0, len1.add(len2,LengthUnit.FEET).getValue());
		}
		
		@Test
		public void testGenericQuantityWeightOperationAddition() {
			w1 = new Quantity<WeightUnit>(1.0,WeightUnit.KG);
			w2 = new Quantity<WeightUnit>(1000.0,WeightUnit.GRAM);
			
			assertEquals(2.0,w1.add(w2,WeightUnit.KG).getValue());
		}
		
		@Test
		public void testGenericQuantityConstructorValidationNullUnit() {
			assertThrows(IllegalArgumentException.class,()->{
				w1 = new Quantity<>(1.0, null);
			});
		}
		
		@Test
		public void testGenericQuantityConstructorValidationInvalidValue() {
			assertThrows(IllegalArgumentException.class,()->{
				w1 = new Quantity<>(Double.NaN, WeightUnit.KG);
			});
		}
		
		  @Test
		    public void testFeetEquality()   {
		    	len1 = new Quantity<LengthUnit>(2.0, LengthUnit.FEET);
		    	len2 = new Quantity<LengthUnit>(2.0,LengthUnit.FEET);
		    	assertTrue(len1.equals(len2));
		    }
		    
		    @Test
		    public void testIncheEquality() throws InvalidUnitMeasurementException {
		    	len1 = new Quantity<LengthUnit>(2.0,LengthUnit.INCHES);
		    	len2 = new Quantity<LengthUnit>(2.0,LengthUnit.INCHES);
		    	assertTrue(len1.equals(len2));
		    }
		    
		    @Test
		    public void testFeetIncheComparision() throws InvalidUnitMeasurementException {
		    	len1 = new Quantity<LengthUnit>(2.0,LengthUnit.FEET);
		    	len2 = new Quantity<LengthUnit>(24.0,LengthUnit.INCHES);
		    	assertTrue(len1.compare(len2));
		    }
		    
		    @Test
		    public void testFeetInEquality() throws InvalidUnitMeasurementException {
		    	len1 = new Quantity<LengthUnit>(2.0,LengthUnit.FEET);
		    	len2 = new Quantity<LengthUnit>(24.0,LengthUnit.FEET);
		    	assertTrue(!len1.equals(len2));
		    }
		    
		    @Test
		    public void testIncheInEquality() throws InvalidUnitMeasurementException {
		    	len1 = new Quantity<LengthUnit>(2.0,LengthUnit.INCHES);
		    	len2 = new Quantity<LengthUnit>(24.0,LengthUnit.INCHES);
		    	assertTrue(!len1.equals(len2));
		    }
		   
		    @ParameterizedTest
		    @ValueSource(doubles= {1.0,4.0,5.0,6.0,5.0})
		    public void multipleFeetcomparison(double feet) throws InvalidUnitMeasurementException {
		    	len1 = new Quantity<LengthUnit>(feet,LengthUnit.FEET);
		    	len2 = new Quantity<LengthUnit>(feet*12,LengthUnit.INCHES);
		    	assertTrue(len1.compare(len2));
		    }
		    
		    @Test
		    public void testEqualityYardToYard() throws InvalidUnitMeasurementException {
		    	len1 = new Quantity<LengthUnit>(1.0,LengthUnit.YARD);
		    	len2 = new Quantity<LengthUnit>(1.0,LengthUnit.YARD);
		    	assertTrue(len1.equals(len2));
		    }
		    
		    @Test
		    public void testInEqualityYardToYard() throws InvalidUnitMeasurementException {
		    	len1 = new Quantity<LengthUnit>(1.0,LengthUnit.YARD);
		    	len2 = new Quantity<LengthUnit>(2.0,LengthUnit.YARD);
		    	assertTrue(!len1.equals(len2));
		    }
		    
		    @Test
		    public void testEqualityYardToFeetEquivalentValue() throws InvalidUnitMeasurementException {
		    	len1 = new Quantity<LengthUnit>(1.0,LengthUnit.YARD);
		    	len2 = new Quantity<LengthUnit>(3.0,LengthUnit.FEET);
		    	assertTrue(len1.equals(len2));
		    }
		    
		    @Test
		    public void testEqualityFeetToYardEquivalentValue() throws InvalidUnitMeasurementException {
		    	len1 = new Quantity<LengthUnit>(3.0,LengthUnit.FEET);
		    	len2 = new Quantity<LengthUnit>(1.0,LengthUnit.YARD);
		    	assertTrue(len1.equals(len2));
		    }
		    
		    @Test
		    public void testEqualityYardToInchesEquivalentValue() throws InvalidUnitMeasurementException {
		    	len1 = new Quantity<LengthUnit>(1.0,LengthUnit.YARD);
		    	len2 = new Quantity<LengthUnit>(36.0,LengthUnit.INCHES);
		    	assertTrue(len1.equals(len2));
		    }
		    
		    @Test
		    public void testEqualityInchesToYardEquivalentValue() throws InvalidUnitMeasurementException {
		    	len1 = new Quantity<LengthUnit>(36.0,LengthUnit.INCHES);
		    	len2 = new Quantity<LengthUnit>(1.0,LengthUnit.YARD);
		    	assertTrue(len1.equals(len2));
		    }
		    
		    @Test
		    public void testEqualityYardToFeetNonEquivalentValue() throws InvalidUnitMeasurementException {
		    	len1 = new Quantity<LengthUnit>(1.0,LengthUnit.YARD);
		    	len2 = new Quantity<LengthUnit>(2.0,LengthUnit.FEET);
		    	assertTrue(!len1.equals(len2));
		    }
		    
		    @Test
		    public void testEqualitycentimetersToInchesEquivalentValue() throws InvalidUnitMeasurementException {
		    	len1 = new Quantity<LengthUnit>(1.0,LengthUnit.CENTIMETRE);
		    	len2 = new Quantity<LengthUnit>(0.393701,LengthUnit.INCHES);
		    	assertTrue(len1.equals(len2));
		    }
		    
		    @Test
		    public void testEqualitycentimetersToFeetNonEquivalentValue() throws InvalidUnitMeasurementException {
		    	len1 = new Quantity<LengthUnit>(1.0,LengthUnit.CENTIMETRE);
		    	len2 = new Quantity<LengthUnit>(1.0,LengthUnit.FEET);
		    	assertTrue(!len1.equals(len2));
		    }
		    
		    @Test
		    public void testEqualityMultiUnitTransitiveProperty() throws InvalidUnitMeasurementException {
		    	len1 = new Quantity<LengthUnit>(1.0,LengthUnit.YARD);
		    	len2 = new Quantity<LengthUnit>(3.0,LengthUnit.FEET);
		    	Quantity<LengthUnit> len3 = new Quantity<LengthUnit>(36.0,LengthUnit.INCHES);
		    	assertTrue(len1.equals(len2)&&len2.equals(len3));
		    }
		    
		    @Test
		    public void testEqualityYardWithNullUnit() throws InvalidUnitMeasurementException {
		    	len1= new Quantity<LengthUnit>(1.0,LengthUnit.YARD);
		    	assertTrue(!len1.equals(null));
		    }
		    
		    @Test
		    public void testEqualityYardSameReference() throws InvalidUnitMeasurementException {
		    	len1= new Quantity<LengthUnit>(1.0,LengthUnit.YARD);
		    	len2 = len1;
		    	assertTrue(len1.equals(len2));
		    }
		    
		    @Test
		    public void testEqualityYardNullComparison() throws InvalidUnitMeasurementException {
		    	len1= new Quantity<LengthUnit>(1.0,LengthUnit.YARD);
		    	assertTrue(!len1.compare(null));
		    }
		    @Test
		    public void testEqualityCentimetersWithNullUnit() throws InvalidUnitMeasurementException{
		       assertThrows(IllegalArgumentException.class,()->{
		    	   len1 = new Quantity<LengthUnit>(1.0,null);
		       });
		    }
		    @Test
		    public void testEqualityCentimetersSameReference() throws InvalidUnitMeasurementException{
		    	len1 = new Quantity<LengthUnit>(1.0,LengthUnit.CENTIMETRE);
		    	len2 = len1;
		    	assertTrue(len1.equals(len2));
		    }
		  
		@Test
		   public void testEqualityAllUnitsComplexScenario()throws InvalidUnitMeasurementException{
			    len1 = new Quantity<LengthUnit>(2.0,LengthUnit.YARD);
		    	len2 = new Quantity<LengthUnit>(6.0,LengthUnit.FEET);
		    	Quantity<LengthUnit> len3 = new Quantity<LengthUnit>(72.0,LengthUnit.INCHES);
		    	assertTrue(len1.equals(len2)&&len1.equals(len3));
		   }
		
		
	    
	    //Test Methods For addition logic 
	    
	    @Test
	    public void testAdditionSameUnitFeetPlusFeet() throws InvalidUnitMeasurementException{
	    	len1 = new Quantity<LengthUnit>(1.0,LengthUnit.FEET);
	    	len2 = new Quantity<LengthUnit>(2.0,LengthUnit.FEET);
	    	assertEquals(3.0,len1.add(len2).getValue());
	    }
	    
	    @Test
	    public void testAdditionSameUnitInchPlusInch() throws InvalidUnitMeasurementException{
	    	len1 = new Quantity<LengthUnit>(6.0,LengthUnit.INCHES);
	    	len2 = new Quantity<LengthUnit>(6.0,LengthUnit.INCHES);
	    	assertEquals(12.0,len1.add(len2).getValue());
	    }
	    
	    @Test
	    public void testAdditionCrossUnitFeetPlusInches() throws InvalidUnitMeasurementException {
	    	len1 = new Quantity<LengthUnit>(1.0,LengthUnit.FEET);
	    	len2 = new Quantity<LengthUnit>(12.0,LengthUnit.INCHES);
	    	assertEquals(2.0,len1.add(len2).getValue());
	    }
	    @Test
	    public void testAdditionCrossUnitInchePlusFeet() throws InvalidUnitMeasurementException{
	    	len1 = new Quantity<LengthUnit>(12.0,LengthUnit.INCHES);
	    	len2 = new Quantity<LengthUnit>(1.0,LengthUnit.FEET);
	    	assertEquals(24.0,len1.add(len2).getValue());
	    }
	    
	    @Test
	    public void testAdditionCrossUnitYardPlusFeet() throws InvalidUnitMeasurementException{
	    	len1 = new Quantity<LengthUnit>(1.0,LengthUnit.YARD);
	    	len2 = new Quantity<LengthUnit>(3.0,LengthUnit.FEET);
	    	assertEquals(2.0,len1.add(len2).getValue());
	    }
	    
	    @Test
	    public void testAdditionCrossUnitCentimeterPlusInch() throws InvalidUnitMeasurementException{
	    	len1 = new Quantity<LengthUnit>(2.54,LengthUnit.CENTIMETRE);
	    	len2 = new Quantity<LengthUnit>(1.0,LengthUnit.INCHES);
	    	assertEquals(5.08,len1.add(len2).getValue(),0.0001);
	    }
	    
	    @Test
	    public void testAdditionCommutativity() throws InvalidUnitMeasurementException{
	    	len1 = new Quantity<LengthUnit>(2.54,LengthUnit.CENTIMETRE);
	    	len2 = new Quantity<LengthUnit>(1.0,LengthUnit.INCHES);
	    	Quantity<LengthUnit> l1 = len1.add(len2);
	    	Quantity<LengthUnit> l2 = len2.add(len1);
	    	l2 = l2.convertTo(l1.getUnit());
	    	assertTrue(l1.equals(l2));
	    }
	    
	    @Test
	    public void testadditionWithZero() throws InvalidUnitMeasurementException {
	    	len1 = new Quantity<LengthUnit>(5.0,LengthUnit.FEET);
	    	len2 = new Quantity<LengthUnit>(0.0,LengthUnit.INCHES);
	    	assertEquals(5.0,len1.add(len2).getValue());
	    }
	    
	    @Test
	    public void testAdditionNegativeValues() throws InvalidUnitMeasurementException{
	    	len1 = new Quantity<LengthUnit>(5.0,LengthUnit.FEET);
	    	len2 = new Quantity<LengthUnit>(-3.0,LengthUnit.FEET);
	    	assertEquals(2.0,len1.add(len2).getValue());
	    }
	    
	   
	    
	    @Test
	    public void testAdditionLargeValues() throws InvalidUnitMeasurementException{
	    	len1 = new Quantity<LengthUnit>(1e-6,LengthUnit.FEET);
	    	len2 = new Quantity<LengthUnit>(1e-6,LengthUnit.FEET);
	    	assertEquals(2e-6,len1.add(len2).getValue());
	    }
	    
	    @Test
	    public void testAdditionSmallValues() throws InvalidUnitMeasurementException{
	    	len1 = new Quantity<LengthUnit>(0.001,LengthUnit.FEET);
	    	len2 = new Quantity<LengthUnit>(0.002,LengthUnit.FEET);
	    	assertEquals(0.003,len1.add(len2).getValue(),0.0001);
	    }
	    
//	    Addition of two unit two specific unit 
	    
	    @Test
	    public void testAdditionExplicitTargetUnitFeet() throws InvalidUnitMeasurementException {
	    	len1 = new Quantity<LengthUnit>(1.0,LengthUnit.FEET);
	    	len2 = new Quantity<LengthUnit>(12.0,LengthUnit.INCHES);
	    	assertEquals(2.0,len1.add(len2,LengthUnit.FEET).getValue());
	    }
	    
	    @Test
	    public void testAdditionExplicitTargetUnitInches() throws InvalidUnitMeasurementException{
	    	len1 = new Quantity<LengthUnit>(1.0,LengthUnit.FEET);
	    	len2 = new Quantity<LengthUnit>(12.0,LengthUnit.INCHES);
	    	System.out.println(len1.add(len2,LengthUnit.INCHES));
	    	assertEquals(24.0,len1.add(len2,LengthUnit.INCHES).getValue());
	    }
	    
	    @Test
	    public void testAdditionExplicitTargetUnitYards() throws InvalidUnitMeasurementException{
	    	len1 = new Quantity<LengthUnit>(1.0,LengthUnit.FEET);
	    	len2 = new Quantity<LengthUnit>(12.0,LengthUnit.INCHES);
	    	assertEquals(0.667,len1.add(len2,LengthUnit.YARD).getValue(),0.001);
	    }
	    
	    @Test
	    public void testAdditionExplicitTargetUnitCentimeter() throws InvalidUnitMeasurementException{
	    	len1 = new Quantity<LengthUnit>(1.0,LengthUnit.INCHES);
	    	len2 = new Quantity<LengthUnit>(1.0,LengthUnit.INCHES);
	    	assertEquals(5.08,len1.add(len2,LengthUnit.CENTIMETRE).getValue(),0.001);
	    }
	     @Test
	     public void testAdditionExplicitTargetUnitSameAsFirstOperand() throws InvalidUnitMeasurementException {
	    	 len1 = new Quantity<LengthUnit>(2.0,LengthUnit.YARD);
	     	len2 = new Quantity<LengthUnit>(3.0,LengthUnit.FEET);
	     	assertEquals(3.0,len1.add(len2,LengthUnit.YARD).getValue());
	     }
	     
	     @Test
	     public void testAdditionExplicitTargetUnitSameAsSecondOprand() throws InvalidUnitMeasurementException{
	    	 len1 = new Quantity<LengthUnit>(2.0,LengthUnit.YARD);
	      	len2 = new Quantity<LengthUnit>(3.0,LengthUnit.FEET);
	      	assertEquals(9.0,len1.add(len2,LengthUnit.FEET).getValue());
	     }
	     
	     @Test
	     public void testAdditionTargetUnitCommutativity() throws InvalidUnitMeasurementException{
	    	 len1 = new Quantity<LengthUnit>(1.0,LengthUnit.FEET);
	      	len2 = new Quantity<LengthUnit>(12.0,LengthUnit.INCHES);
	      	Quantity<LengthUnit> temp1 = len1.add(len2,LengthUnit.YARD);
	        len1 = new Quantity<LengthUnit>(12.0,LengthUnit.INCHES);
	     	len2 = new Quantity<LengthUnit>(1.0,LengthUnit.FEET);
	     	Quantity<LengthUnit> temp2 = len1.add(len2,LengthUnit.YARD);
	     	assertTrue(temp1.equals(temp2));
	     	
	     }
	     
	     @Test
	     public void testAdditionTargetUnitWithZero() throws InvalidUnitMeasurementException{
	    	 len1 = new Quantity<LengthUnit>(5.0,LengthUnit.FEET);
	      	len2 = new Quantity<LengthUnit>(0.0,LengthUnit.INCHES);
	      	assertEquals(1.667,len1.add(len2,LengthUnit.YARD).getValue(),0.001);
	     }
	     
	     @Test
	     public void testAdditionTargetUnitNagativeValues() throws InvalidUnitMeasurementException{
	    	 len1 = new Quantity<LengthUnit>(5.0,LengthUnit.FEET);
	      	len2 = new Quantity<LengthUnit>(-2.0,LengthUnit.FEET);
	      	assertEquals(36.0,len1.add(len2,LengthUnit.INCHES).getValue());
	     }
	     
	     @Test
	     public void testAdditionTargetUnitNullTargetUnit() throws InvalidUnitMeasurementException{
	    	 len1 = new Quantity<LengthUnit>(2.0,LengthUnit.YARD);
	      	len2 = new Quantity<LengthUnit>(3.0,LengthUnit.FEET);
	      	assertThrows(Exception.class,()->{
	      		len1.add(len2,null);
	      	});
	     }
	     
	     @Test
	     public void testAdditionTargetUnitLargeToSmall() throws InvalidUnitMeasurementException{
	    	 len1 = new Quantity<LengthUnit>(1000.0,LengthUnit.FEET);
	      	len2 = new Quantity<LengthUnit>(500.0,LengthUnit.FEET);
	      	assertEquals(18000.0,len1.add(len2,LengthUnit.INCHES).getValue());
	     }
	     
	     @Test
	     public void testAdditionTargetSmallToLarge() throws InvalidUnitMeasurementException{
	    	 len1 = new Quantity<LengthUnit>(12.0,LengthUnit.INCHES);
	      	len2 = new Quantity<LengthUnit>(12.0,LengthUnit.INCHES);
	      	assertEquals(0.667,len1.add(len2,LengthUnit.YARD).getValue(),0.001);
	     }
	     @Test
	     public void testAddition_ExplicitTargetUnit_PrecisionTolerance() throws InvalidUnitMeasurementException {
	         Quantity<LengthUnit> l1 = new Quantity<LengthUnit>(1.0, LengthUnit.FEET);
	         Quantity<LengthUnit> l2 = new Quantity<LengthUnit>(0.1, LengthUnit.FEET);
	         Quantity<LengthUnit> result = l1.add(l2, LengthUnit.INCHES);
	         assertEquals(13.2, result.getValue(),0.001);
	     } 
	     
	     //Refactoring Unit Enum to Standalone with Conversion Responsibility
	     
	     @Test
	     public void testLengthUnitEnumFeetConstant() throws InvalidUnitMeasurementException{
	    	 assertEquals(1.0,LengthUnit.FEET.getConversionFactor());
	     }
	     
	     @Test
	     public void testLengthUnitEnumInchesConstant() throws InvalidUnitMeasurementException{
	    	 assertEquals(0.0833, LengthUnit.INCHES.getConversionFactor(),0.0001);
	     }
	    
	     @Test
	     public void testlengthUnitEnumYardsConstant() throws InvalidUnitMeasurementException{
	    	 assertEquals(3.0,LengthUnit.YARD.getConversionFactor());
	     }
	     
	     @Test
	     public void testLengthUnitEnumCentimeterConstant() throws InvalidUnitMeasurementException{
	    	 assertEquals(0.0328,LengthUnit.CENTIMETRE.getConversionFactor(),0.0001);
	     }
	     
	     @Test
	     public void testConvertToBaseUnitFeetToFeet() throws InvalidUnitMeasurementException{
	    	 assertEquals(5.0,LengthUnit.FEET.convertToBaseUnit(5.0));
	     }
	     
	     @Test
	     public void testConvertToBaseUnitIncheToFeet() throws InvalidUnitMeasurementException{
	    	 assertEquals(1.0,LengthUnit.INCHES.convertToBaseUnit(12.0));
	     }
	     
	     @Test
	     public void testConvertToBaseUnitYardsToFeet() throws InvalidUnitMeasurementException{
	    	 assertEquals(3.0,LengthUnit.YARD.convertToBaseUnit(1.0));
	     }
	     
	     @Test
	     public void testConvertToBaseUnitCentimeterToFeet() throws InvalidUnitMeasurementException{
	    	 assertEquals(1.0,LengthUnit.CENTIMETRE.convertToBaseUnit(30.48),0.01);
	     }
	     
	     //Weight Unit 
	     
	     @Test
	     public void testEqualityKilogramToKilogramSameValue() {
	    	 w1 = new Quantity<WeightUnit>(1.0,WeightUnit.KG);
	    	 w2 = new Quantity<WeightUnit>(1.0,WeightUnit.KG);
	    	 assertTrue(w1.equals(w2));
	     }
	     
	     @Test
	     public void testEqualityKgToKgDifferentValue() {
	    	 val1 = new Quantity<WeightUnit>(1.0,WeightUnit.KG);
	    	 val2 = new Quantity<WeightUnit>(2.0,WeightUnit.KG);
	    	 assertFalse(val1.equals(val2));
	     }
	     
	     @Test
	     public void testEqualityKgToGramEquivalentValue() {
	    	 val1 = new Quantity<WeightUnit>(1.0,WeightUnit.KG);
	    	 val2 = new Quantity<WeightUnit>(1000.0,WeightUnit.GRAM);
	    	 assertTrue(val1.equals(val2));
	     }
	     
	     @Test
	     public void testEqualityGramToKilogramEquivalentValue() {
	    	 val1 = new Quantity<WeightUnit>(1000.0,WeightUnit.GRAM);
	    	 val2 = new Quantity<WeightUnit>(1.0,WeightUnit.KG);
	    	 assertTrue(val1.equals(val2));
	     }
	     
	     @Test
	     public void testEqualityWeightVsLengthIncompatible() throws InvalidUnitMeasurementException {
	    	 val1 = new Quantity<WeightUnit>(1.0,WeightUnit.KG);
	    	 len2 =  new Quantity<LengthUnit>(1.0,LengthUnit.FEET);
	    	 assertFalse(val1.equals(len2));
	     }
	     @Test
	     public void testEqualityNullComparison() {
	    	 val1 = new Quantity<WeightUnit>(1.0,WeightUnit.KG);
	    	
	    	 assertFalse(val1.equals(null));
	     }
	     
	     @Test
	     public void testEqualitySameRefference() {
	    	 val1 = new Quantity<WeightUnit>(1.0,WeightUnit.KG);
	    	 val2 = val1;
	    	 assertTrue(val1.equals(val2));
	     }
	     
	     @Test
	     public void testEqualityNullUnit() {
	    	assertThrows(IllegalArgumentException.class, ()->{
	    		val1 = new Quantity<WeightUnit>(1.0,WeightUnit.KG);
	       	 val2 = new Quantity<WeightUnit>(1.0,null);
	    	});
	    	 
	     }
	     
	     @Test
	     public void testEqualityTransitiveProperty() {
	    	 val1 = new Quantity<WeightUnit>(1.0,WeightUnit.KG);
	    	 val2 = new Quantity<WeightUnit>(1000.0,WeightUnit.GRAM);
	    	 assertTrue(val1.equals(val2)&&val2.equals(val1));
	     }
	     
	     @Test
	     public void testEqualityZeroValue() {
	    	 val1 = new Quantity<WeightUnit>(0.0,WeightUnit.KG);
	    	 val2 = new Quantity<WeightUnit>(0.0,WeightUnit.GRAM);
	    	 assertTrue(val1.equals(val2));
	     }
	     
	     @Test
	     public void testEqualityNegativeValue() {
	    	 val1 = new Quantity<WeightUnit>(-1.0,WeightUnit.KG);
	    	 val2 = new Quantity<WeightUnit>(-1000.0,WeightUnit.GRAM);
	    	 assertTrue(val1.equals(val2));
	     }
	     
	     @Test
	     public void testEqualityLargeWeightValue() {
	    	 val1 = new Quantity<WeightUnit>(1000000.0,WeightUnit.GRAM);
	    	 val2 = new Quantity<WeightUnit>(1000.0,WeightUnit.KG);
	    	 assertTrue(val1.equals(val2));
	     }
	     
	     @Test
	     public void testEqualitySmallWeightValue() {
	    	 val1 = new Quantity<WeightUnit>(0.001,WeightUnit.KG);
	    	 val2 = new Quantity<WeightUnit>(1.0,WeightUnit.GRAM);
	    	 assertTrue(val1.equals(val2));
	     }
	     
	     @Test
	     public void testConversionPoundToKilogram() {
	    	assertEquals(1.0,new Quantity<WeightUnit>(2.20462, WeightUnit.POUND).convertTo(WeightUnit.KG).getValue(),0.0001); 
	     }
	     
	     @Test
	     public void testConversionKgToPound() {
	    	 val1 = new Quantity<WeightUnit>(1.0,WeightUnit.KG);
	    	 assertEquals(2.20462,val1.convertTo(WeightUnit.POUND).getValue(),0.00001);
	     }
	     
	     @Test
	     public void testConversionSameUnit() {
	    	 val1 = new Quantity<WeightUnit>(5.0,WeightUnit.KG);
	    	 assertEquals(5.0,val1.convertTo(WeightUnit.KG).getValue());
	     }
	     
	     @Test
	     public void testConversionZeroUnit() {
	    	 val1 = new Quantity<WeightUnit>(0.0,WeightUnit.KG);
	    	 assertEquals(0.0,val1.convertTo(WeightUnit.GRAM).getValue());
	     }
	     
	     @Test
	     public void testConversionNegativeValue1() {
	    	 val1 = new Quantity<WeightUnit>(-1.0,WeightUnit.KG);
	    	 assertEquals(-1000.0,val1.convertTo(WeightUnit.GRAM).getValue());
	     }
	     
	     @Test
	     public void testConversionRoundTrip() {
	    	 val1 = new Quantity<WeightUnit>(1.5,WeightUnit.KG);
	    	 assertEquals(1.5,val1.convertTo(WeightUnit.GRAM).convertTo(WeightUnit.KG).getValue(),0.001);
	     }
	     
	     @Test
	     public void testAdditionSameUnitKgPlusKg() {
	    	 val1 = new Quantity<WeightUnit>(5.0,WeightUnit.KG);
	    	 val2 = new Quantity<WeightUnit>(5.0, WeightUnit.KG);
	    	 
	    	 assertEquals(10.0,val1.add(val2).getValue());
	     }
	     
	     @Test
	     public void testAdditionCrossUnitKgToGram() {
	    	 val1 = new Quantity<WeightUnit>(5.0,WeightUnit.KG);
	    	 val2 = new Quantity<WeightUnit>(5000.0, WeightUnit.GRAM);
	    	 
	    	 assertEquals(10.0,val1.add(val2).getValue());
	     }
	     
//	     Volume Unit
	     @Test
	     public void testEqualityLitreToLitreSameValue() {
	    	 v1 = new Quantity<VolumneUnit>(1.0,VolumneUnit.LITRE);
	    	 v2 = new Quantity<VolumneUnit>(1.0,VolumneUnit.LITRE);
	    	 assertTrue(v1.equals(v2));
	     }
	     
	     @Test
	     public void testEqualityLitreToLitreDifferentValue() {
	    	 v1 = new Quantity<VolumneUnit>(1.0,VolumneUnit.LITRE);
	    	 v2 = new Quantity<VolumneUnit>(2.0,VolumneUnit.LITRE);
	    	 assertFalse(v1.equals(v2));
	     }
	     
	     @Test
	     public void testEquality_LitreToMillilitre_EquivalentValue() {
	    	 v1 = new Quantity<VolumneUnit>(1.0,VolumneUnit.LITRE);
	    	 v2 = new Quantity<VolumneUnit>(1000.0,VolumneUnit.MILLILITRE);
	    	 assertTrue(v1.equals(v2));
	     }
	     
	     @Test
	     public void testEquality_LitreToGallon_EquivalentValue() {
	    	 v1 = new Quantity<VolumneUnit>(1.0,VolumneUnit.LITRE);
	    	 v2 = new Quantity<VolumneUnit>(0.264172,VolumneUnit.GALLON);
	    	 assertTrue(v1.equals(v2));
	     }
	     
	     @Test
	     public void testEquality_GallonToLitre_EquivalentValue() {
	    	 v1 = new Quantity<VolumneUnit>(3.78541,VolumneUnit.LITRE);
	    	 v2 = new Quantity<VolumneUnit>(1.0,VolumneUnit.GALLON);
	    	 assertTrue(v2.equals(v1));
	     }
	     
	     @Test
	     public void testEquality_NullComparison() {
	    	 assertFalse(new Quantity<VolumneUnit>(1.0,VolumneUnit.LITRE).equals(null));
	     }
	     
	     @Test
	     public void testEquality_SameReference() {
	    	 v1 = new Quantity<VolumneUnit>(1.0,VolumneUnit.LITRE);
	    	 v2 = v1;
	    	 assertTrue(v1.equals(v2));
	     }
	     
	     @Test
	     public void testEquality_NullUnit() {
	    	 assertThrows(IllegalArgumentException.class,()->{
	    		 v1 = new Quantity<VolumneUnit>(1.0,null); 
	    	 });
	     }
	     
	     @Test
	     public void testEquality_ZeroValue() {
	    	 assertTrue(new Quantity<>(0.0,VolumneUnit.LITRE).equals(new Quantity<>(0.0,VolumneUnit.MILLILITRE)));
	     }
	     
	     @Test
	     public void testEquality_NegativeVolume() {
	    	 assertTrue(new Quantity<>(-1.0,VolumneUnit.LITRE).equals(new Quantity<>(-1000.0,VolumneUnit.MILLILITRE)));
	     }
	     @Test
	     public void testConversion_LitreToMillilitre() {
	    	 assertEquals(1000.0,new Quantity<>(1.0,VolumneUnit.LITRE).convertTo(VolumneUnit.MILLILITRE).getValue());
	    	 
	     }
	     
	     @Test
	     public void testConversion_GallonToLitre() {
	    	 assertEquals(3.78541, new Quantity<>(1.0,VolumneUnit.GALLON).convertTo(VolumneUnit.LITRE).getValue(),0.00001);
	     }
	     
	     @Test
	     public void testVolumeUnitEnum_GallonConstant() {
	    	 assertEquals(3.78541, VolumneUnit.GALLON.getConversionFactor());
	     }
	     
	     @Test
	     public void testConvertToBaseUnit_LitreToLitre() {
	    	 assertEquals(5.0,VolumneUnit.LITRE.convertToBaseUnit(5.0));
	     }
	     
	     @Test
	     public void testConvertToBaseUnit_MillilitreToLitre() {
	    	 assertEquals(1.0,VolumneUnit.MILLILITRE.convertToBaseUnit(1000.0));
	     }
	     
	     @Test
	     public void testConvertFromBaseUnit_LitreToLitre() {
	    	 assertEquals(2.0, VolumneUnit.LITRE.convertFromBaseUnit(2.0));
	     }
	     
//	     Arithmetic Operation 
	     
	     @Test
	     public void testSubtraction_SameUnit_FeetMinusFeet() {
	    	 assertEquals(5.0,new Quantity<>(10.0,LengthUnit.FEET).subtract(new Quantity<LengthUnit>(5.0, LengthUnit.FEET)).getValue());
	     }
	     
	     @Test
	     public void testSubtraction_SameUnit_LitreMinusLitre() {
	    	 assertEquals(7.0,new Quantity<VolumneUnit>(10.0,VolumneUnit.LITRE).subtract(new Quantity<VolumneUnit>(3.0,VolumneUnit.LITRE)).getValue());
	     }
	     
	     @Test
	     public void testSubtraction_CrossUnit_FeetMinusInches() {
	    	 assertEquals(9.5,new Quantity<LengthUnit>(10.0,LengthUnit.FEET).subtract(new Quantity<LengthUnit>(6.0,LengthUnit.INCHES)).getValue());
	     }
	     
	     @Test
	     public void testSubtraction_ExplicitTargetUnit_Feet() {
	    	 assertEquals(114.0,new Quantity<>(10.0,LengthUnit.FEET).subtract(new Quantity<LengthUnit>(6.0,LengthUnit.INCHES),LengthUnit.INCHES).getValue());
	     }
	     
	     @Test
	     public void testSubtraction_ResultingInNegative() {
	    	 assertEquals(-5.0,new Quantity<LengthUnit>(5.0,LengthUnit.FEET).subtract(new Quantity<LengthUnit>(10.0,LengthUnit.FEET)).getValue());
	     }
	     
	     @Test
	     public void testSubtraction_ResultingInZero() {
	    	 assertEquals(0.0, new Quantity<LengthUnit>(10.0,LengthUnit.FEET).subtract(new Quantity<LengthUnit>(120.0,LengthUnit.INCHES)).getValue());
	     }
	     
	     @Test
	     public void testSubtraction_WithNegativeValues() {
	    	 assertEquals(7.0,new Quantity<>(5.0, LengthUnit.FEET).subtract(new Quantity<LengthUnit>(-2.0,LengthUnit.FEET)).getValue());
	     }
	     
	     @Test
	     public void testSubtraction_NullOperand() {
	    	 assertThrows(Exception.class,()->{
	    		 new Quantity<>(10.0, LengthUnit.FEET).subtract(null);
	    	 });
	     }
	     
	     @Test
	     public void testDivision_SameUnit_FeetDividedByFeet() {
	    	 assertEquals(5.0,new Quantity<>(10.0,LengthUnit.FEET).division(new Quantity<LengthUnit>(2.0,LengthUnit.FEET)).getValue());
	     }
	     
	     @Test
	     public void testDivision_SameUnit_LitreDividedByLitre() {
	    	 assertEquals(2.0,new Quantity<>(10.0,VolumneUnit.LITRE).division(new Quantity<VolumneUnit>(5.0,VolumneUnit.LITRE)).getValue());
	     }
	     
	     @Test
	     public void testDivision_CrossUnit_FeetDividedByInches() {
	    	
	    	 assertEquals(1.0,new Quantity<>(24.0,LengthUnit.INCHES).division(new Quantity<LengthUnit>(2.0,LengthUnit.FEET)).getValue());
	     }
	     
	     @Test
	     public void testDivision_RatioGreaterThanOne() {
	    	 assertTrue(new Quantity<>(10.0,LengthUnit.FEET).division(new Quantity<LengthUnit>(2.0,LengthUnit.FEET)).getValue()>1.0);
	     }
	     
	     @Test
	     public void testDivision_RatioLessThanOne() {
	    	 assertTrue(new Quantity<>(5.0,LengthUnit.FEET).division(new Quantity<LengthUnit>(10.0,LengthUnit.FEET)).getValue()<1.0);
	     }
	     
	     @Test 
	     public void testDivision_ByZero() {
	    	 assertThrows(ArithmeticException.class,()->{
	    		new Quantity<>(10.0,LengthUnit.FEET).division(new Quantity<LengthUnit>(0.0,LengthUnit.FEET)) ;
	    	 });
	     }
	     
	     //Centralized Arithemetic Operation 
	     @Test
	     public void testArithmeticOperation_Add_EnumComputation() {
	    	 assertEquals(9.0,new Quantity<LengthUnit>(5.0,LengthUnit.FEET).add(new Quantity<LengthUnit>(4.0,LengthUnit.FEET)).getValue());
	     }
	     
	     @Test
	     public void testArithmeticOperation_Subtract_EnumComputation() {
	    	 assertEquals(5.0,new Quantity<LengthUnit>(10.0,LengthUnit.FEET).subtract(new Quantity<LengthUnit>(5.0,LengthUnit.FEET)).getValue()); 
	     }
	     
	     @Test
	     public void testArithmeticOperation_Divide_EnumComputation() {
	    	 assertEquals(2.0,new Quantity<LengthUnit>(10.0,LengthUnit.FEET).division(new Quantity<LengthUnit>(5.0,LengthUnit.FEET)).getValue());
	     }
	     
	     @Test
	     public void testArithmeticOperation_DivideByZero_EnumThrows() {
	    	assertThrows(ArithmeticException.class,()->{
	    		new Quantity<LengthUnit>(10.0,LengthUnit.FEET).division(new Quantity<LengthUnit>(0.0,LengthUnit.FEET));
	    	});
	     }
	     
	     //Temperature 
	     @Test
	     public void testTemperatureEquality_CelsiusToCelsius_SameValue() {
	    	 assertTrue(new Quantity<>(0.0,Temperature.CELSIUS).equals(new Quantity<>(0.0, Temperature.CELSIUS)));
	     }
	     
	     @Test
	     public void testTemperatureEquality_FahrenheitToFahrenheit_SameValue() {
	    	 assertTrue(new Quantity<>(32.0,Temperature.FAHRENHEIT).equals(new Quantity<>(32.0,Temperature.FAHRENHEIT)));
	     }
	     
	     @Test
	     public void testTemperatureEquality_CelsiusToFahrenheit_SameValue() {
	    	 assertTrue(new Quantity<>(100.0,Temperature.CELSIUS).equals(new Quantity<>(212.0,Temperature.FAHRENHEIT)));
	     }
	     
	     @Test
	     public void lengthFeetEqualsInches() {
	    	 QuantityDTO q1 = new QuantityDTO(2,"FEET","LENGTH");
	    	 QuantityDTO q2 = new QuantityDTO(24,"INCHES","LENGTH");
	    	 
	    	 assertTrue(controllers.performComparison(q1, q2));
	     }
	     
	     @Test
	     public void lengthYardsEqualsFeet() {
	    	 QuantityDTO q1 = new QuantityDTO(1.0,"YARD","LENGTH");
	    	 QuantityDTO q2 = new QuantityDTO(3.0,"FEET","LENGTH");
	    	 
	    	 assertTrue(controllers.performComparison(q1, q2));
	     }
	     
	     @Test
	     public void weightKilogramEqualsGrams() {
	    	 QuantityDTO q1 = new QuantityDTO(1,"KG","WEIGHT");
	    	 QuantityDTO q2 = new QuantityDTO(1000,"GRAM","WEIGHT");
	    	 
	    	 assertTrue(controllers.performComparison(q1, q2));
	     }
	     
	     @Test
	     public void convertLengthFeetToInches() {
	    	 QuantityDTO q1 = new QuantityDTO(2.0,"FEET","LENGTH");
	    	 QuantityDTO q2 = new QuantityDTO(0.0,"INCHES","LENGTH");
	    	 
	    	 assertEquals(24.0,controllers.performConversion(q1, q2).getValue());
	     }
	     
	     @Test
	     public void addLengthFeetAndInches() {
	    	 QuantityDTO q1 = new QuantityDTO(2.0,"FEET","LENGTH");
	    	 QuantityDTO q2 = new QuantityDTO(12.0,"INCHES","LENGTH");
	    	 
	    	 assertEquals(3.0,controllers.performAddition(q1, q2).getValue());
	     }
	    @Test
	    public void UnitMisMatchFeetAndGram() {
	    	 QuantityDTO q1 = new QuantityDTO(2.0,"FEET","LENGTH");
	    	 QuantityDTO q2 = new QuantityDTO(12.0,"GRAM","WEIGHT");
	    	 
	    	 assertThrows(IllegalArgumentException.class,()->{
	    		 controllers.performAddition(q1, q2);
	    	 });
	    }
}