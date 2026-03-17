package com.app.entity;

interface IMeasurableUnit{
	String getUnitName();
	String getMeasurementType();
}
public class QuantityDTO {
   enum LengthUnit implements IMeasurableUnit{
    FEET,INCHES,YARD,CENTIMETRE;

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
   enum WeightUnit implements IMeasurableUnit{
	KG,GRAM,POUND;

	@Override
	public String getUnitName() {
		return this.name();
	}

	@Override
	public String getMeasurementType() {
	    return this.getClass().getSimpleName();
	}  
   }
   enum VolumneUnit implements IMeasurableUnit{
	MILILITRE,LITRE,GALLON;

	@Override
	public String getUnitName() {
		return this.name();
	}

	@Override
	public String getMeasurementType() {
		return this.getClass().getSimpleName();
	}   
   }
   enum TemperatureUnit implements IMeasurableUnit{
	CELSIUS,FAHRENHEIT,KELVIN;

	@Override
	public String getUnitName() {
	   return this.name();
	}

	@Override
	public String getMeasurementType() {
	    return this.getClass().getSimpleName();
	}   
   }
   public double value;
   public String unit;
   public String measurementType;
   
   public QuantityDTO(double value,IMeasurableUnit unit) {
	   this.value = value;
	   this.unit = unit.getUnitName();
	   this.measurementType = unit.getMeasurementType();
   }
   
   public QuantityDTO(double value, String unit, String measurementType) {
	this.value = value;
	this.unit = unit;
	this.measurementType = measurementType;
   }

   public double getValue() {
	return value;
   }

   public String getUnit() {
	return unit;
   }

   public String getMeasurementType() {
	return measurementType;
   }

   @Override
   public String toString() {
	return "QuantityDTO [value=" + value + ", unit=" + unit + ", measurementType=" + measurementType + "]";
   } 
   
   
}