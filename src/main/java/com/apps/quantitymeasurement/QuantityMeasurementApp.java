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

 
}