package com.app.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.app.util.ApplicationConfig.ConfigKey;

public class ConnectionPool {
	private static final Logger logger = Logger.getLogger(ConnectionPool.class.getName());
	
	private static ConnectionPool instance;
	private List<Connection> availableConnection;
	private List<Connection> usedConnection;
	private final int poolSize;
	private final String dbUrl;
	private final String dbUserName;
	private final String dbPassword;
	private final String driverClass;
	private final String testQuery;
	
	
	   private ConnectionPool() throws SQLException {
		    ApplicationConfig config = ApplicationConfig.getInstance();
		    poolSize = config.getIntProperty(
		            ConfigKey.DB_POOL_SIZE.getKey(), 5);
		    dbUrl = config.getProperty(ConfigKey.DB_URL.getKey());
		    dbUserName = config.getProperty(ConfigKey.DB_USERNAME.getKey());
		    dbPassword = config.getProperty(ConfigKey.DB_PASSWORD.getKey());
		    driverClass = config.getProperty(ConfigKey.DB_DRIVER_CLASS.getKey());

		    testQuery = config.getProperty(
		            ConfigKey.HIKARI_CONNECTION_TEST_QUERY.getKey(),
		            "SELECT 1");
		    availableConnection = new ArrayList<>();
		    usedConnection = new ArrayList<>();
		    intializeConnection();
		    }
	  public static ConnectionPool getInstance() throws SQLException {
		  if(instance==null) {
			  instance = new ConnectionPool();
		  }
		  return instance;
	  }
	public void intializeConnection() throws SQLException {
		try {
            Class.forName(driverClass);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        for (int i = 0; i < poolSize; i++) {
            availableConnection.add(createConnection());
        }
	}
	private Connection createConnection() throws SQLException{
	        return DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
	 }
	public synchronized Connection getConnection() throws SQLException {
		if(availableConnection.size()>0) {
			Connection conn = availableConnection.remove(0);
			usedConnection.add(conn);
			return conn;
		}
		throw new SQLException("Connection not available");
	}
	public synchronized void releaseConnection(Connection conn) throws SQLException {

	    if (conn == null) {
	        throw new SQLException("Connection is null");
	    }

	    if (usedConnection.remove(conn)) {
	        availableConnection.add(conn);
	        return;
	    }

	    throw new SQLException("Connection not found in used list");
	}
	public boolean validateConnection(Connection connection) {
		try (Statement stmt = connection. createStatement() ) {
		stmt.execute(this.testQuery);
		return true;
		} catch (SQLException e) {
		logger.warning("Connection validation failed: " + e.getMessage());
		return false;
		}
	}
	public synchronized void closeAll() throws SQLException {
		  try {

		        for(Connection conn : availableConnection) {
		            conn.close();
		        }

		        for(Connection conn : usedConnection) {
		            conn.close();
		        }

		        availableConnection.clear();
		        usedConnection.clear();

		    } catch(Exception e) {
		        throw new SQLException("Error closing connections");
		    }
	}
	   public int getAvailableConnectionCount() {
		return availableConnection.size();

		}

		public int getUsedConnectionCount() {
		return usedConnection.size();

		}

		public int getTotalConnectionCount() {
		return availableConnection.size() + usedConnection.size();

		}

		@Override
		public String toString() {
			return "ConnectionPool [availableConnection=" + availableConnection + ", usedConnection=" + usedConnection
					+ ", poolSize=" + poolSize + ", dbUrl=" + dbUrl + ", dbUserName=" + dbUserName + ", dbPassword="
					+ dbPassword + ", driverClass=" + driverClass + ", testQuery=" + testQuery + "]";
		}
		
		public static void main(String[] args) {
			try {
				ConnectionPool pool = ConnectionPool.getInstance();
				Connection conn1 = pool.getConnection();
				logger. info("Validate connection: " + (pool.validateConnection(conn1) ? "Success" : "Failure") );
				logger. info("Available connections after acquiring 1: " + pool.getAvailableConnectionCount());
				logger. info("Used connections after acquiring 1: " + pool.getUsedConnectionCount());
				pool.releaseConnection(conn1);
				logger. info("Available connections after releasing 1: " + pool.getAvailableConnectionCount());
				logger.info("Used connections after releasing 1: " + pool.getUsedConnectionCount());
				pool.closeAll();
			}catch(Exception e) {
				System.out.println(e.getMessage());
			}
		}
}