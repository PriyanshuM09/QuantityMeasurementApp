package com.app.unit;

import java.util.function.Function;

public enum Temperature implements IMeasurable {
	    CELSIUS(false),
	    FAHRENHEIT(true),
        KELVIN(false);
	    private final Function<Double,Double> FARENHEIT_TO_CELCIUS = (Double farenheit)-> (farenheit-32)*5/9;
	    
	    private final Function<Double, Double> CELSIUS_TO_CELCIUS = (Double celcius)-> celcius;
	    
	    private final Function<Double, Double> CELSIUS_TO_FAHRENHEIT =  (Double c) -> (c * 9 / 5) + 32;


	    private final Function<Double, Double> KELVIN_TO_CELSIUS = k -> k - 273.15;

	    private final Function<Double, Double> CELSIUS_TO_KELVIN = c -> c + 273.15;
	    
	    Function<Double, Double> toBase;
	    Function<Double, Double> fromBase;

	    private final SupportArithemetic supportArithemetic = () -> false;

	    Temperature(boolean isFahrenheit) {

	        if (isFahrenheit) {
	            toBase = FARENHEIT_TO_CELCIUS;
	            fromBase = CELSIUS_TO_FAHRENHEIT;
	        }else if(this.getUnitName().equalsIgnoreCase("KELVIN")) {
	        	toBase = KELVIN_TO_CELSIUS;
	        	fromBase = CELSIUS_TO_KELVIN;
	        }else {
	            toBase = CELSIUS_TO_CELCIUS;
	            fromBase = CELSIUS_TO_CELCIUS;
	        }
	    }
	    @Override
	    public double convertToBaseUnit(double value) { return toBase.apply(value); }

	    @Override
	    public double convertFromBaseUnit(double value) { return fromBase.apply(value); }
        
	    @Override
	    public boolean supportsArithemetics() { return supportArithemetic.isSupported(); }

	    @Override
	    public void validateOperationSupport(String operation) {
	    	if (!supportArithemetic.isSupported() ) {
	    		String message = this.name() + " does not support " + operation + " operations.";
	    		throw new UnsupportedOperationException(message);}
	    }
        public double convertTo(double value,Temperature targetUnit) {
        	 if (this == targetUnit) {
        	        return value;
        	    }
        	    double baseValue = this.convertToBaseUnit(value);
                return targetUnit.convertFromBaseUnit(baseValue);	
        }
		@Override
		public double getConversionFactor() {
			return 1.0;
		}

		@Override
		public String getUnitName() {
			return this.name();
		}
		@Override
		public String getMeasurementType() {
			// TODO Auto-generated method stub
			return this.getClass().getSimpleName();
		}
		@Override
		public IMeasurable getUnitInstance(String unitName) {
			for(Temperature temp : Temperature.values()) {
				if(temp.equals(unitName)) {
					return temp;
				}
			}
			throw new IllegalArgumentException("This unit doesn't exists");
		}

}