package com.excilys.lmengozzi.cdb.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.lmengozzi.cdb.business.Computer;
import com.excilys.lmengozzi.cdb.persistence.mapper.CompanyMapper;

// TODO Finish CompanyManager
@Service
@Primary
@Transactional
public class CompanyManager implements ICompanyManager {

	private static CompanyManager instance;

	public static final Logger LOGGER = LoggerFactory
			.getLogger(CompanyManager.class);

	@Autowired
	private ConnectionManager cm;

	@Autowired
	private ComputerManager computerManager;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private CompanyMapper cmap;

	public static CompanyManager getInstance() {
		if (instance == null) {
			instance = new CompanyManager();
		}
		return instance;
	}

	public CompanyManager() {
		cmap = CompanyMapper.getInstance();
	}

	@Override
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
		LOGGER.debug("Retrieved: " + company);
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
	public List<String> findPage(int page, int pageSize, String orderBy,
			boolean ascending, String search) {
		return findRange(page * pageSize, page * pageSize + pageSize);
	}

	@Override
	// TODO Save the company id
	public void create(String company) {
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

	@Override
	public void delete(String company) {
		List<Computer> lComputers;
		lComputers = computerManager.findAllInCompany(company);
		for (Computer c : lComputers) {
			computerManager.delete(c.getId());
		}
		String sql = "DELETE FROM company WHERE company.name = ?";
		jdbcTemplate.update(sql, new Object[] { company });
	}

	@Override
	public void delete(long id) {
		// TODO Auto-generated method stub

	}
}
