package com.apps.quantitymeasurement.model;

/**
 * Data Transfer Object for Quantity input
 */
public class QuantityDTO {

    public double value;
    public IMeasurableUnit unit;

    public QuantityDTO(double value, IMeasurableUnit unit) {
        this.value = value;
        this.unit = unit;
    }

    /**
     * Interface representing measurable unit for DTO
     */
    public interface IMeasurableUnit {
        String getUnitName();
        String getMeasurementType();
    }

    // ---------------- LENGTH UNITS ----------------
    public enum LengthUnit implements IMeasurableUnit {

        FEET, INCHES, YARDS, CENTIMETERS;

        @Override
        public String getUnitName() {
            return this.name();
        }

        @Override
        public String getMeasurementType() {
            return this.getClass().getSimpleName();
        }
    }

    // ---------------- WEIGHT UNITS ----------------
    public enum WeightUnit implements IMeasurableUnit {

        GRAM, KILOGRAM;

        @Override
        public String getUnitName() {
            return this.name();
        }

        @Override
        public String getMeasurementType() {
            return this.getClass().getSimpleName();
        }
    }

    // ---------------- VOLUME UNITS ----------------
    public enum VolumeUnit implements IMeasurableUnit {

        LITRE, MILLILITRE, GALLON;

        @Override
        public String getUnitName() {
            return this.name();
        }

        @Override
        public String getMeasurementType() {
            return this.getClass().getSimpleName();
        }
    }

    // ---------------- TEMPERATURE UNITS ----------------
    public enum TemperatureUnit implements IMeasurableUnit {

        CELSIUS, FAHRENHEIT, KELVIN;

        @Override
        public String getUnitName() {
            return this.name();
        }

        @Override
        public String getMeasurementType() {
            return this.getClass().getSimpleName();
        }
    }
}