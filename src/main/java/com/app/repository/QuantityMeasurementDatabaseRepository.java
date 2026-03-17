package com.app.repository;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.app.model.QuantityMeasurementEntity;
import com.app.util.ConnectionPool;


public class QuantityMeasurementDatabaseRepository implements IQuantityMeasurementRepository {

    public static final Logger logger = Logger.getLogger(QuantityMeasurementDatabaseRepository.class.getName());

    private static QuantityMeasurementDatabaseRepository instance;

    private ConnectionPool connectionPool;

    private static final String INSERT_QUERY =
            "INSERT INTO quantity_measurement_entity " +
            "(this_value, this_unit, this_measurement_type, that_value, that_unit, " +
            "that_measurement_type, operation, result_value, result_unit, " +
            "result_measurement_type, result_string, is_error, error_message, " +
            "created_at, updated_at) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW())";

    private static final String SELECT_ALL_QUERY =
            "SELECT * FROM quantity_measurement_entity ORDER BY created_at DESC";

    private static final String SELECT_BY_OPERATION =
            "SELECT * FROM quantity_measurement_entity WHERE operation = ? ORDER BY created_at DESC";

    private static final String SELECT_BY_MEASUREMENT_TYPE =
            "SELECT * FROM quantity_measurement_entity WHERE this_measurement_type = ? ORDER BY created_at DESC";

    private static final String DELETE_ALL_QUERY =
            "DELETE FROM quantity_measurement_entity";

    private static final String COUNT_QUERY =
            "SELECT COUNT(*) FROM quantity_measurement_entity";


    private QuantityMeasurementDatabaseRepository() {
        try {
            initializeDatabase();
        } catch (SQLException e) {
            logger.severe(e.getMessage());
        }
    }


    public void initializeDatabase() throws SQLException {
        connectionPool = ConnectionPool.getInstance();
    }


    public static synchronized QuantityMeasurementDatabaseRepository getInstance() {
        if (instance == null) {
            instance = new QuantityMeasurementDatabaseRepository();
        }
        return instance;
    }


    @Override
    public void save(QuantityMeasurementEntity entity) {

        try {
            Connection conn = connectionPool.getConnection();

            PreparedStatement stmt = conn.prepareStatement(INSERT_QUERY);

            stmt.setDouble(1, entity.getThisValue());
            stmt.setString(2, entity.getThisUnit());
            stmt.setString(3, entity.getThisMeasurementType());

            stmt.setDouble(4, entity.getThatValue());
            stmt.setString(5, entity.getThatUnit());
            stmt.setString(6, entity.getThatMeasurementType());

            stmt.setString(7, entity.getOperation());

            stmt.setDouble(8, entity.getResultValue());
            stmt.setString(9, entity.getResultUnit());
            stmt.setString(10, entity.getResultMeasurementType());

            stmt.setString(11, entity.getResultString());
            stmt.setBoolean(12, entity.isError());
            stmt.setString(13, entity.getErrorMessage());

            int rows = stmt.executeUpdate();

            logger.info("Rows affected: " + rows);

            connectionPool.releaseConnection(conn);

        } catch (Exception e) {
            logger.severe(e.getMessage());
        }
    }


    @Override
    public List<QuantityMeasurementEntity> getAllMeasurement() {

        List<QuantityMeasurementEntity> list = new ArrayList<>();

        try {
            Connection conn = connectionPool.getConnection();

            PreparedStatement stmt = conn.prepareStatement(SELECT_ALL_QUERY);

            ResultSet set = stmt.executeQuery();

            while (set.next()) {
                list.add(mapResultToEntity(set));
            }

            connectionPool.releaseConnection(conn);

        } catch (Exception e) {
            logger.severe(e.getMessage());
        }

        return list;
    }


    @Override
    public List<QuantityMeasurementEntity> getMeasurementByOperation(String operation) {

        List<QuantityMeasurementEntity> list = new ArrayList<>();

        try {
            Connection conn = connectionPool.getConnection();

            PreparedStatement stmt = conn.prepareStatement(SELECT_BY_OPERATION);

            stmt.setString(1, operation);

            ResultSet set = stmt.executeQuery();

            while (set.next()) {
                list.add(mapResultToEntity(set));
            }

            connectionPool.releaseConnection(conn);

        } catch (Exception e) {
            logger.severe(e.getMessage());
        }

        return list;
    }


    @Override
    public List<QuantityMeasurementEntity> getMeasurementByType(String type) {

        List<QuantityMeasurementEntity> list = new ArrayList<>();

        try {
            Connection conn = connectionPool.getConnection();

            PreparedStatement stmt = conn.prepareStatement(SELECT_BY_MEASUREMENT_TYPE);

            stmt.setString(1, type);

            ResultSet set = stmt.executeQuery();

            while (set.next()) {
                list.add(mapResultToEntity(set));
            }

            connectionPool.releaseConnection(conn);

        } catch (Exception e) {
            logger.severe(e.getMessage());
        }

        return list;
    }


    @Override
    public int getTotalCount() {

        int count = 0;

        try {
            Connection conn = connectionPool.getConnection();

            PreparedStatement stmt = conn.prepareStatement(COUNT_QUERY);

            ResultSet set = stmt.executeQuery();

            if (set.next()) {
                count = set.getInt(1);
            }

            connectionPool.releaseConnection(conn);

        } catch (Exception e) {
            logger.severe(e.getMessage());
        }

        return count;
    }


    @Override
    public void deleteALl() {

        try {
            Connection conn = connectionPool.getConnection();

            PreparedStatement stmt = conn.prepareStatement(DELETE_ALL_QUERY);

            stmt.executeUpdate();

            connectionPool.releaseConnection(conn);

            logger.info("All records deleted successfully");

        } catch (Exception e) {
            logger.severe(e.getMessage());
        }
    }


    @Override
    public String getPoolStatistic() {

        try {
            return connectionPool.toString();
        } catch (Exception e) {
            logger.severe(e.getMessage());
        }

        return "Pool statistics unavailable";
    }


    @Override
    public void releaseResource() {

        try {
            connectionPool.closeAll();
            logger.info("Connection pool shutdown successfully");
        } catch (Exception e) {
            logger.severe(e.getMessage());
        }
    }


    private QuantityMeasurementEntity mapResultToEntity(ResultSet set) throws SQLException {

        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();

        entity.setThisValue(set.getDouble("this_value"));
        entity.setThisUnit(set.getString("this_unit"));
        entity.setThisMeasurementType(set.getString("this_measurement_type"));

        entity.setThatValue(set.getDouble("that_value"));
        entity.setThatUnit(set.getString("that_unit"));
        entity.setThatMeasurementType(set.getString("that_measurement_type"));

        entity.setOperation(set.getString("operation"));

        entity.setResultValue(set.getDouble("result_value"));
        entity.setResultUnit(set.getString("result_unit"));
        entity.setResultMeasurementType(set.getString("result_measurement_type"));

        entity.setResultString(set.getString("result_string"));
        entity.setError(set.getBoolean("is_error"));
        entity.setErrorMessage(set.getString("error_message"));

        return entity;
    }
}