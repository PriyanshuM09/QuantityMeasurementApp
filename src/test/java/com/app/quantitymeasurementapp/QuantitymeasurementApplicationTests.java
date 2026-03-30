package com.app.quantitymeasurementapp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.app.quantitymeasurementapp.controller.QuantityMeasurementController;
import com.app.quantitymeasurementapp.dto.QuantityDTO;
import com.app.quantitymeasurementapp.dto.QuantityInputDTO;
import com.app.quantitymeasurementapp.dto.QuantityMeasurementDTO;
import com.app.quantitymeasurementapp.dto.QuantityModel;
import com.app.quantitymeasurementapp.exception.CategoryMismatchException;
import com.app.quantitymeasurementapp.exception.QuantityMeasurementException;
import com.app.quantitymeasurementapp.unit.IMeasurable;
import com.app.quantitymeasurementapp.unit.LengthUnit;
import com.app.quantitymeasurementapp.unit.WeightUnit;

@SpringBootTest
class QuantityMeasurementApplicationTests {
	@Autowired
	private QuantityMeasurementController controller;
	
	//here start
	@Test
    public void testQuantityEntity_SingleOperandConstruction() {
        // Verifies correct storage using the Enum-based constructor
        QuantityDTO quantity = new QuantityDTO(1.0, "FEET", "LengthUnit");
        
        assertEquals(1.0, quantity.getValue());
        assertEquals("FEET", quantity.getUnit());
        assertEquals("LengthUnit", quantity.getMeasurementType());
    }
	
    @Test
    void testQuantityDTO_Constructor_WithIMeasurable() {
        QuantityDTO dto = new QuantityDTO(1.0,"FEET","LengthUnit");

        assertEquals(1.0, dto.getValue());
        assertEquals("FEET", dto.getUnit());
        assertEquals("LengthUnit", dto.getMeasurementType());
    }

    @Test
    void testQuantityDTO_Constructor_WithStrings() {
        QuantityDTO dto = new QuantityDTO(2.0, "GRAM", "WeightUnit");

        assertEquals(2.0, dto.getValue());
        assertEquals("GRAM", dto.getUnit());
        assertEquals("WeightUnit", dto.getMeasurementType());
    }

    @Test
    void testQuantityDTO_ToString() {
        QuantityDTO dto = new QuantityDTO(1.0, "LITRE", "VolumeUnit");
        String text = dto.toString();

        assertTrue(text.contains("1.0"));
        assertTrue(text.contains("LITRE"));
        assertTrue(text.contains("VolumeUnit"));
    }

    // ----------------------------------------------------
    // MODEL TESTS
    // ----------------------------------------------------

    @Test
    void testQuantityModel_Construction() {
        QuantityModel<IMeasurable> model = new QuantityModel<>(5.0, LengthUnit.INCHES);

        assertEquals(5.0, model.getValue());
        assertEquals(LengthUnit.INCHES, model.getUnit());
    }

    @Test
    void testQuantityModel_ToString() {
        QuantityModel<IMeasurable> model = new QuantityModel<>(10.0, WeightUnit.KILOGRAM);
        String text = model.toString();

        assertTrue(text.contains("10.0"));
        assertTrue(text.contains("KILOGRAM"));
    }

    // ----------------------------------------------------
    // SERVICE - COMPARE
    // ----------------------------------------------------

    @Test
    void testService_CompareEquality_SameUnit_Success() {
        QuantityInputDTO qt = new QuantityInputDTO(new QuantityDTO(1.0, "FEET", "LengthUnit"), new QuantityDTO(1.0, "FEET", "LengthUnit"), null);
        boolean result = (controller.performComparison(qt)).getBody().isError();
        assertFalse(result);
    }

    @Test
    void testService_CompareEquality_DifferentUnit_Success() {
        QuantityInputDTO qt = new QuantityInputDTO(new QuantityDTO(1.0, "FEET","LengthUnit"), new QuantityDTO(12.0, "INCHES","LengthUnit"), null);
        boolean result = controller.performComparison(qt).getBody().isError();

        assertFalse(result);
    }

    @Test
    void testService_CompareEquality_CrossCategory_Error() {
        QuantityInputDTO qt = new QuantityInputDTO(new QuantityDTO(1.0, "FEET", "LengthUnit"), new QuantityDTO(1.0, "LITRE", "VolumeUnit"),null);
        assertThrows(CategoryMismatchException.class, ()->{
        		controller.performComparison(qt);
        });
    }

    // ----------------------------------------------------
    // SERVICE - CONVERT
    // ----------------------------------------------------

    @Test
    void testService_Convert_Success() {
        QuantityInputDTO qt = new QuantityInputDTO(new QuantityDTO(1.0, "FEET","LengthUnit"), new QuantityDTO(0.0, "INCHES","LengthUnit"), null);
        ResponseEntity<QuantityMeasurementDTO> result = controller.performConversion(qt);

        assertEquals(12.0, result.getBody().resultValue, 0.01);
        assertEquals("FEET", result.getBody().resultUnit);
        assertEquals("LengthUnit", result.getBody().resultMeasurementType);
    }

    // ----------------------------------------------------
    // SERVICE - ADD
    // ----------------------------------------------------


