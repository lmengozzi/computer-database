package com.excilys.lmengozzi.cdb.business.validation;

import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Validates Computer attributes. Each validating method returns true if the
 * argument is valid, false otherwise.
 */
public class ComputerValidator {

	private final static String DATE_REGEX = "^(0[1-9]|1[0-9]|2[0-8]|29((?=-([0][13-9]|1[0-2])|(?=-(0[1-9]|1[0-2])-([0-9]{2}(0[48]|[13579][26]|[2468][048])|([02468][048]|[13579][26])00))))|30(?=-(0[13-9]|1[0-2]))|31(?=-(0[13578]|1[02])))-(0[1-9]|1[0-2])-[0-9]{4}$";
	private final static Pattern DATE_PATTERN = java.util.regex.Pattern
			.compile(DATE_REGEX);
	DateTimeFormatter formatter = DateTimeFormatter
			.ofPattern("uuuu/MM/dd HH:mm:ss");

	private static ComputerValidator instance;

	public static ComputerValidator getInstance() {
		if (instance == null)
			instance = new ComputerValidator();
		return instance;
	}

	public boolean name(String name) {
		if (name == null)
			return false;
		if (name.length() > 256 || name.length() == 0)
			return false;
		return true;
	}

	public boolean discontinued(String discontinuedString) {
		return date(discontinuedString);
	}
	
	public boolean introduced(String introducedString) {
		return date(introducedString);
	}

	public boolean company(String manufacturer) {
		if (manufacturer == null)
			return true;
		if (manufacturer.length() > 256)
			return false;
		return true;
	}
	
	private boolean date(String dateString) {
		if (dateString == null)
			return true;
		Matcher matcher = DATE_PATTERN.matcher(dateString);
		if(!matcher.matches())
			return false;
		return true;
	}

}
