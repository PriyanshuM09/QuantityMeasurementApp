package com.app.quantitymeasurementapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.quantitymeasurementapp.dto.QuantityInputDTO;
import com.app.quantitymeasurementapp.dto.QuantityMeasurementDTO;
import com.app.quantitymeasurementapp.service.IQuantityMeasurementService;
import com.app.quantitymeasurementapp.service.QuantityMeasurementServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.util.List;
import java.util.logging.*;
@RestController
@RequestMapping("api/v1/quantities")
@Tag(name = "Quantity Measurements", description = "REST API for quantity measurement operations")
public class QuantityMeasurementController {
	
	// Logger for logging information and errors in the controller
	private static final Logger logger = Logger.getLogger(
	QuantityMeasurementController.class.getName()
	);
		
	private IQuantityMeasurementService quantityMeasurementService;

	//constructor
	public QuantityMeasurementController(QuantityMeasurementServiceImpl quantityMeasurementService) {
		this.quantityMeasurementService = quantityMeasurementService;
	}
	
	// - Reusable json example snippets
	private static final String EX_FEET_INCH ="""
		{ 
			"thisQuantityDTO": {"value":1.0,"unit":"FEET", "measurementType":"LengthUnit"},
			"thatQuantityDTO": {"value":12.0,"unit":"INCHES", "measurementType":"LengthUnit"}
		 }""";

	private static final String EX_YARD_FEET = """
		{
			 "thisQuantityDTO": {"value":1.0,"unit":"YARDS", "measurementType":"LengthUnit"},
			"thatQuantityDTO": {"value":3.0,"unit":"FEET", "measurementType":"LengthUnit"}
		 }""";

	private static final String EX_GALLON_LITRE = """
		{ 
			"thisQuantityDTO": {"value":1.0,"unit":"GALLON", "measurementType":"VolumeUnit"},
			"thatQuantityDTO": {"value":3.785,"unit":"LITRE", "measurementType":"VolumeUnit"}
		 }""";

	private static final String EX_TEMP = """
		{ 
			"thisQuantityDTO": {"value":212.0,"unit":"FAHRENHEIT", "measurementType":"TemperatureUnit"},
			"thatQuantityDTO": {"value":100.0,"unit":"CELSIUS", "measurementType":"TemperatureUnit"} 
		}""";

	private static final String EX_WITH_TARGET = """
		{ 
			"thisQuantityDTO": {"value":1.0, "unit":"FEET", "measurementType":"LengthUnit"},
			"thatQuantityDTO": {"value": 12.0, "unit":"INCHES", "measurementType":"LengthUnit"},
			"targetQuantityDTO": {"value":0.0, "unit":"INCHES", "measurementType":"LengthUnit"} 
		}""";
	
