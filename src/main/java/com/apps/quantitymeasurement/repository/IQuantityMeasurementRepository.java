package com.apps.quantitymeasurement.repository;

import java.util.List;
import com.apps.quantitymeasurement.model.QuantityMeasurementEntity;

/**
 * Repository interface for storing quantity measurement operations
 */
public interface IQuantityMeasurementRepository {

    void save(QuantityMeasurementEntity entity);

    List<QuantityMeasurementEntity> findAll();

}