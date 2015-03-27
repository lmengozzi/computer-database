package com.excilys.lmengozzi.cdb.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.lmengozzi.cdb.business.Computer;
import com.excilys.lmengozzi.cdb.persistence.mapper.CompanyMapper;

// TODO Finish CompanyManager
public class CompanyManager implements ICompanyManager {

	private static CompanyManager instance;

	public static final Logger LOGGER = LoggerFactory
			.getLogger(CompanyManager.class);

	private ConnectionManager cm;
	private CompanyMapper cmap;
	private ComputerManager computerManager;

	public static CompanyManager getInstance() {
		if (instance == null) {
			instance = new CompanyManager();
		}
		return instance;
	}

	private CompanyManager() {
		cm = ConnectionManager.getInstance();
		cmap = CompanyMapper.getInstance();
		computerManager = ComputerManager.getInstance();
	}

	public long findByName(String name) {
		Connection connection = cm.getConnection();
		long companyId = -1;
		PreparedStatement statement;
		try {
			statement = connection.prepareStatement("SELECT company.id "
					+ "FROM company WHERE company.name = ? ;");
			statement.setString(1, name);
			ResultSet resultSet = statement.executeQuery();
			connection.close();
			resultSet.next();
			companyId = resultSet.getLong(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		LOGGER.debug("Retrieved: " + companyId);

		return companyId;
	}

	@Override
	public String findById(long id) {
		String company = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try (Connection connection = cm.getConnection()) {
			statement = connection
					.prepareStatement("SELECT company.name "
							+ "FROM company WHERE company.id = ? ORDER BY company.name ;");
			statement.setLong(1, id);
			resultSet = statement.executeQuery();
			connection.close();
			resultSet.next();
			company = resultSet.getString(1);
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

		LOGGER.debug("Retrieved: " + company.toString());

		return company;
	}

	@Override
	public List<String> findAll() {
		List<String> lCompanies = null;
		ResultSet resultSet = null;
		PreparedStatement statement = null;
		try (Connection connection = cm.getConnection()) {
			statement = connection.prepareStatement("SELECT company.name "
					+ "FROM company ORDER BY company.name;");
			resultSet = statement.executeQuery();
			if (!resultSet.isBeforeFirst()) {
				return new ArrayList<String>();
			}
			lCompanies = cmap.parseRows(resultSet);
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
		return lCompanies;
	}

	@Override
	public List<String> findRange(int start, int end) {
		List<String> lCompanies = null;
		ResultSet resultSet = null;
		PreparedStatement statement = null;
		try (Connection connection = cm.getConnection()) {
			statement = connection
					.prepareStatement("SELECT company.id, company.name "
							+ "FROM computer ORDER BY company.name;");
			statement.setInt(1, end - start);
			statement.setInt(2, start);
			resultSet = statement.executeQuery();

			if (!resultSet.isBeforeFirst()) {
				return new ArrayList<String>();
			}
			lCompanies = cmap.parseRows(resultSet);
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
		LOGGER.debug("Retrieved: " + lCompanies);
		return lCompanies;
	}

	@Override
	public void put(String company) {
		PreparedStatement statement = null;
		try (Connection connection = cm.getConnection()) {
			statement = connection
					.prepareStatement("INSERT INTO company VALUES(default, ?)");
			statement.setString(1, company);
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
		LOGGER.info("Inserted: " + company);
	}

	@Override
	public int getCount() {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		int result = 0;
		try (Connection connection = cm.getConnection()) {
			statement = connection
					.prepareStatement("SELECT count(*) FROM company");
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

	// TODO tout doux
	public void delete(String company) {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<Computer> lComputers = null;
		try (Connection connection = cm.getConnection();) {
			connection.setAutoCommit(false);
			lComputers = computerManager.findAllInCompany(company, connection);
			for(Computer c : lComputers) {
				computerManager.delete(c.getId(), connection);
			}
			statement = connection.prepareStatement(
					"DELETE FROM company WHERE company.name = ?");
			statement.setString(1, company);
			statement.executeUpdate();
		} catch (SQLException e) {
			connection.rollback();
			e.printStackTrace();
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
