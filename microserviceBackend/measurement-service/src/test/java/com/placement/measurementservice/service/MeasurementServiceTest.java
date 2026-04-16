package com.placement.measurementservice.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MeasurementServiceTest {

    private final MeasurementService service = new MeasurementService();

    @Test
    void convertsTemperatureWhenTypeAliasIsTemp() {
        double result = service.convert("temp", "celsius", "fahrenheit", 100);

        assertEquals(212.0, result, 0.001);
    }

    @Test
    void convertsLengthWithLowerCaseUnits() {
        double result = service.convert("length", "meter", "centimeter", 2);

        assertEquals(200.0, result, 0.001);
    }

    @Test
    void rejectsUnknownMeasurementType() {
        assertThrows(IllegalArgumentException.class,
                () -> service.convert("speed", "meter", "kilometer", 1));
    }
}
