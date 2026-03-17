package com.app.unit;

public enum LengthUnit implements IMeasurable{
	FEET(1.0),
    INCHES(1.0 / 12.0),
    YARD(3.0),
    CENTIMETRE(0.0328084);
	 
	 private final double conversion;
	 
	LengthUnit(double conversion) {
		this.conversion = conversion;
	}
	
	public double getConversionFactor() {
		return conversion;
	}
	public double convertToBaseUnit(double value) {
   	 return value*this.getConversionFactor();
    }
    public double convertFromBaseUnit(double value) {
    	return (LengthUnit.FEET.getConversionFactor()*value)/this.getConversionFactor();
    }


	@Override
	public String getUnitName() {
		// TODO Auto-generated method stub
		return LengthUnit.this.name();
	}

	@Override
	public String getMeasurementType() {
		// TODO Auto-generated method stub
		return this.getClass().getSimpleName();
	}

	@Override
	public IMeasurable getUnitInstance(String unitName) {
		for(LengthUnit len: LengthUnit.values()) {
			if(len.equals(unitName)) {
				return len;
			}
		}
		throw new IllegalArgumentException("The Unit Doesn't exists");
	}

	
}