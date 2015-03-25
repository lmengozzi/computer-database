package com.excilys.lmengozzi.cdb.persistence;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

/**
 * Provides methods to connect to the mysql database.
 */
public class ConnectionManager {

	private Logger LOGGER = LoggerFactory.getLogger(ConnectionManager.class);

	private static ConnectionManager instance;

	private BoneCP pool = null;

	private ConnectionManager() {
		Properties props = new Properties();
		try {
			props.load(this.getClass()
					.getResourceAsStream("/sql.properties"));
		} catch (IOException e) {
			LOGGER.error("Could not open sql properties file");
		}
		BoneCPConfig config = new BoneCPConfig();
		config.setUsername(props.getProperty("login"));
		config.setPassword(props.getProperty("password"));
		config.setJdbcUrl(props.getProperty("jdbc-url"));
		config.setMinConnectionsPerPartition(5);
		config.setMaxConnectionsPerPartition(10);
		config.setPartitionCount(1);

		try {
			pool = new BoneCP(config);
		} catch (SQLException e) {
			String error = "Could not connect to database";
			LOGGER.error(error);
		}
	}

	public static ConnectionManager getInstance() {
		if (instance == null)
			instance = new ConnectionManager();
		return instance;
	}

	public Connection getConnection() {

		try {
			return pool.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void closeConnection(Connection connection) {
		try {
			connection.close();
		} catch (SQLException e) {
			String error = "Could not close connection to database";
			LOGGER.error(error);
		}
	}
}
