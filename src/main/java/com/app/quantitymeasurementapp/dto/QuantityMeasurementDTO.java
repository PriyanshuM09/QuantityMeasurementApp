package com.app.quantitymeasurementapp.dto;

import java.util.ArrayList;
import java.util.List;

import com.app.quantitymeasurementapp.entity.QuantityMeasurementEntity;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuantityMeasurementDTO {
	public double thisValue;
	public String thisUnit;
	public String thisMeasurementType;
	public double thatValue;
	public String thatUnit;
	public String thatMeasurementType;
	public String operation;
	public double resultValue;
	public String resultUnit;
	public String resultString;
	public String resultMeasurementType;
	@JsonProperty("error")
	public boolean error;
	public String errorMessage;
	

	public QuantityMeasurementDTO from(QuantityMeasurementEntity entity) {
		return new QuantityMeasurementDTO(entity.getThisValue(), entity.getThisUnit(), entity.getThisMeasurementType(), entity.getThatValue(), entity.getThatUnit(), entity.getThatMeasurementType(), entity.getOperation(), entity.getResultValue(), entity.getResultUnit(), entity.getResultString(), entity.getResultMeasurementType(),  entity.isError(), entity.getErrorMessage());
	}
	
	public QuantityMeasurementEntity toEntity() {
		if(error) {
			return new QuantityMeasurementEntity(thisValue, thisUnit, thisMeasurementType, thatValue, thatUnit, thatMeasurementType, operation, errorMessage, error);
		}
		return new QuantityMeasurementEntity(thisValue, thisUnit, thisMeasurementType, thatValue, thatUnit, thatMeasurementType, operation, resultValue, resultUnit, resultMeasurementType, resultString, error, errorMessage);
	}
	
	public List<QuantityMeasurementDTO> fromList(List<QuantityMeasurementEntity> list){ 		
		List<QuantityMeasurementDTO> compute = new ArrayList<>();
		for(QuantityMeasurementEntity entity:list) {
			compute.add(from(entity));
		}
		return compute;
	}
	public List<QuantityMeasurementEntity> toEntityList(List<QuantityMeasurementDTO> list){
		List<QuantityMeasurementEntity> compute = new ArrayList<>();
		for(QuantityMeasurementDTO entity:list) {
			compute.add(entity.toEntity());
		}
		return compute;
	}
}