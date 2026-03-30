package com.app.quantitymeasurementapp.entity;

import java.time.LocalDateTime;

import jakarta.persistence .*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // Marks this class as a JPA entity
@Table(name = "quantity_measurement_entity", indexes = {
		@Index(name = "idx_operation", columnList = "operation"),
		@Index(name ="idx_measurement_type", columnList = "this_measurement_type"),
		@Index(name = "idx_created_at", columnList = "created_at")
})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuantityMeasurementEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="this_value", nullable = false)
	private double thisValue;
	
	@Column(name="this_unit", nullable = false)
	private String thisUnit;
	
	@Column(name="this_measurement_type", nullable = false)
	private String thisMeasurementType;
	
	@Column(name="that_value", nullable = false)
	private double thatValue;
	
	@Column(name="that_unit", nullable = false)
	private String thatUnit;
	
	@Column(name="that_measurement_type", nullable = false)
	private String thatMeasurementType;

	// e.g., "COMPARE", "CONVERT", "ADD", "SUBTRACT", "DIVIDE"
	@Column(name="operation", nullable = false)
	private String operation;
	
	@Column(name="result_Value", nullable = false)
	private double resultValue;
	
	@Column(name="result_unit", nullable = false)
	private String resultUnit;
	
	@Column(name="result_measurement_type", nullable = false)
	private String resultMeasurementType;
	
	//For comparison Equals or not
	@Column(name="result_string", nullable = false)
	private String resultString;
	
	//Error handling
	@Column(name="is_error", nullable = false)
	private boolean isError;
	
	@Column(name="error_message", nullable = false)
	private String errorMessage;
	
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@Column(name = "updated_at", nullable = false)
	private LocalDateTime updatedAt;
	
	@PrePersist
	protected void onCreate() {
		createdAt = LocalDateTime.now();
		updatedAt = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		updatedAt = LocalDateTime.now();
	}
	
	 /*
     * Comparison / Conversion
     * Example: 1 ft == 12 in
     */
	public QuantityMeasurementEntity(double thisValue, String thisUnit, String thisMeasurementType, double thatValue, String thatUnit, String thatMeasurementType, String operation, String resultString) {
			initializeCommonFields(thisValue, thisUnit, thisMeasurementType, thatValue, thatUnit, thatMeasurementType, operation);
			
			this.resultString = resultString;
	}


    /*
     * Arithmetic Operation
     * Example: 1 ft + 12 in = 2 ft
     */
	public QuantityMeasurementEntity(double thisValue, String thisUnit, String thisMeasurementType, double thatValue, String thatUnit, String thatMeasurementType, String operation, double resultValue, String resultUnit, String resultMeasurementType) {
			initializeCommonFields(thisValue, thisUnit, thisMeasurementType, thatValue, thatUnit, thatMeasurementType, operation);
			
			this.resultValue = resultValue;
			this.resultUnit = resultUnit;
			this.resultMeasurementType = resultMeasurementType;
	}
    
	/*
	 * Numeric Result Only(division result)
	 * Example: 2 ft / 24 in = 0 ft
	 */
	public QuantityMeasurementEntity(double thisValue, String thisUnit, String thisMeasurementType, double thatValue, String thatUnit, String thatMeasurementType, String operation, double resultValue) {
			initializeCommonFields(thisValue, thisUnit, thisMeasurementType, thatValue, thatUnit, thatMeasurementType, operation);

			this.resultValue = resultValue;
	}

    /*
     * Constructor for error cases
     */
	public QuantityMeasurementEntity(double thisValue, String thisUnit, String thisMeasurementType, double thatValue, String thatUnit, String thatMeasurementType, String operation, String errorMessage, boolean isError) {
			initializeCommonFields(thisValue, thisUnit, thisMeasurementType, thatValue, thatUnit, thatMeasurementType, operation);
			
			this.isError = isError;
			this.errorMessage = errorMessage;
	}

	/*
	 * Full Constructor
	 */
	public QuantityMeasurementEntity(double thisValue, String thisUnit, String thisMeasurementType,double thatValue, String thatUnit, String thatMeasurementType, String operation, double resultValue, String resultUnit, String resultMeasurementType, String resultString, boolean isError, String errorMessage) {
			initializeCommonFields(thisValue, thisUnit, thisMeasurementType, thatValue, thatUnit, thatMeasurementType, operation);
			
			this.resultValue = resultValue;
			this.resultUnit = resultUnit;
			this.resultMeasurementType = resultMeasurementType;
			this.resultString = resultString;
			this.isError = isError;
			this.errorMessage = errorMessage;
	}
	
    /*
     * Common Field Initializer
     */
    private void initializeCommonFields(double thisValue, String thisUnit, String thisMeasurementType, double thatValue, String thatUnit, String thatMeasurementType, String operation) {
		this.thisValue = thisValue;
		this.thisUnit = thisUnit;
		this.thisMeasurementType = thisMeasurementType;
		
		this.thatValue = thatValue;
		this.thatUnit = thatUnit;
		this.thatMeasurementType = thatMeasurementType;
		
		this.operation = operation;
		
		// default values
		this.resultValue = 0;
		this.resultUnit = "";
		this.resultMeasurementType = "";
		this.resultString = "";
		this.isError = false;
		this.errorMessage = "";
    }    
}