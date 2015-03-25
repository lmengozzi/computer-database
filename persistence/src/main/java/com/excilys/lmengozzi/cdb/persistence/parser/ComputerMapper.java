package com.excilys.lmengozzi.cdb.persistence.parser;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.excilys.lmengozzi.cdb.business.Computer;

/**
 * Used to map a computer tuple from SQL ResultSet to a Computer object.
 * Computer attributes in the same order as the rows in the computer table.
 */
public class ComputerMapper {
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
	public List<Computer> parseRows(ResultSet resultSet) throws SQLException {
		List<Computer> lComputers = new ArrayList<>();
		// parseRow uses resultSet.next() before it parses the row, so stop at
		// last row
		while (!resultSet.isLast()) {
			lComputers.add(parseRow(resultSet));
		}
		return lComputers;
	}

	/**
	 * @param resultSet
	 * @return A queried computer object.
	 * @throws SQLException
	 */
	public Computer parseRow(ResultSet resultSet) throws SQLException {
		Computer computer;

		resultSet.next();
		computer = new Computer(resultSet.getLong(1), resultSet.getString(2));
		String field;
		Timestamp timeStamp;
		if ((timeStamp = resultSet.getTimestamp(3)) != null) {
			computer.setIntroducedDate(LocalDateTime.ofEpochSecond(timeStamp.getTime()/1000, timeStamp.getNanos(), ZoneOffset.UTC));
		}
		if ((timeStamp = resultSet.getTimestamp(4)) != null) {
			computer.setDiscontinuedDate(LocalDateTime.ofEpochSecond(timeStamp.getTime()/1000, timeStamp.getNanos(), ZoneOffset.UTC));
		}
		if ((field = resultSet.getString(5)) != null) {
			computer.setManufacturer(field);
		}
		return computer;
	}
}
