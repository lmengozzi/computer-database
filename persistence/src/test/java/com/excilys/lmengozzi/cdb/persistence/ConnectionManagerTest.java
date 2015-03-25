package com.excilys.lmengozzi.cdb.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.*;

public class ConnectionManagerTest {

	static ConnectionManager manager;

	@Before
	public void initTest() {
		System.out.println("Initializing pool...");
		manager = ConnectionManager.getInstance();
	}
	
	@Test
	public void connectionTest() throws SQLException {
		for (int i = 0; i < 50; i++) {
			Connection connection = manager.getConnection();
			connection.close();
		}
	}
}
