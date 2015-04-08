package com.excilys.lmengozzi.cdb.persistence;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

/**
 * Provides methods to connect to the mysql database.
 */

@Component
public class ConnectionManager {
	private Logger LOGGER = LoggerFactory.getLogger(ConnectionManager.class);
	private static ConnectionManager instance;
	private MysqlDataSource source = new MysqlDataSource();
	
	public static ConnectionManager getInstance() {
		if (instance == null)
			instance = new ConnectionManager();
		return instance;
	}

	private ConnectionManager() {
		Properties props = new Properties();
		try {
			props.load(this.getClass().getResourceAsStream("/sql.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		source.setServerName(props.getProperty("server"));
		source.setPortNumber(Integer.parseInt(props.getProperty("port")));
		source.setDatabaseName(props.getProperty("database"));

		// Have the datasource convert zero dates to null to avoid
		// java.sql.SQLException about zero dates representation
		source.setZeroDateTimeBehavior("convertToNull");
		source.setUser(props.getProperty("login"));
		source.setPassword(props.getProperty("password"));
	}
	
	public DataSource getDatasource() {
		return source;
	}

	public Connection getConnection() {
		try {
			return source.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	

}
