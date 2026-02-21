package com.apps.quantitymeasurement;

import java.util.Scanner;

public class QuantityMeasurementApp {

    // Inner class to represent Feet measurement
    public static class Feet {
        private final double value;

        public Feet(double value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {
            // 1. Reference check
            if (this == obj) {
            	return true;
            }

            // 2. Null & type check
            if (obj == null || getClass() != obj.getClass()) {
            	return false;
            }

            // 3. Cast and compare values
            Feet other = (Feet) obj;
            return Double.compare(this.value, other.value) == 0;
        }

        @Override
        public int hashCode() {
            return Double.hashCode(value);
        }
    }
    // Inner class to represent Inches measurement
    public static class Inches {
        private final double value;

        public Inches(double value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
            	return true;
            }
            
            if (obj == null || getClass() != obj.getClass()) {
            	return false;
            }
            
            Inches other = (Inches) obj;
            return Double.compare(this.value, other.value) == 0;
        }

        @Override
        public int hashCode() {
            return Double.hashCode(value);
        }
    }

    
    
    // Static method to demonstrate Feet equality check
    public static void demonstrateFeetEquality() {
        Feet feet1 = new Feet(1.0);
        Feet feet2 = new Feet(1.0);

        System.out.println("Input: 1.0 ft and 1.0 ft");
        System.out.println("Output: Equal (" + feet1.equals(feet2) + ")");
    }

    
    
    // Static method to demonstrate Inches equality check
    public static void demonstrateInchesEquality() {
        Inches inch1 = new Inches(1.0);
        Inches inch2 = new Inches(1.0);

        System.out.println("Input: 1.0 inch and 1.0 inch");
        System.out.println("Output: Equal (" + inch1.equals(inch2) + ")");
    }

    
    // Main method calls demonstrate methods
    public static void main(String[] args) {
        demonstrateFeetEquality();
        demonstrateInchesEquality();
    }
 
}