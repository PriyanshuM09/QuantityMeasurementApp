package com.app.quantitymeasurementapp.service;

import org.springframework.stereotype.Service;

import com.app.quantitymeasurementapp.dto.QuantityDTO;
import com.app.quantitymeasurementapp.dto.QuantityMeasurementDTO;
import com.app.quantitymeasurementapp.dto.QuantityModel;
import com.app.quantitymeasurementapp.entity.QuantityMeasurementEntity;
import com.app.quantitymeasurementapp.exception.CategoryMismatchException;
import com.app.quantitymeasurementapp.exception.InvalidUnitException;
import com.app.quantitymeasurementapp.exception.InvalidUnitMeasurementException;
import com.app.quantitymeasurementapp.exception.QuantityMeasurementException;
import com.app.quantitymeasurementapp.quantity.Quantity;
import com.app.quantitymeasurementapp.repository.QuantityMeasurementRepository;
import com.app.quantitymeasurementapp.unit.IMeasurable;
import com.app.quantitymeasurementapp.unit.LengthUnit;
import com.app.quantitymeasurementapp.unit.TemperatureUnit;
import com.app.quantitymeasurementapp.unit.VolumeUnit;
import com.app.quantitymeasurementapp.unit.WeightUnit;

import java.util.List;
import java.util.logging.*;

