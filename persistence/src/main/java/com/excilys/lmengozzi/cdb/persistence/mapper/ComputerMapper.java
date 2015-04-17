package com.excilys.lmengozzi.cdb.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.excilys.lmengozzi.cdb.business.Company;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.excilys.lmengozzi.cdb.business.Computer;

/**
 * Used to map a computer tuple from SQL ResultSet to a Computer object.
 * Computer attributes in the same order as the rows in the computer table.
 */
@Component
public class ComputerMapper implements RowMapper<Computer> {
	private static ComputerMapper instance;

	private ComputerMapper() {

	}

	public static ComputerMapper getInstance() {
		if (instance == null)
			instance = new ComputerMapper();
		return instance;
	}

	protected final static DateTimeFormatter FORMATTER = DateTimeFormatter
			.ofPattern("yyyy-MM-dd HH:mm:ss");

	/**
	 * @param resultSet
	 *            A ResultSet obtained from a SQL query of one or more
	 *            computers. The selected attributes must be in the same order
	 *            as the rows in the computer table.
	 * @return A list containing the queried Computer objects.
	 * @throws SQLException
	 */
	public List<Computer> mapRows(ResultSet resultSet) throws SQLException {
		List<Computer> lComputers = new ArrayList<>();
		if (!resultSet.isBeforeFirst()) {
			return lComputers;
		}
		while (!resultSet.isLast()) {
			resultSet.next();
			lComputers.add(mapRow(resultSet, 0));
		}
		return lComputers;
	}

	/**
	 * @param resultSet
	 * @return A queried computer object.
	 * @throws SQLException
	 */
	public Computer mapRow(ResultSet resultSet, int intuile)
			throws SQLException {
		Computer computer = new Computer(resultSet.getLong(1),
				resultSet.getString(2));
		String companyField;
		Timestamp timeStamp;
		if ((timeStamp = resultSet.getTimestamp(3)) != null) {
			computer.setIntroducedDate(LocalDateTime.ofEpochSecond(
					timeStamp.getTime() / 1000, timeStamp.getNanos(),
					ZoneOffset.UTC));
		}
		if ((timeStamp = resultSet.getTimestamp(4)) != null) {
			computer.setDiscontinuedDate(LocalDateTime.ofEpochSecond(
					timeStamp.getTime() / 1000, timeStamp.getNanos(),
					ZoneOffset.UTC));
		}
		if ((companyField = resultSet.getString(5)) != null) {
			computer.setCompany(new Company(companyField));
		}
		return computer;
	}
}
