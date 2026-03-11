package com.apps.quantitymeasurement.model;

import java.io.Serializable;
import com.apps.quantitymeasurement.core.IMeasurable;

public class QuantityMeasurementEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    public double thisValue;
    public String thisUnit;
    public String thisMeasurementType;

    public double thatValue;
    public String thatUnit;
    public String thatMeasurementType;

    // Example operations: COMPARE, CONVERT, ADD, SUBTRACT, DIVIDE
    public String operation;

    public double resultValue;
    public String resultUnit;
    public String resultMeasurementType;

    // For comparison results
    public String resultString;

    // Error handling
    public boolean isError;
    public String errorMessage;


    /**
     * Comparison / Conversion constructor
     */
    public QuantityMeasurementEntity(
            QuantityModel<IMeasurable> thisQuantity,
            QuantityModel<IMeasurable> thatQuantity,
            String operation,
            String result
    ) {

        this(thisQuantity, thatQuantity, operation);
        this.resultString = result;
    }


    /**
     * Arithmetic constructor
     */
    public QuantityMeasurementEntity(
            QuantityModel<IMeasurable> thisQuantity,
            QuantityModel<IMeasurable> thatQuantity,
            String operation,
            QuantityModel<IMeasurable> result
    ) {

        this(thisQuantity, thatQuantity, operation);

        this.resultValue = result.value;
        this.resultUnit = result.unit.getUnitName();
        this.resultMeasurementType = result.unit.getMeasurementType();
    }


    /**
     * Error constructor
     */
    public QuantityMeasurementEntity(
            QuantityModel<IMeasurable> thisQuantity,
            QuantityModel<IMeasurable> thatQuantity,
            String operation,
            String errorMessage,
            boolean isError
    ) {

        this(thisQuantity, thatQuantity, operation);

        this.errorMessage = errorMessage;
        this.isError = isError;
    }


    /**
     * Base constructor
     */
    private QuantityMeasurementEntity(
            QuantityModel<IMeasurable> thisQuantity,
            QuantityModel<IMeasurable> thatQuantity,
            String operation
    ) {

        this.thisValue = thisQuantity.value;
        this.thisUnit = thisQuantity.unit.getUnitName();
        this.thisMeasurementType = thisQuantity.unit.getMeasurementType();

        if (thatQuantity != null) {
            this.thatValue = thatQuantity.value;
            this.thatUnit = thatQuantity.unit.getUnitName();
            this.thatMeasurementType = thatQuantity.unit.getMeasurementType();
        }

        this.operation = operation;
    }
}