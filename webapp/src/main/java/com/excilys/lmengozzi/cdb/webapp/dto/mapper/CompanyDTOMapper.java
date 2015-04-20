package com.excilys.lmengozzi.cdb.webapp.dto.mapper;

import com.excilys.lmengozzi.cdb.persistence.entity.Company;
import com.excilys.lmengozzi.cdb.webapp.dto.CompanyDTO;
import org.springframework.stereotype.Component;

@Component
public class CompanyDTOMapper {

	public CompanyDTO toDTO(Company company) {
		if (company == null) {
			return null;
		} else {
			return new CompanyDTO(company);
		}
	}

	public Company toComputer(CompanyDTO dto) {
		if (dto == null) {
			return null;
		}
		Company company = new Company(dto.getId(), dto.getName());


		return company;
	}
}
