package com.excilys.lmengozzi.cdb.business;

import java.time.LocalDateTime;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ComputerTest {

	Computer computer;

	@Before
	public void Init() {
		computer = new Computer(0, "Skynut");
		computer.setDiscontinuedDate(LocalDateTime.of(2000, 1, 1, 0, 0));
		computer.setIntroducedDate(LocalDateTime.of(1990, 1, 1, 0, 0));
		computer.setCompany(new Company("John Smith"));
		System.out.println();
	}

	@After
	public void Destroy() {
		System.out.println();
	}

	@Test
	public void toStringTest() {
		// Expected result string
		String testString = "Computer name: Skynut, introduced: 1990-01-01T00:00, discontinued: 2000-01-01T00:00, manufacturer: John Smith";
		Assert.assertEquals(computer.toString(), testString);
	}

	@Test
	public void dateTest() {
		computer.getIntroducedDate().isAfter(
				computer.getDiscontinuedDate());
	}
}
