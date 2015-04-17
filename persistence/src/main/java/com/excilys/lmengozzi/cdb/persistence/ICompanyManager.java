package com.excilys.lmengozzi.cdb.persistence;

import com.excilys.lmengozzi.cdb.persistence.entity.Company;

public interface ICompanyManager extends IDAO<Company>{
	Company findByName(String name);

	void create(Company company);

	void delete(long id);
}
