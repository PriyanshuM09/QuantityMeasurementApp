package com.app.model;

import java.util.function.DoubleBinaryOperator;

import com.app.unit.IMeasurable;


public class Quantity<T extends IMeasurable> {
     private double value;
     private T unit;
     
     public Quantity(Double value,T unit){
    	 if(Double.isNaN(value)||value==null||unit==null) {
    		 throw new IllegalArgumentException("Invalid Input");
    	 }
    	 this.value = value;
    	 this.unit = unit;
     }
     
     public double getValue() {
		return value;
	}

	 public T getUnit() {
		 return unit;
	 }

	 @Override
    public boolean equals(Object obj) {
    	if(obj==this) return true;
    	
    	if(obj==null||this.getClass()!=obj.getClass()) return false;
    	Quantity<T> temp= (Quantity<T>)obj;
    	if(temp.unit.getClass()!=this.unit.getClass()) return false;
    	return this.compare((Quantity<T>)obj);
    }
     
     public boolean compare(Quantity<T> obj) {
    	 if(obj==null) return false;
    	 
    	 return Math.abs(this.unit.convertToBaseUnit(value)-obj.getUnit().convertToBaseUnit(obj.getValue()))<0.0001;
     }
     
     public Quantity<T> convertTo(T unit) {
    	 if(unit==null) throw new IllegalArgumentException("Invalid Input");
    	 double temp = this.unit.convertToBaseUnit(value)/unit.getConversionFactor();
    	 return new Quantity<>(temp, unit);
     }
     public Quantity<T> convertTo(Quantity<T> val){
    	 if(val==null) throw new IllegalArgumentException("Invalid Input");
    	 double temp = this.unit.convertToBaseUnit(value)/val.getUnit().getConversionFactor();
    	 return new Quantity<>(temp, unit);
     }
     private double convertBaseToTargetUnit(Double lengthInInches,T targetUnit) {
    	 if(lengthInInches==null||targetUnit==null) throw new IllegalArgumentException("Invalid Input");
		 return (lengthInInches*this.unit.getConversionFactor())/targetUnit.getConversionFactor();
	 }
     
     public Quantity<T> add(Quantity<T> val){
    	 val.getUnit().validateOperationSupport("add");
    	 this.validateArithmeticOperands(val, null, false);
    	 return new Quantity<>(performBaseArithmetic(val, ArithemeticOperations.ADD), this.unit);
     }
     
     private Quantity<T> addAndConvert(Quantity<T> val1, T unit){
    	 unit.validateOperationSupport("add");
    	this.validateArithmeticOperands(val1,unit, true);
    	 
    	 return new Quantity<>(performBaseArithmetic(val1, ArithemeticOperations.ADD),this.getUnit());
     }
     public Quantity<T> add(Quantity<T> val1,T unit) {
    	 unit.validateOperationSupport("add");
    	 return this.addAndConvert(val1, unit).convertTo(unit);
     }
     
    public Quantity<T> subtract(Quantity<T> val){
    	val.getUnit().validateOperationSupport("subtract");
        this.validateArithmeticOperands(val,null, false);
    	return new Quantity<>(performBaseArithmetic(val,ArithemeticOperations.SUBTRACT),this.getUnit());
    }
    
    public Quantity<T> division(Quantity<T>val) {
    	val.getUnit().validateOperationSupport("division");
    	this.validateArithmeticOperands(val, null, false);
    	return new Quantity<>(performBaseArithmetic(val, ArithemeticOperations.DIVIDE),this.getUnit());
    }
     
    public Quantity<T> division(Quantity<T> val,T target) {
    	target.validateOperationSupport("division");
    	this.validateArithmeticOperands(val, target, true);
    	val = division(val).convertTo(target);
    	return val;
    }
    public Quantity<T> subtract(Quantity<T> val,T target) {
    	target.validateOperationSupport("subtract");
    	this.validateArithmeticOperands(val, target,true);
    	val = subtract(val).convertTo(target);
    	return val;
    }
    private void validateArithmeticOperands(Quantity<T> other, T targetUnit, boolean targetRequired) {
        if (other == null)
            throw new IllegalArgumentException("Operand cannot be null");

        if (this.unit.getClass() != other.unit.getClass())
            throw new IllegalArgumentException("Incompatible unit categories");

        if (!Double.isFinite(this.value) || !Double.isFinite(other.value))
            throw new IllegalArgumentException("Values must be finite");

        if (targetRequired && targetUnit == null)
            throw new IllegalArgumentException("Target unit cannot be null");
    }
    private double performBaseArithmetic(Quantity<T> other, ArithemeticOperations operation) {
    	double temp = other.convertTo(this.getUnit()).getValue();
    	double result = operation.compute(this.getValue(),temp);
    	return result;
    }


	 @Override
	 public String toString() {
		return "Quantity [value=" + value + ", unit=" + unit + "]";
	 }

     private enum ArithemeticOperations {
	 ADD((a, b) ->a +b),
	 SUBTRACT((a, b) -> a -b),
	 DIVIDE((a, b) -> {
	 if (b == 0.0) throw new ArithmeticException();
	 return a / b;});
	 
	 private final DoubleBinaryOperator operation;

	 ArithemeticOperations(DoubleBinaryOperator operation) {
	 this.operation = operation;

	 }

	 public double compute(double a, double b) {
	 return operation.applyAsDouble(a, b);}
}

 
}