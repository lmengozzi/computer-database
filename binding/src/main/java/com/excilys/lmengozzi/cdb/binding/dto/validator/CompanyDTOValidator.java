package com.excilys.lmengozzi.cdb.binding.dto.validator;

import com.excilys.lmengozzi.cdb.binding.dto.CompanyDTO;
import com.excilys.lmengozzi.cdb.validation.StringValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class CompanyDTOValidator implements Validator {

	@Autowired
	private StringValidator stringValidator;

	@Override
	public boolean supports(Class<?> clazz) {
		return CompanyDTO.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		CompanyDTO dto = (CompanyDTO) target;
		if (!stringValidator.isValid(dto.getName())) {
			errors.reject("name", "name.required");
		}
	}
}