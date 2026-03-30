package com.app.quantitymeasurementapp.service;

import java.util.List;

import com.app.quantitymeasurementapp.dto.QuantityDTO;
import com.app.quantitymeasurementapp.dto.QuantityMeasurementDTO;

public interface IQuantityMeasurementService {
	public QuantityMeasurementDTO compare(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO);
	
	public QuantityMeasurementDTO convert (QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO);
	
	public QuantityMeasurementDTO add (QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO);
	
	public QuantityMeasurementDTO add(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO, QuantityDTO targetUnitDTO);
	
	public QuantityMeasurementDTO subtract (QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO);
	
	public QuantityMeasurementDTO subtract (QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO, QuantityDTO targetUnitDTO);
	
	public QuantityMeasurementDTO divide (QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO);
	
	/**
	* Retrieve the history of quantity measurement operations for a specific operation type.
	* @param operation the type of operation for which to retrieve the history
	(e.g., "conversion", "comparison",
	* @return a list of {@code QuantityMeasurementDTO} representing the history of
	operations for the specified type
	*/

	List<QuantityMeasurementDTO> getOperationHistory(String operation);
	

	/**
	* Retrieve the history of quantity measurement operations for a specific
	* measurement type.
	* @param type the measurement type for which to retrieve the history (e.g.,
	"length", "weight", "volume", "temperature")
	* @return a list of {@code QuantityMeasurementDTO} representing the history
	of operations for the specified type
	*/

	List<QuantityMeasurementDTO> getMeasurementsByType(String type);
	
	/**
	* Get the count of quantity measurement operations for a specific operation type.
	*
	* @param operation the type of operation for which to count operations
	* @return the number of operations of the specified type
	*/
	long getOperationCount(String operation);
	
	/**
	* Retrieve the history of quantity measurement operations that resulted in errors.

	* @return a list of {@code QuantityMeasurementDTO} representing the history of
	operations that resulted in errors
	*/
	List<QuantityMeasurementDTO> getErrorHistory();
}