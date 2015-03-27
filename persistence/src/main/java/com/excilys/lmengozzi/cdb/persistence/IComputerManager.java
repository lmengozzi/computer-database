package com.excilys.lmengozzi.cdb.persistence;

import java.sql.Connection;
import java.util.List;

import com.excilys.lmengozzi.cdb.business.Computer;

public interface IComputerManager extends IDAO<Computer> {

	void delete(long id, Connection connection);

	Computer findById(long id, Connection connection);

	List<Computer> findAllInCompany(String company, Connection connection);
}
