package com.apps.quantitymeasurement.model;

import com.apps.quantitymeasurement.core.IMeasurable;

/**
 * Internal POJO used inside service layer
 */
public class QuantityModel<U extends IMeasurable> {

    public double value;
    public U unit;

    public QuantityModel(double value, U unit) {
        this.value = value;
        this.unit = unit;
    }

}