package com.excilys.lmengozzi.cdb.dao;

import java.util.List;

import com.excilys.lmengozzi.cdb.business.Computer;
import com.excilys.lmengozzi.cdb.dao.parser.ComputerMapper;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ComputerManager implements IComputerManager {

	private static ComputerManager instance;
	
	public final Logger logger = LoggerFactory.getLogger(ComputerManager.class);

	private ConnectionManager cm;
	private ComputerMapper cp;

	public static ComputerManager getInstance() {
		if (instance == null)
			instance = new ComputerManager();
		return instance;
	}

	private ComputerManager() {
		cm = ConnectionManager.getInstance();
		cp = ComputerMapper.getInstance();
	}

	@Override
	public Computer findById(long id) throws SQLException {
		Connection connection = cm.getConnection();
		PreparedStatement statement = connection
				.prepareStatement("SELECT computer.id, computer.name, computer.introduced, computer.discontinued, company.name "
						+ "FROM computer LEFT OUTER JOIN company ON computer.company_id = company.id "
						+ "WHERE computer.id = ? ;");
		statement.setLong(1, id);
		ResultSet resultSet = statement.executeQuery();
		connection.close();
		
		resultSet.next();
		Computer computer = cp.parseRow(resultSet);
		
		logger.info("Retrieved: " + computer.toString());
		
		return computer;
	}
	
	@Override
	public List<Computer> findAll() throws SQLException {
		Connection connection = cm.getConnection();
		PreparedStatement statement = connection
				.prepareStatement("SELECT computer.id, computer.name, computer.introduced, computer.discontinued, company.name "
						+ "FROM computer LEFT OUTER JOIN company ON computer.company_id = company.id "
						+ "ORDER BY company.id");
		ResultSet resultSet = statement.executeQuery();

		List<Computer> lComputers = cp.parseRows(resultSet);
		connection.close();
		return lComputers;
	}

	/**
	 * @return A page of 50 computers at index <code>page</code> in the computer
	 *         database, or <code>null</code> if the page is empty.
	 */
	@Override
	public List<Computer> findPage(int page) throws SQLException {

		Connection connection = cm.getConnection();
		PreparedStatement statement = connection
				.prepareStatement("SELECT computer.id, computer.name, computer.introduced, computer.discontinued, company.name "
						+ "FROM computer LEFT OUTER JOIN company ON computer.company_id = company.id "
						+ "ORDER BY company.id LIMIT 50 OFFSET ?;");
		statement.setInt(1, page * 50 + 1);
		ResultSet resultSet = statement.executeQuery();

		if (!resultSet.isBeforeFirst()) {
			return null;
		}
		List<Computer> lComputers = cp.parseRows(resultSet);
		connection.close();
		return lComputers;
	}

	@Override
	public List<Computer> getAllInCompany(String company) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	// TODO Set manufacturer
	public void put(Computer computer) throws SQLException {
		Connection connection = cm.getConnection();
		PreparedStatement statement = connection.prepareStatement(
				"INSERT INTO computer VALUES(default, ?, ?, ?, ?)",
				Statement.RETURN_GENERATED_KEYS);
		statement.setString(1, computer.getName());
		if (!(computer.getIntroducedDate() == null))
			statement.setDate(2, new Date(computer.getIntroducedDate()
					.toEpochSecond(null)));
		else
			statement.setNull(2, Types.DATE);
		if (!(computer.getDiscontinuedDate() == null))
			statement.setDate(3, new Date(computer.getDiscontinuedDate()
					.toEpochSecond(null)));
		else
			statement.setNull(3, Types.DATE);
		statement.setInt(4, 0);
		statement.executeUpdate();
		ResultSet resultSet = statement.getGeneratedKeys();
		computer.setId(resultSet.getLong(1));
		
		logger.info("Inserted: " + computer.toString());

		connection.close();
	}

	@Override
	public void delete(long id) throws SQLException {
		Connection connection = cm.getConnection();
		PreparedStatement statement = connection.prepareStatement(
				"DELETE FROM computer WHERE computer.id = ?",
				Statement.RETURN_GENERATED_KEYS);
		statement.setLong(1, id);
		statement.executeUpdate();
		connection.close();
	}

}
