package com.app.quantitymeasurementapp.unit;

import java.util.function.Function;

public enum TemperatureUnit implements IMeasurable{
	CELSIUS(
        c -> c,
        c -> c
    ),

    FAHRENHEIT(
        f -> (f - 32) * 5 / 9,
        c -> (c * 9 / 5) + 32
    ),

    KELVIN(
        k -> k - 273.15,
        c -> c + 273.15
    );

    private final Function<Double, Double> toBase;
    private final Function<Double, Double> fromBase;

    SupportsArithmetic supportsArithmetic = () -> false;

    TemperatureUnit(Function<Double, Double> toBase,
                    Function<Double, Double> fromBase) {
        this.toBase = toBase;
        this.fromBase = fromBase;
    }

	@Override
	public double getConversionFactor() {
		return 1.0;
	}

	@Override
	public double convertToBaseUnit(double value) {
		return toBase.apply(value);
	}

	@Override
	public double convertFromBaseUnit(double baseValue) {
		return fromBase.apply(baseValue);
	}

	@Override
	public String getUnitName() {
		return this.name();
	}	
	
	public boolean supportsArithmetic() {
		return supportsArithmetic.isSupported();
	}
	
	@Override
	public String getMeasurableType() {
		return this.getClass().getSimpleName();
	}
	
	@Override
	public void validateOperationSupport(String operation) {
		if(operation.equals("ADD") || operation.equals("DIVIDE") || operation.equals("SUBTRACT")) {
			String message = this.name()+" does not support "+operation+" operations!";
			throw new UnsupportedOperationException(message);
	    }
	}
}