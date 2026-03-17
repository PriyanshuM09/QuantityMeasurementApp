package com.app.repository;


import java.sql.ResultSet;
import java.util.*;

import com.app.model.QuantityMeasurementEntity;

public interface IQuantityMeasurementRepository {
      void save(QuantityMeasurementEntity enity);
      List<QuantityMeasurementEntity> getAllMeasurement();
      List<QuantityMeasurementEntity> getMeasurementByOperation(String Operation);
      List<QuantityMeasurementEntity> getMeasurementByType(String type);
      int getTotalCount();
      void deleteALl();
      String getPoolStatistic();
      void releaseResource();
     
}