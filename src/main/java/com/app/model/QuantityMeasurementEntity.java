package com.app.model;

import java.util.Objects;

import com.app.entity.QuantityDTO;


public class QuantityMeasurementEntity implements java.io.Serializable {
		private static final long serialVersionUID = 1L;
		public double thisValue;
		public String thisUnit;
		public String thisMeasurementType;
		public double thatValue;
		public String thatUnit;
		public String thatMeasurementType;
		
		// e.g., "COMPARE", "CONVER", "ADD", "SUBTRACT", "DEVIDE"
		public String operation;
		public double resultValue;
		public String resultUnit;
		public String resultMeasurementType;
		
		// For comparison results like "Equal" or "Not Equal"
		public String resultString;
		
		// Flag to indicate if an error occurred during the operation
		public boolean isError;
		
		// For capturing any error messages during operations
		public String errorMessage;
		
		
		public QuantityMeasurementEntity() {
			super();
		}

		public QuantityMeasurementEntity(QuantityDTO thisQuantity,QuantityDTO  thatQuantity,String operation, String result) {
                this(thisQuantity, thatQuantity, operation);
				this.resultString = result;
		}
		
		public QuantityMeasurementEntity(QuantityDTO  thisQuantity,QuantityDTO  thatQuantity,String operation,QuantityDTO  result) {
				this(thisQuantity, thatQuantity, operation);
				this. resultValue = result.getValue();
				this.resultUnit = result.getUnit();
				this. resultMeasurementType = result.getMeasurementType();}
		
		public QuantityMeasurementEntity(QuantityDTO  thisQuantity,QuantityDTO  thatQuantity,String operation, String errorMessage, boolean isError) {
				this(thisQuantity, thatQuantity, operation);
				this.errorMessage = errorMessage;
				this.isError = isError;
		}
		public QuantityMeasurementEntity(QuantityDTO  thisQuantity,QuantityDTO  thatQuantity, String operation) {
			this.thisValue = thisQuantity.getValue();
			this.thisUnit =  thisQuantity.getUnit().toString();
			this.thatValue = thatQuantity.getValue();
			this.thatUnit = thatQuantity.getUnit().toString();
			this.operation = operation;
		}
		@Override
	    public boolean equals(Object obj) {

	        if (this == obj)
	            return true;

	        if (obj == null || getClass() != obj.getClass())
	            return false;

	        QuantityMeasurementEntity that = (QuantityMeasurementEntity) obj;
	        return Double.compare(that.thisValue, thisValue) == 0 &&
	                Double.compare(that.thatValue, thatValue) == 0 &&
	                Double.compare(that.resultValue, resultValue) == 0 &&
	                isError == that.isError &&
	                Objects.equals(thisUnit, that.thisUnit) &&
	                Objects.equals(thisMeasurementType, that.thisMeasurementType) &&
	                Objects.equals(thatUnit, that.thatUnit) &&
	                Objects.equals(thatMeasurementType, that.thatMeasurementType) &&
	                Objects.equals(operation, that.operation) &&
	                Objects.equals(resultUnit, that.resultUnit) &&
	                Objects.equals(resultMeasurementType, that.resultMeasurementType) &&
	                Objects.equals(resultString, that.resultString) &&
	                Objects.equals(errorMessage, that.errorMessage);
	    }
        
		public double getThisValue() {
			return thisValue;
		}

		public void setThisValue(double thisValue) {
			this.thisValue = thisValue;
		}

		public String getThisUnit() {
			return thisUnit;
		}

		public void setThisUnit(String thisUnit) {
			this.thisUnit = thisUnit;
		}

		public String getThisMeasurementType() {
			return thisMeasurementType;
		}

		public void setThisMeasurementType(String thisMeasurementType) {
			this.thisMeasurementType = thisMeasurementType;
		}

		public double getThatValue() {
			return thatValue;
		}

		public void setThatValue(double thatValue) {
			this.thatValue = thatValue;
		}

		public String getThatUnit() {
			return thatUnit;
		}

		public void setThatUnit(String thatUnit) {
			this.thatUnit = thatUnit;
		}

		public String getThatMeasurementType() {
			return thatMeasurementType;
		}

		public void setThatMeasurementType(String thatMeasurementType) {
			this.thatMeasurementType = thatMeasurementType;
		}

		public String getOperation() {
			return operation;
		}

		public void setOperation(String operation) {
			this.operation = operation;
		}

		public double getResultValue() {
			return resultValue;
		}

		public void setResultValue(double resultValue) {
			this.resultValue = resultValue;
		}

		public String getResultUnit() {
			return resultUnit;
		}

		public void setResultUnit(String resultUnit) {
			this.resultUnit = resultUnit;
		}

		public String getResultMeasurementType() {
			return resultMeasurementType;
		}

		public void setResultMeasurementType(String resultMeasurementType) {
			this.resultMeasurementType = resultMeasurementType;
		}

		public String getResultString() {
			return resultString;
		}

		public void setResultString(String resultString) {
			this.resultString = resultString;
		}

		public boolean isError() {
			return isError;
		}

		public void setError(boolean isError) {
			this.isError = isError;
		}

		public String getErrorMessage() {
			return errorMessage;
		}

		public void setErrorMessage(String errorMessage) {
			this.errorMessage = errorMessage;
		}

		@Override
		public String toString() {
			return "QuantityMeasurementEntity [thisValue=" + thisValue + ", thisUnit=" + thisUnit
					+ ", thisMeasurementType=" + thisMeasurementType + ", thatValue=" + thatValue + ", thatUnit="
					+ thatUnit + ", thatMeasurementType=" + thatMeasurementType + ", operation=" + operation
					+ ", resultValue=" + resultValue + ", resultUnit=" + resultUnit + ", resultMeasurementType="
					+ resultMeasurementType + ", resultString=" + resultString + ", isError=" + isError
					+ ", errorMessage=" + errorMessage + "]";
		}
		
		
}