@Service
public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {
	// Logger for logging information and errors
	private static final Logger logger = Logger.getLogger(QuantityMeasurementServiceImpl.class.getName());

	private QuantityMeasurementRepository repository;

	// constructor
	public QuantityMeasurementServiceImpl(QuantityMeasurementRepository repository) {
		this.repository = repository;
	}

	private enum Operation {
		ADD, SUBTRACT, MULTIPLY, DIVIDE, COMPARE, CONVERT;
	}

	@Override
	public QuantityMeasurementDTO compare(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {
		// 1. Map
		QuantityModel<IMeasurable> m1 = mapToModel(thisQuantityDTO);
		QuantityModel<IMeasurable> m2 = mapToModel(thatQuantityDTO);

		// validate
		validateModels(m1, m2);

		// 3. Create Domain Objects
		Quantity<IMeasurable> q1 = new Quantity<>(m1.getValue(), m1.getUnit());
		Quantity<IMeasurable> q2 = new Quantity<>(m2.getValue(), m2.getUnit());

		double val1 = q1.convertTo(q1.getUnit());
		double val2 = q2.convertTo(q1.getUnit());

		// 4. Use the equals method from Quantity.java
		boolean isEqual = Double.compare(val1, val2) == 0;

		// 5. Save to Repository
		QuantityMeasurementEntity entity = new QuantityMeasurementEntity(thisQuantityDTO.value, thisQuantityDTO.unit,
				thisQuantityDTO.measurementType, thatQuantityDTO.value, thatQuantityDTO.unit,
				thatQuantityDTO.measurementType, Operation.COMPARE.name(), isEqual ? 1.0 : 0.0, thisQuantityDTO.unit,
				thisQuantityDTO.measurementType, "null", false, "null");
		repository.save(entity);

		return new QuantityMeasurementDTO().from(entity);
	}

	@Override
	public QuantityMeasurementDTO convert(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {
		// 1. Map
		QuantityModel<IMeasurable> m1 = mapToModel(thisQuantityDTO);
		QuantityModel<IMeasurable> m2 = mapToModel(thatQuantityDTO);

		// 3. Create Domain Objects
		Quantity<IMeasurable> q1 = new Quantity<>(m1.getValue(), m1.getUnit());

		double value1 = q1.convertTo(m2.getUnit());

		// 4. save to repository
		QuantityMeasurementEntity entity = new QuantityMeasurementEntity(thisQuantityDTO.value, thisQuantityDTO.unit,
				thisQuantityDTO.measurementType, thatQuantityDTO.value, thatQuantityDTO.unit,
				thatQuantityDTO.measurementType, Operation.CONVERT.name(), value1, thisQuantityDTO.unit,
				thisQuantityDTO.measurementType, "null", false, "null");

		repository.save(entity);

		return new QuantityMeasurementDTO().from(entity);
	}

	@Override
	public QuantityMeasurementDTO add(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {
		return executeArithmetic(thisQuantityDTO, thatQuantityDTO, null, Operation.ADD);
	}

	@Override
	public QuantityMeasurementDTO add(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO,
			QuantityDTO targetUnitDTO) {
		return executeArithmetic(thisQuantityDTO, thatQuantityDTO, targetUnitDTO, Operation.ADD);
	}

	@Override
	public QuantityMeasurementDTO subtract(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {
		return executeArithmetic(thisQuantityDTO, thatQuantityDTO, null, Operation.SUBTRACT);
	}

	@Override
	public QuantityMeasurementDTO subtract(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO,
			QuantityDTO targetUnitDTO) {
		;
		return executeArithmetic(thisQuantityDTO, thatQuantityDTO, targetUnitDTO, Operation.SUBTRACT);
	}

	@Override
	public QuantityMeasurementDTO divide(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) {
		return executeArithmetic(thisQuantityDTO, thatQuantityDTO, null, Operation.DIVIDE);
	}

	@Override
	public List<QuantityMeasurementDTO> getOperationHistory(String operation) {
		validateOperation(operation);
		return new QuantityMeasurementDTO().fromList(repository.findByOperation(operation));
	}

	@Override
	public List<QuantityMeasurementDTO> getMeasurementsByType(String type) {
		validateTypes(type);
		return new QuantityMeasurementDTO().fromList(repository.findByThisMeasurementType(type));
	}

	@Override
	public long getOperationCount(String operation) {
		validateOperation(operation);
		return repository.countByOperationAndIsErrorFalse(operation);
	}

	@Override
	public List<QuantityMeasurementDTO> getErrorHistory() {
		return new QuantityMeasurementDTO().fromList(repository.findAll());
	}

	/**
	 * Helper to map DTO (Strings) to Model (Actual Unit Enums)
	 */
	private QuantityModel<IMeasurable> mapToModel(QuantityDTO dto) {
		if (dto == null) {
			throw new QuantityMeasurementException("Quantity data cannot be null");
		}

		String type = dto.getMeasurementType();
		String unitName = dto.getUnit();
		IMeasurable unit;
		try {
			switch (type) {
			case "LengthUnit":
				unit = LengthUnit.valueOf(unitName);
				break;
			case "VolumeUnit":
				unit = VolumeUnit.valueOf(unitName);
				break;
			case "WeightUnit":
				unit = WeightUnit.valueOf(unitName);
				break;
			case "TemperatureUnit":
				unit = TemperatureUnit.valueOf(unitName);
				break;
			default:
				throw new InvalidUnitMeasurementException("Invalid Measurement Category: " + type);
			}
		} catch (IllegalArgumentException e) {
			throw new InvalidUnitException("Unit '" + unitName + "' is not valid for " + type);
		}
		return new QuantityModel<>(dto.getValue(), unit);
	}

	/*
	 * Validate Operation
	 */
	private void validateOperation(String operation) {
		try {
			switch (operation) {
			case "ADD":
				break;
			case "SUBTRACT":
				break;
			case "COMPARE":
				break;
			case "CONVERT":
				break;
			case "DIVIDE":
				break;
			default:
				throw new UnsupportedOperationException("Invalid Operation: " + operation);
			}
		} catch (IllegalArgumentException e) {
			throw new InvalidUnitException("Unit Type is not valid for");
		}
	}

	/*
	 * Validate Types
	 */
	private void validateTypes(String type) {
		try {
			switch (type) {
			case "LengthUnit":
				break;
			case "VolumeUnit":
				break;
			case "WeightUnit":
				break;
			case "TemperatureUnit":
				break;
			default:
				throw new InvalidUnitMeasurementException("Invalid Measurement Category: " + type);
			}
		} catch (IllegalArgumentException e) {
			throw new InvalidUnitException("Unit Type is not valid for");
		}
	}

	/**
	 * Validation logic as requested in the flow diagram
	 */
	private void validateModels(QuantityModel<?> m1, QuantityModel<?> m2) {
		if (m1 == null || m2 == null) {
			throw new QuantityMeasurementException("Measurement operands cannot be null");
		}

		if (m1.getUnit().getClass() != m2.getUnit().getClass()) {
			throw new CategoryMismatchException("Incompatible types: " + m1.getUnit().getClass().getSimpleName()
					+ " vs " + m2.getUnit().getClass().getSimpleName());
		}

		if (!Double.isFinite(m1.getValue()) || !Double.isFinite(m2.getValue())) {
			throw new QuantityMeasurementException("Invalid numeric value provided");
		}
	}

	/**
	 * This will helper method reuse for all method
	 */
	private QuantityMeasurementDTO executeArithmetic(QuantityDTO d1, QuantityDTO d2, QuantityDTO target,
			Operation opType) {
		// 1. Map
		QuantityModel<IMeasurable> m1 = mapToModel(d1);
		QuantityModel<IMeasurable> m2 = mapToModel(d2);
		QuantityModel<IMeasurable> mT = (target != null) ? mapToModel(target) : null;

		// 2. Validate
		validateModels(m1, m2);
		if (mT != null)
			validateModels(m1, mT);

		// 3. Domain Call (Quantity.java handles the actual Math)
		Quantity<IMeasurable> q1 = new Quantity<>(m1.getValue(), m1.getUnit());
		Quantity<IMeasurable> q2 = new Quantity<>(m2.getValue(), m2.getUnit());

		Quantity<IMeasurable> result;
		if (opType.name().contains("ADD")) {
			result = (mT != null) ? q1.add(q2, mT.getUnit()) : q1.add(q2);
		} else if (opType.name().contains("SUBTRACT")) {
			result = (mT != null) ? q1.subtract(q2, mT.getUnit()) : q1.subtract(q2);
		} else {
			double value = q1.divide(q2);
			result = new Quantity<IMeasurable>(value, q1.getUnit());
		}

		// 4. Extract & Save (Persistence)
		double resVal = result.getValue();
		String resUnit = result.getUnit().toString();

		QuantityMeasurementEntity entity = new QuantityMeasurementEntity(d1.getValue(), d1.getUnit(),
				d1.getMeasurementType(), d2.getValue(), d2.getUnit(), d2.getMeasurementType(), opType.name(), resVal,
				resUnit, d1.getMeasurementType(), "null", false, "null");
		repository.save(entity);

		// 5. Return
		return new QuantityMeasurementDTO().from(entity);
	}
}