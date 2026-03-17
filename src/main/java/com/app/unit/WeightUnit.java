package com.app.unit;

public enum WeightUnit implements IMeasurable{
    KG(1.0),
	GRAM(0.001),
	POUND(0.453592);
	
	 private double conversion;
	
     WeightUnit(double conversion) {
		this.conversion = conversion;
	}
      
     public double getConversionFactor() {
    	 return conversion;
     }
     
     public double convertToBaseUnit(double value) {
    	 return value*getConversionFactor();
     }

	 @Override
	public double convertFromBaseUnit(double value) {
			return (WeightUnit.KG.getConversionFactor()*value)/this.getConversionFactor();
			}

	 @Override
	public String getUnitName() {
		// TODO Auto-generated method stub
		return WeightUnit.this.name();
	}

	 @Override
	public String getMeasurementType() {
		// TODO Auto-generated method stub
		 return this.getClass().getSimpleName();
	}

	 @Override
	public IMeasurable getUnitInstance(String unitName) {
	     for(WeightUnit weight: WeightUnit.values()) {
	    	 if(weight.equals(unitName)) {
	    		 return weight;
	    	 }
	     }
	     throw new IllegalArgumentException("The Unit Doesn't Exists");
	}
}