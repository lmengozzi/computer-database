package com.excilys.lmengozzi.cdb.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class DateValidator {
	@Autowired
	private StringValidator stringValidator;
	@Autowired
	private MessageSource messageSource;
	public boolean isValid(String date) {
		if (!stringValidator.isValid(date)) {
			return false;
		}
		String pattern = messageSource.getMessage("validation.date.format", null, LocaleContextHolder.getLocale());
		if (!org.apache.commons.validator.routines.DateValidator.getInstance().isValid(date, pattern)) {
			return false;
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		LocalDate localDate = LocalDate.parse(date, formatter);
		return isDayFromMonthAndYear(localDate.getDayOfMonth(), localDate.getMonthValue(), localDate.getYear());
	}

	public boolean isDayFromMonthAndYear(int day, int month, int year) {
		if (month < 1 || month > 12) {
			return false;
		}
		if (day >= 1 && day <= 28) {
			return true;
		}
		if (month != 2) {
			return (day == 29) || (day == 30) || (((day == 31) && !(month == 4 || month == 6 || month == 9 || month == 11)));
		} else {
			if (day == 29) {
				return ((year % 4 == 0) && !(year % 100 == 0)) || ((year % 400) == 0);
			}
		}
		return false;
	}
}