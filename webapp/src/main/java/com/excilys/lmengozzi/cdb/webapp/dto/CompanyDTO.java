package com.excilys.lmengozzi.cdb.webapp.dto;

import com.excilys.lmengozzi.cdb.business.Company;

public class CompanyDTO {
	long id;
	String name;

	public CompanyDTO(Company company) {
		this.id = company.getId();
		this.name = company.getName();
	}

	public long getId() {

		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
