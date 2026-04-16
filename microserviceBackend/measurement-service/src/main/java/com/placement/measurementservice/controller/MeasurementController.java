package com.placement.measurementservice.controller;

import com.placement.measurementservice.service.MeasurementService;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for quantity conversion operations.
 * This controller handles multi-unit conversions via its generic /convert endpoint.
 */
@RestController
@RequestMapping("/measure")
public class MeasurementController {

    private final MeasurementService service;

    public MeasurementController(MeasurementService service) {
        this.service = service;
    }

    /**
     * Generic conversion endpoint.
     */
    @GetMapping("/convert")
    public double convert(
            @RequestParam String type,
            @RequestParam String fromUnit,
            @RequestParam String toUnit,
            @RequestParam double value) {
        return service.convert(type, fromUnit, toUnit, value);
    }

    /**
     * Arithmetic operation endpoint.
     */
    @GetMapping("/arithmetic")
    public double calculate(
            @RequestParam String type,
            @RequestParam String op,
            @RequestParam double val1,
            @RequestParam String unit1,
            @RequestParam double val2,
            @RequestParam String unit2,
            @RequestParam String targetUnit) {
        return service.calculate(type, op, val1, unit1, val2, unit2, targetUnit);
    }

    /**
     * Comparison endpoint.
     */
    @GetMapping("/compare")
    public boolean compare(
            @RequestParam String type,
            @RequestParam double val1,
            @RequestParam String unit1,
            @RequestParam double val2,
            @RequestParam String unit2) {
        return service.compare(type, val1, unit1, val2, unit2);
    }

    // Keep legacy endpoints for backward compatibility
    @GetMapping("/length")
    public double convertLength(@RequestParam double value) {
        return service.convert("length", "Meter", "Centimeter", value);
    }

    @GetMapping("/temp")
    public double convertTemperature(@RequestParam double celsius) {
        return service.convert("temperature", "Celsius", "Fahrenheit", celsius);
    }

    @GetMapping("/weight")
    public double convertWeight(@RequestParam double kg) {
        return service.convert("weight", "Kilogram", "Gram", kg);
    }

    @GetMapping("/volume")
    public double convertVolume(@RequestParam double liter) {
        return service.convert("volume", "Liter", "Milliliter", liter);
    }
}
