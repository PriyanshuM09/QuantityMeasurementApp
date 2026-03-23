package com.app.quantitymeasurement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.app.quantitymeasurement.dto.QuantityDTO;
import com.app.quantitymeasurement.dto.QuantityInputDTO;
import com.app.quantitymeasurement.service.IQuantityMeasurementService;

import lombok.Data;

@RestController
@RequestMapping("/api/v1/quantities")
@Data
public class QuantityMeasurementController {

    @Autowired
    private IQuantityMeasurementService service;

    // ================= COMPARE =================

    @PostMapping("/compare")
    public boolean compare(@RequestBody QuantityInputDTO input) {

        return service.compare(
                input.getThisQuantityDTO(),
                input.getThatQuantityDTO()
        );
    }

    // ================= CONVERT =================

    @PostMapping("/convert/{targetUnit}")
    public QuantityDTO convert(
            @RequestBody QuantityDTO input,
            @PathVariable String targetUnit) {

        return service.convert(input, targetUnit);
    }

    // ================= ADD =================

    @PostMapping("/add")
    public QuantityDTO add(@RequestBody QuantityInputDTO input) {

        return service.add(
                input.getThisQuantityDTO(),
                input.getThatQuantityDTO()
        );
    }

    // ================= SUBTRACT =================

    @PostMapping("/subtract")
    public QuantityDTO subtract(@RequestBody QuantityInputDTO input) {

        return service.subtract(
                input.getThisQuantityDTO(),
                input.getThatQuantityDTO()
        );
    }

    // ================= DIVIDE =================

    @PostMapping("/divide")
    public double divide(@RequestBody QuantityInputDTO input) {

        return service.divide(
                input.getThisQuantityDTO(),
                input.getThatQuantityDTO()
        );
    }
}