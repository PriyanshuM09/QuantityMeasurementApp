package com.apps.quantitymeasurement.service;

import com.apps.quantitymeasurement.model.QuantityDTO;

public interface IQuantityMeasurementService {

    boolean compare(QuantityDTO q1, QuantityDTO q2);

    QuantityDTO convert(QuantityDTO quantity, QuantityDTO.IMeasurableUnit targetUnit);

    QuantityDTO add(QuantityDTO q1, QuantityDTO q2);

    QuantityDTO subtract(QuantityDTO q1, QuantityDTO q2);

    double divide(QuantityDTO q1, QuantityDTO q2);

}