package com.excilys.lmengozzi.cdb.binding.dto.validator;

import com.excilys.lmengozzi.cdb.binding.dto.ComputerDTO;
import com.excilys.lmengozzi.cdb.validation.DateValidator;
import com.excilys.lmengozzi.cdb.validation.StringValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ComputerDTOValidator implements Validator {
	@Autowired
	private StringValidator stringValidator;
	@Autowired
	private DateValidator dateValidator;

	@Override
	public boolean supports(Class<?> clazz) {
		return ComputerDTO.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ComputerDTO dto = (ComputerDTO) target;
		if (!stringValidator.isValid(dto.getName())) {
			errors.reject("name", "name.required");
		}
		if (dto.getIntroduced() != null) {
			if (!dto.getIntroduced().trim().isEmpty()) {
				if (!dateValidator.isValid(dto.getIntroduced())) {
					errors.reject("introduced", "introduced.wrong.date");
				}
			}
		}
		if (dto.getDiscontinued() != null) {
			if (!dto.getDiscontinued().trim().isEmpty()) {
				if (!dateValidator.isValid(dto.getDiscontinued())) {
					errors.reject("discontinued", "discontinued.wrong.date");
				}
			}
		}
	}
}