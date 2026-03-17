package com.app;

import com.app.controller.QuantityMeasurementController;
import com.app.entity.QuantityDTO;
import com.app.model.Quantity;
import com.app.repository.QuantityMeasurementCacheRepository;
import com.app.service.QuantityMeasurementServiceImpl;
import com.app.unit.IMeasurable;
import com.app.unit.LengthUnit;
import com.app.unit.Temperature;
import com.app.unit.VolumneUnit;
import com.app.unit.WeightUnit;

public class QuantityMeasurementApp {
       
	private static QuantityMeasurementApp instance;
	public static QuantityMeasurementController controller;
	public static QuantityMeasurementCacheRepository repository;
	
	private QuantityMeasurementApp() {
		this.repository = QuantityMeasurementCacheRepository.getInstance();
		QuantityMeasurementServiceImpl service = new QuantityMeasurementServiceImpl(this.repository);
		this.controller = new QuantityMeasurementController(service);
	}
	
	public static QuantityMeasurementApp getInstance() {
		if(instance==null) {
			instance = new QuantityMeasurementApp();
		}
		return instance;
	}
    public static<T extends IMeasurable> boolean demonstrateEquality(Quantity<T> quantity1,Quantity<T> quantity2) {
    	if(quantity1.getUnit().getClass()!=quantity2.getUnit().getClass()) throw new IllegalArgumentException("Invalid Unit does Not Match");
    	return quantity1.equals(quantity2);
    }
    
    public static <T extends IMeasurable> Quantity<T> demonstrateConversion(Quantity<T> quantity1,T targetUnit){
        if(quantity1.getUnit().getClass()!=targetUnit.getClass()) throw new IllegalArgumentException("Invalid Unit does Not match");
    	return quantity1.convertTo(targetUnit);
    }
    
    public static <T extends IMeasurable> Quantity<T> demonstrateAddition(Quantity<T> quantity1, Quantity<T> quantity2){
    	if(quantity1.getUnit().getClass()!=quantity2.getUnit().getClass()) throw new IllegalArgumentException("Invalid Unit does Not Match");
    	return quantity1.add(quantity2);
    }
    
    public static <T extends IMeasurable> Quantity<T> demonstrateAddition(Quantity<T> quantity1, Quantity<T> quantity2,T targetUnit){
    	if(targetUnit.getClass()!=quantity1.getUnit().getClass()||targetUnit.getClass()!=quantity2.getUnit().getClass()) throw new IllegalArgumentException("Invalid Unit Does Not Match");
    	return quantity1.add(quantity2,targetUnit);
    }
    
    public static <T extends IMeasurable> Quantity<T> demonstrateDivision(Quantity<T> val1,Quantity<T> val2){
    	if(val1.getUnit().getClass()!=val2.getUnit().getClass()) throw new IllegalArgumentException("Invalid Argument Exception ");
    	return val1.division(val2);
    }
    public static <T extends IMeasurable> Quantity<T> demonstrateDivision(Quantity<T> val1,Quantity<T> val2,T target){
    	if(val1.getUnit().getClass()!=val2.getUnit().getClass()||target.getClass()!=val1.getUnit().getClass()||target.getClass()!=val2.getUnit().getClass()) throw new IllegalArgumentException("Invalid Argument Exception ");
    	return val1.division(val2,target);
    }
    public static <T extends IMeasurable> Quantity<T> demonstrateSubtract(Quantity<T> val1,Quantity<T> val2){
    	if(val1.getUnit().getClass()!=val2.getUnit().getClass()) throw new IllegalArgumentException("Invalid Argument Exception ");
    	return val1.subtract(val2);
    }
    public static <T extends IMeasurable> Quantity<T> demonstrateSubtract(Quantity<T> val1,Quantity<T> val2,T target) {
    	if(val1.getUnit().getClass()!=val2.getUnit().getClass()||target.getClass()!=val1.getUnit().getClass()||target.getClass()!=val2.getUnit().getClass()) throw new IllegalArgumentException("Invalid Argument Exception ");
    	return val1.subtract(val2,target);
    }
    
