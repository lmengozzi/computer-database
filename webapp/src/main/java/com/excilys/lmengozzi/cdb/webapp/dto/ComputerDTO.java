package com.excilys.lmengozzi.cdb.webapp.dto;

import com.excilys.lmengozzi.cdb.persistence.entity.Computer;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Component
@Validated
public class ComputerDTO {

	@NotNull
	private long id;
	@NotNull
	private String name;
	private String introduced;
	private String discontinued;
	private CompanyDTO companyDTO;

	public ComputerDTO() {
	}

	public ComputerDTO(Computer computer) {
		id = computer.getId();
		name = computer.getName();
		if (computer.getIntroduced() != null) {
			introduced = computer.getIntroduced().toLocalDate().toString();
		} else {
			introduced = null;
		}
		if (computer.getDiscontinued() != null) {
			discontinued = computer.getDiscontinued().toLocalDate().toString();
		} else {
			discontinued = null;
		}
		if (computer.getCompany() != null) {
			companyDTO = new CompanyDTO(computer.getCompany());
		} else {
			companyDTO = null;
		}
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

	public String getIntroduced() {
		return introduced;
	}

	public void setIntroduced(String introduced) {
		this.introduced = introduced;
	}

	public String getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(String discontinued) {
		this.discontinued = discontinued;
	}

	public CompanyDTO getCompanyDTO() {
		return companyDTO;
	}

	public void setCompanyDTO(CompanyDTO company) {
		this.companyDTO = company;
	}
}