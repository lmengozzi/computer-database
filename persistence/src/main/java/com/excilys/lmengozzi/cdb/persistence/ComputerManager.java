package com.excilys.lmengozzi.cdb.persistence;

import com.excilys.lmengozzi.cdb.business.Computer;
import com.excilys.lmengozzi.cdb.persistence.mapper.ComputerMapper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
@Primary
public class ComputerManager implements IComputerManager {


	public static final Logger LOGGER = LoggerFactory
			.getLogger(ComputerManager.class);

	private ComputerMapper cp;

	@Autowired
	private ConnectionManager cm;

	//TODO Replace dat crap
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private SessionFactory sessionFactory;

	private static ComputerManager instance;

	public static ComputerManager getInstance() {
		if (instance == null) {
			instance = new ComputerManager();
		}
		return instance;
	}

	public ComputerManager() {
		cp = ComputerMapper.getInstance();
	}

	@Override
	public Computer findById(long id) {
		String sql = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, company.name "
				+ "FROM computer LEFT OUTER JOIN company ON computer.company_id = company.id "
				+ "WHERE computer.id = ? ;";
		Computer computer = jdbcTemplate.queryForObject(sql,
				new Object[]{id}, cp);
		LOGGER.info("Retrieved: " + computer.toString());
		return computer;
	}

	@Override
	public List<Computer> findPage(int start, int end, String orderBy,
								   boolean ascending, String search) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(orderBy);
		if (ascending) {
			stringBuilder.append(" ASC");
		} else {
			stringBuilder.append(" DESC");
		}
		orderBy = stringBuilder.toString();
		System.out.println(orderBy);
		String sql = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, company.name "
				+ "FROM computer LEFT OUTER JOIN company ON computer.company_id = company.id "
				+ "WHERE computer.name LIKE ? "
				+ "ORDER BY computer.name LIMIT ? OFFSET ? ";
		return jdbcTemplate.query(sql, new Object[]{"%" + search + "%",
				orderBy, end - start, start}, cp);
	}

	@Override
	public List<Computer> findRange(int start, int end) {
		String sql = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, company.name "
				+ "FROM computer LEFT OUTER JOIN company ON computer.company_id = company.id "
				+ "ORDER BY company.name, computer.name LIMIT ? OFFSET ?;";
		return jdbcTemplate.query(sql, new Object[]{end - start, start}, cp);
	}

	@Override
	public List<Computer> findAll() {
		return sessionFactory.openSession().createCriteria(Computer.class).list();
	}

	@Override
	public List<Computer> findAllInCompany(String company) {
		String sql = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, company.name "
				+ "FROM computer LEFT OUTER JOIN company ON computer.company_id = company.id "
				+ "WHERE company.name = ?";
		return jdbcTemplate.query(sql, new Object[]{company}, cp);
	}

	@Override
	// TODO put using JdbcTemplate...
	public void create(Computer computer) {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try (Connection connection = cm.getConnection()) {
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
		String sql = "DELETE FROM computer WHERE computer.id = ?";
		jdbcTemplate.update(sql, new Object[]{id});
	}

	@Deprecated
	@Override
	public void delete(long id, Connection connection) {
		PreparedStatement statement = null;
		try {
			statement = connection
					.prepareStatement("DELETE FROM computer WHERE computer.id = ?");
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
		LOGGER.info("Deleted computer " + id);
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
			computer = cp.mapRow(resultSet, 0);
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
