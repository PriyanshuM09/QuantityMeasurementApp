package com.app.quantitymeasurementapp.dto;

import java.util.logging.Logger;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

interface IMeasurableUnit{
	String getUnitName();
	String getMeasurementType();
}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "A quantity with a value and unit")
public class QuantityDTO {

	// Logger for debugging purposes
	private static final Logger logger = Logger.getLogger(QuantityDTO.class.getName());

	enum LengthUnit implements IMeasurableUnit {
		FEET, INCHES, YARD, CENTIMETRE;

		@Override
		public String getUnitName() {
			// TODO Auto-generated method stub
			return this.name();
		}

		@Override
		public String getMeasurementType() {
			return this.getClass().getSimpleName();
		}
	}

	enum WeightUnit implements IMeasurableUnit {
		KG, GRAM, POUND;

		@Override
		public String getUnitName() {
			return this.name();
		}

		@Override
		public String getMeasurementType() {
			return this.getClass().getSimpleName();
		}
	}

	enum VolumneUnit implements IMeasurableUnit {
		MILILITRE, LITRE, GALLON;

		@Override
		public String getUnitName() {
			return this.name();
		}
		@Override
		public String getMeasurementType() {
			return this.getClass().getSimpleName();
		}
	}

	enum TemperatureUnit implements IMeasurableUnit {
		CELSIUS, FAHRENHEIT, KELVIN;

		@Override
		public String getUnitName() {
			return this.name();
		}

		@Override
		public String getMeasurementType() {
			return this.getClass().getSimpleName();
		}
	}

	@NotNull(message = "Value cannot be empty")
	@Schema(example = "1.0")
	public double value;

	@NotNull(message = "Unit cannot be null")
	@Schema(example = "FEET", allowableValues = { "FEET", "INCHES", "YARDS", "CENTIMETERS", "LITRE", "MILLILITER",
			"GALLON", "MILLIGRAM", "GRAM", "KILOGRAM", "POUND", "TONNE", "CELSIUS", "FAHRENHEIT" })
	public String unit;

	@NotNull(message = "Measurement type cannot be null")
	@Pattern(regexp = "(?i)LengthUnit|VolumeUnit|WeightUnit|TemperatureUnit", message = "Measurement type must be one of: LengthUnit, VolumeUnit, "
			+ "WeightUnit, TemperatureUnit")
	@Schema(example = "LengthUnit", allowableValues = { "LengthUnit", "VolumeUnit", "WeightUnit", "TemperatureUnit" })
	public String measurementType;

	@AssertTrue(message = "Unit must be valid for the specified measurement type")
	public boolean isValidUnit() {
		logger.info("Validating unit: " + unit + " for measurement type: " + measurementType);
		try {
			switch (measurementType) {
			case "LengthUnit":
				LengthUnit.valueOf(unit);
				break;
			case "VolumeUnit":
				VolumneUnit.valueOf(unit);
				break;
			case "WeightUnit":
				WeightUnit.valueOf(unit);
				break;
			case "TemperatureUnit":
				TemperatureUnit.valueOf(unit);
				break;
			default:
				return false;
			}
		} catch (IllegalArgumentException e) {
			return false;
		}
		return true;
	}
}