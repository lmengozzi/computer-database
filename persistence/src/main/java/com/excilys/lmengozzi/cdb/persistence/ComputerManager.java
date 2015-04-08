package com.excilys.lmengozzi.cdb.persistence;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Component;

import java.util.List;

import com.excilys.lmengozzi.cdb.business.Computer;
import com.excilys.lmengozzi.cdb.persistence.mapper.ComputerMapper;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class ComputerManager implements IComputerManager {

	private static ComputerManager instance;

	public static final Logger LOGGER = LoggerFactory
			.getLogger(ComputerManager.class);

	@Autowired
	private ConnectionManager cm;
	private ComputerMapper cp;

	public static ComputerManager getInstance() {
		if (instance == null) {
			instance = new ComputerManager();
		}
		return instance;
	}

	private ComputerManager() {
		cp = ComputerMapper.getInstance();
	}

	@Override
	public Computer findById(long id) {
		Connection connection = cm.getConnection();
		Computer computer = null;
		ResultSet resultSet = null;
		PreparedStatement statement = null;
		try {
			statement = connection
					.prepareStatement("SELECT computer.id, computer.name, computer.introduced, computer.discontinued, company.name "
							+ "FROM computer LEFT OUTER JOIN company ON computer.company_id = company.id "
							+ "WHERE computer.id = ? ;");
			statement.setLong(1, id);
			resultSet = statement.executeQuery();
			connection.close();

			resultSet.next();
			computer = cp.parseRow(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				statement.close();
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		LOGGER.info("Retrieved: " + computer.toString());
		return computer;
	}
	


	@Override
	public List<Computer> findRange(int start, int end) {

		List<Computer> lComputers = null;
		ResultSet resultSet = null;
		PreparedStatement statement = null;
		try (Connection connection = cm.getConnection()) {
			statement = connection
					.prepareStatement("SELECT computer.id, computer.name, computer.introduced, computer.discontinued, company.name "
							+ "FROM computer LEFT OUTER JOIN company ON computer.company_id = company.id "
							+ "ORDER BY company.name, computer.name LIMIT ? OFFSET ?;");
			statement.setInt(1, end - start);
			statement.setInt(2, start);
			resultSet = statement.executeQuery();

			if (!resultSet.isBeforeFirst()) {
				return new ArrayList<Computer>();
			}
			lComputers = cp.parseRows(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();/*
		} finally {
			try {
				statement.close();
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}*/
		}
		return lComputers;
	}

	@Override
	// TODO Tout doux
	public List<Computer> findAll() {
		List<Computer> lComputers = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try(Connection connection = cm.getConnection()) {
			statement = connection
					.prepareStatement("SELECT computer.id, computer.name, computer.introduced, computer.discontinued, company.name "
							+ "FROM computer LEFT OUTER JOIN company ON computer.company_id = company.id "
							+ "ORDER BY company.id");
			resultSet = statement.executeQuery();
			if (!resultSet.isBeforeFirst()) {
				return new ArrayList<Computer>();
			}
			lComputers = cp.parseRows(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				statement.close();
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return lComputers;
	}

	@Override
	public List<Computer> findAllInCompany(String company, Connection connection) {
		List<Computer> lComputers = null;
		ResultSet resultSet = null;
		PreparedStatement statement = null;
		try {
			statement = connection
					.prepareStatement("SELECT computer.id, computer.name, computer.introduced, computer.discontinued, company.name "
							+ "FROM computer LEFT OUTER JOIN company ON computer.company_id = company.id "
							+ "WHERE company.id = ? ;");
			statement.setString(1, company);
			resultSet = statement.executeQuery();
			resultSet.next();
			lComputers = cp.parseRows(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				statement.close();
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		LOGGER.info("Retrieved: " + lComputers.toString());
		return lComputers;
	}

	@Override
	// TODO Set manufacturer
	public void put(Computer computer) {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try(Connection connection = cm.getConnection()) {
			statement = connection.prepareStatement(
					"INSERT INTO computer VALUES(default, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			statement.setString(1, computer.getName());
			if (!(computer.getIntroducedDate() == null)) {
				statement.setDate(2, new Date(computer.getIntroducedDate()
						.toEpochSecond(null)));
			} else {
				statement.setNull(2, Types.DATE);
			}
			if (!(computer.getDiscontinuedDate() == null)) {
				statement.setDate(3, new Date(computer.getDiscontinuedDate()
						.toEpochSecond(null)));
			} else {
				statement.setNull(3, Types.DATE);
			}
			statement.setInt(4, 0);
			statement.executeUpdate();
			resultSet = statement.getGeneratedKeys();
			computer.setId(resultSet.getLong(1));
		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.error("Warning: Failed to insert " + computer.toString());
		} finally {
			try {
				statement.close();
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		LOGGER.info("Inserted: " + computer.toString());
	}
	
	@Override
	public void delete(long id) {
		
		PreparedStatement statement = null;
		try (Connection connection = cm.getConnection();) {
			statement = connection.prepareStatement(
					"DELETE FROM computer WHERE computer.id = ?");
			statement.setLong(1, id);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void delete(long id, Connection connection) {
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(
					"DELETE FROM computer WHERE computer.id = ?");
			statement.setLong(1, id);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public int getCount() {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		int result = 0;
		try (Connection connection = cm.getConnection();) {
			statement = connection
					.prepareStatement("SELECT count(*) FROM computer");
			resultSet = statement.executeQuery();
			resultSet.next();
			result = resultSet.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				resultSet.close();
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	@Override
	public Computer findById(long id, Connection connection) {
		Computer computer = null;
		ResultSet resultSet = null;
		PreparedStatement statement = null;
		try {
			statement = connection
					.prepareStatement("SELECT computer.id, computer.name, computer.introduced, computer.discontinued, company.name "
							+ "FROM computer LEFT OUTER JOIN company ON computer.company_id = company.id "
							+ "WHERE computer.id = ? ;");
			statement.setLong(1, id);
			resultSet = statement.executeQuery();
			resultSet.next();
			computer = cp.parseRow(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				statement.close();
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		LOGGER.info("Retrieved: " + computer.toString());
		return computer;
	}
}