	/*
	 * Welcome Get Mapping Testing
	 */
	@GetMapping()
	public String welcome() {
		return "Welcome! It is working";
	}
	@PostMapping("/compare")
	@Operation(summary = "Compare two quantities",
		requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
			content = @Content(examples = {
				@ExampleObject(name = "Feet = 12 Inches", value = EX_FEET_INCH),
				@ExampleObject(name = "Yard = 3 Feet", value = EX_YARD_FEET),
				@ExampleObject(name = "Gallon = Litres", value = EX_GALLON_LITRE),
				@ExampleObject(name ="212°F = 100℃", value = EX_TEMP)
			})
		)
	)
	public ResponseEntity<QuantityMeasurementDTO> performComparison(@Valid @RequestBody QuantityInputDTO quantityInputDTO) {
		return ResponseEntity.ok(quantityMeasurementService.compare(quantityInputDTO.getThisQuantityDTO(), quantityInputDTO.getThatQuantityDTO()));
	}
	
	@PostMapping("/convert")
	@Operation (summary = "Convert quantity to target unit",
		requestBody = @io. swagger.v3.oas.annotations.parameters.RequestBody(
			content = @Content (examples = {
				@ExampleObject(name = "Feet -> Inches", value = EX_FEET_INCH),
				@ExampleObject(name = "Yard -> Feet", value = EX_YARD_FEET),
				@ExampleObject(name = "Gallon -> Litres", value = EX_GALLON_LITRE),
				@ExampleObject(name = "212°F -> 100℃", value = EX_TEMP)
			})
		)
	)
	public ResponseEntity<QuantityMeasurementDTO> performConversion(@Valid @RequestBody QuantityInputDTO quantityInputDTO) {
		return ResponseEntity.ok(quantityMeasurementService.convert(quantityInputDTO.getThisQuantityDTO(), quantityInputDTO.getThatQuantityDTO()));
	}
	
	@PostMapping("/add")
	@Operation (summary = "Add two quantities",
		requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
			content = @Content (examples = {
				@ExampleObject (name = "Feet + Inches", value = EX_FEET_INCH),
				@ExampleObject(name = "Yard + Feet", value = EX_YARD_FEET),
				@ExampleObject(name = "Gallon + Litres", value = EX_GALLON_LITRE)
			})
		)
	)
	
	public ResponseEntity<QuantityMeasurementDTO> performAddition(@Valid @RequestBody QuantityInputDTO quantityInputDTO) {
		return ResponseEntity.ok(quantityMeasurementService.add(quantityInputDTO.getThisQuantityDTO(), quantityInputDTO.getThatQuantityDTO()));
	}
	
	@PostMapping("/add-with-target-unit")
	@Operation (summary = "Add two quantities with a target unit",
		requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
			content = @Content (examples = {
				@ExampleObject(name = "Feet + Inches with Target Unit", value = EX_WITH_TARGET)
			})
		)
	)
	
	public ResponseEntity<QuantityMeasurementDTO> performAdditionWithTargetUnit(@Valid @RequestBody QuantityInputDTO quantityInputDTO) {
		return ResponseEntity.ok(quantityMeasurementService.add(quantityInputDTO.getThisQuantityDTO(), quantityInputDTO.getThatQuantityDTO(), quantityInputDTO.getTargetQuantityDTO()));
	}
	
	@PostMapping("/subtract")
	@Operation (summary = "Subtract two quantities",
		requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
			content = @Content(examples = {
				@ExampleObject(name = "Feet - Inches", value = EX_FEET_INCH),
				@ExampleObject(name = "Yard - Feet", value = EX_YARD_FEET),
				@ExampleObject(name = "Gallon - Litres", value = EX_GALLON_LITRE)
			})
		)
	)
	
	public ResponseEntity<QuantityMeasurementDTO> performSubtraction(@Valid @RequestBody QuantityInputDTO quantityInputDTO) {
		return ResponseEntity.ok(quantityMeasurementService.subtract(quantityInputDTO.getThisQuantityDTO(), quantityInputDTO.getThatQuantityDTO()));
	}
	
	@PostMapping("/subtract-with-target-unit")
	@Operation (summary = "Subtract two quantities with target unit",
		requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
			content = @Content(examples = {
				@ExampleObject(name = "Feet - Inches with Target Unit", value = EX_WITH_TARGET)
			})
		)
	)
	
	public ResponseEntity<QuantityMeasurementDTO> performSubtractionWithTargetUnit(@Valid @RequestBody QuantityInputDTO quantityInputDTO) {
		return ResponseEntity.ok(quantityMeasurementService.subtract(quantityInputDTO.getThisQuantityDTO(), quantityInputDTO.getThatQuantityDTO(), quantityInputDTO.getTargetQuantityDTO()));
	}
	
	@PostMapping("/divide")
	@Operation (summary = "Divide two quantities",
		requestBody = @io. swagger.v3.oas.annotations.parameters. RequestBody(
			content = @Content(examples = {
				@ExampleObject(name = "Feet / Inches", value = EX_FEET_INCH),
				@ExampleObject(name = "Yard / Feet", value = EX_YARD_FEET),
				@ExampleObject(name = "Gallon / Litres", value = EX_GALLON_LITRE)
			})
		)
	)
	
	public ResponseEntity<QuantityMeasurementDTO> performDivision(@Valid @RequestBody QuantityInputDTO quantityInputDTO) {
		return ResponseEntity.ok(quantityMeasurementService.divide(quantityInputDTO.getThisQuantityDTO(), quantityInputDTO.getThatQuantityDTO()));
	}

	@GetMapping("/history/operation/{operation}")
	@Operation(
		summary = "Get operation history",
		description = "Valid operations: ADD, SUBTRACT, MULTIPLY, DIVIDE, CONVERT"
	)
	public ResponseEntity<List<QuantityMeasurementDTO>> getOperationHistory(@PathVariable String operation){
		return ResponseEntity.ok(quantityMeasurementService.getOperationHistory(operation));
	}
	

	@GetMapping("/history/type/{type}")
	@Operation(
		summary = "Get operation history by type",
		description = "Valid types: LengthUnit, VolumeUnit, WeightUnit, TemperatureUnit"
	)
	public ResponseEntity<List<QuantityMeasurementDTO>> getOperationHistoryByType(@PathVariable String type){
		return ResponseEntity.ok(quantityMeasurementService.getMeasurementsByType(type));
	}


	@GetMapping("/count/{operation}")
	@Operation(
		summary = "Get operation count",
		description = "Valid operations: ADD, SUBTRACT, MULTIPLY, DIVIDE, CONVERT"
	)
	
	public ResponseEntity<Long> getOperationCount(@PathVariable String operation){
		return ResponseEntity.ok(quantityMeasurementService.getOperationCount(operation));
	}
	
	@GetMapping("/history/errored")
	@Operation (summary = "Get errored operations history")
	public ResponseEntity<List<QuantityMeasurementDTO>> getErroredOperations(){
		return ResponseEntity.ok(quantityMeasurementService.getErrorHistory());
	}
}