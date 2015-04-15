package com.excilys.lmengozzi.cdb.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class CompanyMapper implements RowMapper<String> {
	
	private static CompanyMapper instance;

	private CompanyMapper() {

	}

	public static CompanyMapper getInstance() {
		if (instance == null)
			instance = new CompanyMapper();
		return instance;
	}	

	/**
	 * @param resultSet
	 *            A ResultSet obtained from a SQL query of one or more
	 *            computers. The selected attributes must be in the same order
	 *            as the rows in the computer table.
	 * @return A list containing the queried Computer objects.
	 * @throws SQLException
	 */
	public List<String> parseRows(ResultSet resultSet) throws SQLException {
		List<String> lCompanies = new ArrayList<>();
		// next() before the row is parsed, 
		// so stop at last row
		while (!resultSet.isLast()) {
			resultSet.next();
			lCompanies.add(resultSet.getString(1));
		}
		return lCompanies;
	}

	@Override
	public String mapRow(ResultSet resultSet, int arg1) throws SQLException {
		return resultSet.getString(1);
	}

}
