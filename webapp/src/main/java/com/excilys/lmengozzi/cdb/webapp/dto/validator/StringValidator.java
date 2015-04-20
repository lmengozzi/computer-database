package com.excilys.lmengozzi.cdb.webapp.dto.validator;

import org.springframework.stereotype.Component;

@Component
public class StringValidator {

	public boolean isValid(String s) {
		if (s == null) {
			return false;
		}
		return !s.trim().isEmpty();
	}
}