    @Test
    void testService_Add_WithTargetUnit_Success() {
        QuantityInputDTO qt = new QuantityInputDTO(new QuantityDTO(1.0, "FEET","LengthUnit"), new QuantityDTO(12.0, "INCHES","LengthUnit"), new QuantityDTO(0.0, "INCHES","LengthUnit"));

        ResponseEntity<QuantityMeasurementDTO> result = controller.performAdditionWithTargetUnit(qt);

        assertEquals(24.0, result.getBody().resultValue, 0.01);
        assertEquals("INCHES", result.getBody().resultUnit);
        assertEquals("LengthUnit", result.getBody().resultMeasurementType);
    }

    @Test
    void testService_Add_CrossCategory_Error() {
        QuantityInputDTO qt = new QuantityInputDTO(new QuantityDTO(1.0, "FEET","LengthUnit"), new QuantityDTO(1.0, "KILOGRAM","WeightUnit"), null);
        assertThrows(CategoryMismatchException.class, ()->{
            controller.performAddition(qt);
        });
    }

    // ----------------------------------------------------
    // SERVICE - SUBTRACT
    // ----------------------------------------------------


    @Test
    void testService_Subtract_WithTargetUnit_Success() { 
        QuantityInputDTO qt = new QuantityInputDTO(new QuantityDTO(5.0, "KILOGRAM","WeightUnit"), new QuantityDTO(2000.0, "GRAM","WeightUnit"), new QuantityDTO(0.0, "GRAM", "WeightUnit"));

        ResponseEntity<QuantityMeasurementDTO> result = controller.performSubtractionWithTargetUnit(qt);

        assertEquals(3000.0, result.getBody().resultValue, 0.01);
        assertEquals("GRAM", result.getBody().resultUnit);
        assertEquals("WeightUnit", result.getBody().resultMeasurementType);
    }

    // ----------------------------------------------------
    // SERVICE - DIVIDE
    // ----------------------------------------------------

    @Test
    void testService_Divide_Success() {
        QuantityInputDTO qt = new QuantityInputDTO(new QuantityDTO(2.0, "LITRE","VolumeUnit"), new QuantityDTO(1000.0, "MILLILITRE","VolumeUnit"), null);

        double result = controller.performDivision(qt).getBody().resultValue;
        assertEquals(2.0, result, 0.01);
    }

    @Test
    void testService_Divide_ByZero_Error() {
        QuantityInputDTO qt = new QuantityInputDTO(new QuantityDTO(10.0, "FEET","LengthUnit"), new QuantityDTO(0, "FEET","LengthUnit"), null);
        
        assertThrows(ArithmeticException.class, ()->{
        		controller.performDivision(qt);
        });
    }

    // ----------------------------------------------------
    // SERVICE - VALIDATION
    // ----------------------------------------------------

    @Test
    void testService_NullEntity_Rejection() {
        QuantityInputDTO qt = new QuantityInputDTO(new QuantityDTO(10.0, "FEET","LengthUnit"), null, null);
        assertThrows(QuantityMeasurementException.class, ()->{
        		controller.performAddition(qt);
        });
    }

    @Test
    void testService_ValidationConsistency() {        
        assertThrows(QuantityMeasurementException.class, () -> controller.performAddition(new QuantityInputDTO(null, new QuantityDTO(10.0, "FEET","LengthUnit"), null)));
        assertThrows(QuantityMeasurementException.class, () -> controller.performAddition(new QuantityInputDTO(new QuantityDTO(10.0, "FEET","LengthUnit"), null, null)));
        assertThrows(QuantityMeasurementException.class, () -> controller.performAddition(new QuantityInputDTO(null, new QuantityDTO(10.0, "FEET","LengthUnit"), null)));

    }

    // ----------------------------------------------------
    // SERVICE - ALL CURRENT MEASUREMENT CATEGORIES
    // ----------------------------------------------------

    @Test
    void testService_AllMeasurementCategories_CurrentlySupported() {
        assertFalse(controller.performComparison(
                new QuantityInputDTO(new QuantityDTO(1.0, "FEET","LengthUnit"), new QuantityDTO(12.0, "INCHES","LengthUnit"), null)
                ).getBody().isError());
        
        assertFalse(controller.performComparison(
                new QuantityInputDTO(new QuantityDTO(1.0, "KILOGRAM","WeightUnit"), new QuantityDTO(1000.0, "GRAM","WeightUnit"), null)
                ).getBody().isError());
        
        assertFalse(controller.performComparison(
                new QuantityInputDTO(new QuantityDTO(1.0, "LITRE","VolumeUnit"), new QuantityDTO(1000.0, "MILLILITRE","VolumeUnit"), null)
                ).getBody().isError());
    }

    @Test
    void testService_Temperature_CurrentlyNotSupportedInService() {
        assertThrows(UnsupportedOperationException.class, ()->{
            QuantityInputDTO qt = new QuantityInputDTO(new QuantityDTO(0.0, "CELSIUS", "TemperatureUnit"), new QuantityDTO(32.0, "FAHRENHEIT", "TemperatureUnit"), null);
        		controller.performDivision(qt);
        });
    }
}