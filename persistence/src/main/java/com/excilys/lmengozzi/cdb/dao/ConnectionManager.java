package com.excilys.lmengozzi.cdb.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class ConnectionManager {

	private static ConnectionManager instance;

	private MysqlDataSource source = new MysqlDataSource();

	private ConnectionManager() {
		
	}

	public static ConnectionManager getInstance() {
		if (instance == null)
			instance = new ConnectionManager();
		return instance;
	}

	public Connection getConnection() throws SQLException {


		Properties props = new Properties();
		try {
			props.load(new FileInputStream("src/main/resources/sql.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		source.setServerName(props.getProperty("server"));
		source.setPortNumber(Integer.parseInt(props.getProperty("port")));
		source.setDatabaseName(props.getProperty("database"));
		
		// Have the datasource convert zero dates to null to avoid
		// java.sql.SQLException about zero dates representation
		source.setZeroDateTimeBehavior("convertToNull");
		
		return source.getConnection(props.getProperty("login"),
				props.getProperty("password"));
	}
}
