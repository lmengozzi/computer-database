package com.excilys.lmengozzi.cdb.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.*;
import org.springframework.beans.factory.annotation.Autowired;

public class ConnectionManagerTest {

	@Autowired
	static ConnectionManager manager;

	@Before
	@Ignore("Cli resources path incompatible with webapp")
	public void initTest() {
		System.out.println("Initializing pool...");
	}
	
	@Test
	@Ignore("Cli resources path incompatible with webapp")
	public void connectionTest() throws SQLException {
		for (int i = 0; i < 50; i++) {
			Connection connection = manager.getConnection();
			connection.close();
		}
	}
}
