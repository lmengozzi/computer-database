package com.excilys.lmengozzi.cdb.persistence;

import java.util.List;

import com.excilys.lmengozzi.cdb.entity.Company;
import com.excilys.lmengozzi.cdb.entity.Computer;

public interface IComputerManager extends IDAO<Computer> {

	List<Computer> findAllInCompany(Company company);

}
