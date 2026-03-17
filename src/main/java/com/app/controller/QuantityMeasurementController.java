package com.app.controller;

import com.app.entity.QuantityDTO;
import com.app.service.IQuantityMeasurementService;

public class QuantityMeasurementController {
   private IQuantityMeasurementService quantityMeasurementService;
   
   public QuantityMeasurementController(IQuantityMeasurementService quantityMeasurementService) {
	   this.quantityMeasurementService = quantityMeasurementService;
   }
   
   public boolean performComparison(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {
	   	return quantityMeasurementService.compare(thisQuantityDTO, thatQuantityDTO);
   }
   
   public QuantityDTO performConversion(QuantityDTO thisQuantityDTO,QuantityDTO thatQuantityDTO) {
	   return quantityMeasurementService.convert(thisQuantityDTO, thatQuantityDTO);
   }
   
   public QuantityDTO performAddition(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {
	   return quantityMeasurementService.add(thisQuantityDTO, thatQuantityDTO);
   }
   
   public QuantityDTO performAddition(QuantityDTO thisQuantityDTO,QuantityDTO thatQuantityDTO,QuantityDTO targetQuantityDTO) {
	   return quantityMeasurementService.add(thisQuantityDTO, thatQuantityDTO,targetQuantityDTO);
   }
   
   public QuantityDTO performSubtraction(QuantityDTO thisQuantityDTO,QuantityDTO thatQuantityDTO) {
	   return quantityMeasurementService.subtract(thisQuantityDTO, thatQuantityDTO);
   }
   
   public QuantityDTO performSubtraction(QuantityDTO thisQuantityDTO,QuantityDTO thatQuantityDTO,QuantityDTO targetQuantityDTO) {
	   return quantityMeasurementService.subtract(thisQuantityDTO, thatQuantityDTO, targetQuantityDTO);
   }
   
   public QuantityDTO performDivision(QuantityDTO thisQuantityDTO,QuantityDTO thatQuantityDTO) {
	   return quantityMeasurementService.divide(thisQuantityDTO, thatQuantityDTO);
   }
   
   public QuantityDTO performDivision(QuantityDTO thisQuantityDTO,QuantityDTO thatQuantityDTO, QuantityDTO targetQuantityDTO) {
	   return quantityMeasurementService.divide(thisQuantityDTO, thatQuantityDTO, targetQuantityDTO);
   }
}