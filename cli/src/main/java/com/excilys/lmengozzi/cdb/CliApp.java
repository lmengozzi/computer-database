package com.excilys.lmengozzi.cdb;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.excilys.lmengozzi.cdb.business.Computer;
import com.excilys.lmengozzi.cdb.persistence.service.ComputerService;
import com.excilys.lmengozzi.cdb.persistence.service.IComputerService;

@Configuration
@ComponentScan("com.excilys.lmengozzi.cdb.persistence")
@Component
public class CliApp {

	private static IComputerService service;
	
	private final static String DATE_REGEX = "^(0[1-9]|1[0-9]|2[0-8]|29((?=-([0][13-9]|1[0-2])|(?=-(0[1-9]|1[0-2])-([0-9]{2}(0[48]|[13579][26]|[2468][048])|([02468][048]|[13579][26])00))))|30(?=-(0[13-9]|1[0-2]))|31(?=-(0[13578]|1[02])))-(0[1-9]|1[0-2])-[0-9]{4}$";
	private final static Pattern DATE_PATTERN = java.util.regex.Pattern.compile(DATE_REGEX);
	
	private static final Logger logger = LoggerFactory.getLogger(CliApp.class);
	
	private static int pageSize = 50;
	
	public static void main(String[] args) {
	
		logger.info("CLI Started.");
		CLI();
	}

	private static void CLI() {

		AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext(CliApp.class);
		service = appContext.getBean(ComputerService.class);
	
		List<String> choices = new ArrayList<String>();
		choices.add("a");
		choices.add("b");
		choices.add("c");
		choices.add("d");
		choices.add("e");
		choices.add("f");
		choices.add("exit");

		System.out.println();
		System.out.println("		Welcome!");
		System.out.println("	- Main menu, type :");
		System.out.println("	- \"a\" to list computers");
		System.out.println("	- \"b\" to list companies");
		System.out.println("	- \"c\" to show computer details ");
		System.out.println("	- \"d\" to create a computer");
		System.out.println("	- \"e\" to update a computer");
		System.out.println("	- \"f\" to delete a computer");
		System.out.println("	- Or \"exit\" to... you know, exit.");
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		String choice = null;
		while (choice == null || !choices.contains(choice.toLowerCase())) {
			System.out.println("Make your request:");
			choice = scanner.next();
		}
		switch (choice.toLowerCase()) {
		case "a":
			showComputers();
			break;
		case "b":
			showCompanies();
			break;
		case "c":
			showComputerDetails();
			break;
		case "d":
			createComputer();
			break;
		case "e":
			updateComputer();
			break;
		case "f":
			deleteComputer();
			break;
		case "exit":
			appContext.close();
			System.exit(0);
		}
	}

	private static void showComputers() {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		ArrayList<String> choices = new ArrayList<String>();
		choices.add("y");
		choices.add("n");

		System.out.println("List computers (pages of 50 computers) :");

		List<Computer> computers;
		int page = 0;

		boolean exit = false;
		while (true) {
			try {
				computers = service.findPage(page, pageSize);
				if ((computers) == null) {
					System.out.println("List ended.");
					break;
				}
				printComputers(computers);
			} catch (SQLException e) {
				e.printStackTrace();
			}

			String choice = null;
			while (choice == null || !choices.contains(choice.toLowerCase())) {
				System.out.println("\nShow more computers ? [Y/N] :");
				choice = scanner.next();
			}
			switch (choice.toLowerCase()) {
			case "y":
				page++;
				break;
			case "n":
				exit = true;
				break;
			}
			if (exit)
				break;
		}
		CLI();
	}

	private static void showCompanies() {
		// TODO Auto-generated method stub

	}

	private static void showComputerDetails() {
		// TODO Auto-generated method stub

	}

	public static void createComputer() {
		Scanner scanner = new Scanner(System.in);
		String name = null;
		LocalDateTime introduced = null;
		LocalDateTime discontinued = null;

		DateTimeFormatter formatter = DateTimeFormatter
				.ofPattern("uuuu/MM/dd HH:mm:ss");

		while (name == null) {
			System.out.println("Enter computer name : ");
			name = scanner.nextLine();
		}
		
		String scannedLine;

		System.out
				.println("Enter computer introduced date (yyyy/MM/dd) or leave blank : ");
		
		scannedLine = scanDate(scanner);
		introduced = scannedLine == null ? null : LocalDateTime.parse(
				scannedLine + " 00:00:00", formatter);

		System.out
				.println("Enter computer discontinued date (yyyy/MM/dd) or leave blank : ");
		
		scannedLine = scanDate(scanner);
		discontinued = scannedLine == null ? null : LocalDateTime.parse(
				scannedLine + " 00:00:00", formatter);

		Computer computer = new Computer(name);
		computer.setIntroducedDate(introduced);
		computer.setIntroducedDate(discontinued);
		try {
			service.put(computer);
			System.out.println("Insertion done.");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Could not insert: " + e.getMessage());
		}
		CLI();
	}

	private static void updateComputer() {
		// TODO Auto-generated method stub
		
	}

	private static void deleteComputer() {
		// TODO Auto-generated method stub

	}
	
	/**
	 * @param scanner
	 * @return scannedLine the scanned line or null if blank line
	 */
	private static String scanDate(Scanner scanner) {

		String scannedLine;
		Matcher matcher;

		while(true) {
			scannedLine = scanner.nextLine();
			if(scannedLine.isEmpty())
				return null;
			matcher = DATE_PATTERN.matcher(scannedLine);
			if(!matcher.matches())
				System.out.println("Please type a date with this pattern (yyyy/MM/dd)");
			else
				return scannedLine;
		}
	}

	private static void printComputers(List<Computer> lComputers) {
		for (Computer c : lComputers) {
			System.out.println(c);
		}
	}

}
