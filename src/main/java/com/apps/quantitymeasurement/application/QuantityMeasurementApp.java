package com.apps.quantitymeasurement.application;

import com.apps.quantitymeasurement.controller.QuantityMeasurementController;
import com.apps.quantitymeasurement.model.QuantityDTO;
import com.apps.quantitymeasurement.repository.QuantityMeasurementCacheRepository;
import com.apps.quantitymeasurement.service.QuantityMeasurementServiceImpl;

public class QuantityMeasurementApp {

    public static void main(String[] args) {

        // Repository
        QuantityMeasurementCacheRepository repository =
                new QuantityMeasurementCacheRepository();

        // Service
        QuantityMeasurementServiceImpl service =
                new QuantityMeasurementServiceImpl(repository);

        // Controller
        QuantityMeasurementController controller =
                new QuantityMeasurementController(service);

        // Example 1: Comparison
        QuantityDTO q1 =
                new QuantityDTO(1, QuantityDTO.LengthUnit.FEET);

        QuantityDTO q2 =
                new QuantityDTO(12, QuantityDTO.LengthUnit.INCHES);

        System.out.println("1 FEET equals 12 INCHES: "
                + controller.compare(q1, q2));

        // Example 2: Conversion
        QuantityDTO converted =
                controller.convert(q1, QuantityDTO.LengthUnit.INCHES);

        System.out.println("1 FEET in INCHES: "
                + converted.value);

        // Example 3: Addition
        QuantityDTO added =
                controller.add(q1, q2);

        System.out.println("Addition result: "
                + added.value + " " + added.unit.getUnitName());

        // Example 4: Subtraction
        QuantityDTO subtracted =
                controller.subtract(q1, q2);

        System.out.println("Subtraction result: "
                + subtracted.value + " " + subtracted.unit.getUnitName());

        // Example 5: Division
        double result =
                controller.divide(q1, new QuantityDTO(2,
                        QuantityDTO.LengthUnit.FEET));

        System.out.println("Division result: " + result);
    }
}