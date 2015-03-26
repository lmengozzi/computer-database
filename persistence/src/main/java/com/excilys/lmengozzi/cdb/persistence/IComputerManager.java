package com.excilys.lmengozzi.cdb.persistence;

import java.util.List;

import com.excilys.lmengozzi.cdb.business.Computer;

public interface IComputerManager extends IDAO<Computer> {

	List<Computer> getAllInCompany(String company);
}
