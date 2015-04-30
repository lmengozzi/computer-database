package com.excilys.lmengozzi.cdb.validation;

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