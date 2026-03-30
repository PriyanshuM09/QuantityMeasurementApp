package com.app.quantitymeasurementapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema (example = """
	{
		"thisQuantityDTO":{ "value": 1.0, "unit": "FEET", "measurementType": "LengthUnit" },
		"thatQuantityDTO":{ "value": 12.0, "unit": "INCHES", "measurementType": "LengthUnit" },
		"targetQuantityDTO": { "value": 0.0, "unit": "INCHES", "measurementType": "LengthUnit" }
	}
	"""
)
public class QuantityInputDTO {
	@Valid
	@NotNull(message = "First quantity cannot be null")
	private QuantityDTO thisQuantityDTO;

	@Valid
	@NotNull(message = "Second quantity cannot be null")
	private QuantityDTO thatQuantityDTO;

	// Optional field for addition and subtraction operations
	@Valid
	@Schema(nullable = true)
	private QuantityDTO targetQuantityDTO;
}