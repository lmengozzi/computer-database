package com.excilys.lmengozzi.cdb.persistence;

import com.excilys.lmengozzi.cdb.entity.Company;

public interface ICompanyManager extends IDAO<Company>{
	Company findByName(String name);

	long create(Company company);

	void delete(long id);
}
