package com.excilys.lmengozzi.cdb.persistence;

import java.sql.Connection;
import java.util.List;

import com.excilys.lmengozzi.cdb.business.Company;
import com.excilys.lmengozzi.cdb.business.Computer;

public interface IComputerManager extends IDAO<Computer> {

	List<Computer> findAllInCompany(Company company);

}