    public static void main(String[] args) {
//    	
////    	Length Unit
//		Quantity<LengthUnit> len1 = new Quantity<LengthUnit>(12.0, LengthUnit.INCHES);
//		Quantity<LengthUnit> len2 = new Quantity<LengthUnit>(1.0,LengthUnit.FEET);
//		System.out.println("Are The len1 and Len2 equals : "+demonstrateEquality(len1,len2));
//		
//		System.out.println("Conversion Inche To Feet : "+len2.convertTo(LengthUnit.INCHES));
//		System.out.println("Addition of feet and inches : "+len2.add(len1,LengthUnit.FEET));
//		
////		Weight Unit
//	    Quantity<WeightUnit> w1 = new Quantity<WeightUnit>(1.0, WeightUnit.KG);
//	    Quantity<WeightUnit> w2 = new Quantity<WeightUnit>(1000.0,WeightUnit.GRAM);
//	    System.out.println("Are the w1 and w2 equals : "+demonstrateEquality(w1, w2));
//	    
//	    System.out.println("Convert Kg To Gram : "+demonstrateConversion(w1,WeightUnit.GRAM));
//	    System.out.println("Addition of Kg and Gram : "+demonstrateAddition(w1, w2,WeightUnit.KG));
//	    
////	    Volume Unit
//	    
//	    System.out.println("Is Litre equal Litre : "+demonstrateEquality(new Quantity<VolumneUnit>(1.0,VolumneUnit.LITRE),new Quantity<VolumneUnit>(1.0, VolumneUnit.LITRE)));
//	    
//        System.out.println("Is Litre equal Mililitre : "+demonstrateEquality(new Quantity<VolumneUnit>(1.0,VolumneUnit.LITRE),new Quantity<VolumneUnit>(1000.0, VolumneUnit.MILLILITRE)));
//        
//        System.out.println("Is Gallon equal Gallon : "+demonstrateAddition(new Quantity<VolumneUnit>(1.0,VolumneUnit.GALLON),new Quantity<VolumneUnit>(1.0, VolumneUnit.GALLON)));
//        
//        System.out.println("Is Mililitre equal Litre : "+demonstrateEquality(new Quantity<VolumneUnit>(500.0,VolumneUnit.MILLILITRE),new Quantity<VolumneUnit>(0.5, VolumneUnit.LITRE)));
//        
//        System.out.println("Is Litre equal Gallon : "+demonstrateEquality(new Quantity<VolumneUnit>(3.78541,VolumneUnit.LITRE),new Quantity<VolumneUnit>(1.0, VolumneUnit.GALLON)));
//        
//        System.out.println("Addition of Litre with Litre : "+demonstrateAddition(new Quantity<VolumneUnit>(1.0,VolumneUnit.LITRE),new Quantity<VolumneUnit>(2.0, VolumneUnit.LITRE)));
//        
//        System.out.println("Addition of litre with mililitre : "+demonstrateAddition(new Quantity<VolumneUnit>(1.0,VolumneUnit.LITRE),new Quantity<VolumneUnit>(1000.0, VolumneUnit.MILLILITRE)));
//        
////        Arthimetic operation
//        System.out.println("Subtraction of Feet and Inches : "+demonstrateSubtract(new Quantity<LengthUnit>(10.0,LengthUnit.FEET),new Quantity<LengthUnit>(6.0,LengthUnit.INCHES)));
//        
//        System.out.println("Subtraction of Kg and Gram : "+demonstrateSubtract(new Quantity<WeightUnit>(10.0,WeightUnit.KG), new Quantity<WeightUnit>(5000.0, WeightUnit.GRAM)));
//        
//        System.out.println("Subtraction of Litre and mililitre : "+demonstrateSubtract(new Quantity<VolumneUnit>(5.0, VolumneUnit.LITRE), new Quantity<VolumneUnit>(500.0,VolumneUnit.MILLILITRE)));
//        
//        System.out.println("Subtraction of feet with inche to inche : "+demonstrateSubtract(new Quantity<LengthUnit>(10.0, LengthUnit.FEET),new Quantity<LengthUnit>(6.0,LengthUnit.INCHES),LengthUnit.INCHES));
//        
//        System.out.println("Subtraction of kg with gram to gram : "+demonstrateSubtract(new Quantity<WeightUnit>(10.0,WeightUnit.KG),new Quantity<WeightUnit>(5000.0,WeightUnit.GRAM),WeightUnit.GRAM));
//        
//        System.out.println("Division of Feet with feet : "+demonstrateDivision(new Quantity<LengthUnit>(10.0, LengthUnit.FEET), new Quantity<LengthUnit>(2.0, LengthUnit.FEET)));
//       
//        System.out.println("Division of inche with feet : "+demonstrateDivision(new Quantity<LengthUnit>(24.0,LengthUnit.INCHES),new Quantity<LengthUnit>(2.0,LengthUnit.FEET)));
//        
//        //Centralized Arithemetic Operation
//        
//        System.out.println("Centralized Addition feet to inches : "+demonstrateAddition(new Quantity<LengthUnit>(1.0, LengthUnit.FEET),new Quantity<LengthUnit>(12.0, LengthUnit.INCHES)));
//        
//        System.out.println("Centralized Addition of kg and gram to Gram : "+demonstrateAddition(new Quantity<WeightUnit>(10.0,WeightUnit.KG ), new Quantity<WeightUnit>(5000.0, WeightUnit.GRAM),WeightUnit.GRAM));
//        
//        System.out.println("Centralized Substraction of Feet and inches : "+demonstrateSubtract(new Quantity<LengthUnit>(10.0,LengthUnit.FEET),new Quantity<LengthUnit>(6.0,LengthUnit.INCHES)));
//        
//        System.out.println("centralized Subtraction Litre and Millitre : "+demonstrateSubtract(new Quantity<VolumneUnit>(5.0,VolumneUnit.LITRE), new Quantity<VolumneUnit>(2.0,VolumneUnit.LITRE),VolumneUnit.MILLILITRE));
//        
//        System.out.println("Centralized Division Feet by Feet : "+demonstrateDivision(new Quantity<LengthUnit>(10.0,LengthUnit.FEET), new Quantity<LengthUnit>(2.0,LengthUnit.FEET)));
//        
//        System.out.println("Centralized Division Inches by feet : "+demonstrateDivision(new Quantity<LengthUnit>(24.0,LengthUnit.INCHES), new Quantity<LengthUnit>(2.0,LengthUnit.FEET)));
//        
//        //Temperature 
//        
//     // Equality Demonstrxation
//        Quantity<Temperature> temp1 = new Quantity<>(0.0,Temperature.CELSIUS);
//        Quantity<Temperature> temp2 = new Quantity<>(32.0, Temperature.FAHRENHEIT);
//        System.out.println("0C equals 32F: "+ temp1.equals(temp2));
//        
//     // Conversion Demonstration
//        Quantity<Temperature> celsius = new Quantity<>(100.0,Temperature.CELSIUS);
//        System.out.println("100C =" + celsius.getUnit().convertTo(100.0,Temperature.FAHRENHEIT)+"F");
//        
//        try {
//        	celsius.add(new Quantity<>(50.0, Temperature.CELSIUS));
//        	} catch (UnsupportedOperationException e) {
//        	System.out.println("Cannot add absolute temperatures: " +e.getMessage());
//
//        	}
    	
    	QuantityMeasurementApp temp = getInstance();
    	QuantityDTO q1 = temp.controller.performDivision(new QuantityDTO(10,"FEET","LENGTH"),new QuantityDTO(2,"FEET","LENGTH"));
    	System.out.println(q1.toString());
	}
}