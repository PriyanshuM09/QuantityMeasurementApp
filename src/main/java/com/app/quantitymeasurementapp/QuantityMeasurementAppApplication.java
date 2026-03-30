package com.app.quantitymeasurementapp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
@SpringBootApplication
@OpenAPIDefinition(
info = @Info(
	title = "Quantity Measurement API",
	version = "1.0.0",
	description = "REST API for quantity measurements with support "+ 
													"for multiple unit types"
	)
)
public class QuantityMeasurementAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuantityMeasurementAppApplication.class, args);
	}
}