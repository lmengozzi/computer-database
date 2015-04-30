package com.excilys.lmengozzi.cdb.binding.dto;

import com.excilys.lmengozzi.cdb.entity.Company;
import org.springframework.stereotype.Component;

import javax.validation.constraints.*;

@Component
public class CompanyDTO {

	@NotNull
	long id;
	@NotNull
	String name;

	public CompanyDTO() {

	}

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
