package com.excilys.lmengozzi.cdb;

import com.excilys.lmengozzi.cdb.business.service.ICompanyService;
import com.excilys.lmengozzi.cdb.business.service.IComputerService;
import com.excilys.lmengozzi.cdb.entity.Company;
import com.excilys.lmengozzi.cdb.entity.Computer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.GenericXmlApplicationContext;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The main CLI class.
 */
public class CliApp {

	private static IComputerService computerService;

	// This regex validates a date. This is an alternative to an actual validator.
	private final static String DATE_REGEX = "^(0[1-9]|1[0-9]|2[0-8]|29((?=-([0][13-9]|1[0-2])|(?=-(0[1-9]|1[0-2])-([0-9]{2}(0[48]|[13579][26]|[2468][048])|([02468][048]|[13579][26])00))))|30(?=-(0[13-9]|1[0-2]))|31(?=-(0[13578]|1[02])))-(0[1-9]|1[0-2])-[0-9]{4}$";
	private final static Pattern DATE_PATTERN = java.util.regex.Pattern.compile(DATE_REGEX);

	// TODO Move REST url to somewhere convenient
	private static final String RESTURL = "http://localhost:8080/rest/";

	private static final Logger LOGGER = LoggerFactory.getLogger(CliApp.class);
	private static final Client CLIENT = ClientBuilder.newClient();

	private static final int PAGESIZE = 50;

	public static void main(String[] args) {
		LOGGER.info("CLI Started.");
		GenericXmlApplicationContext appContext = new GenericXmlApplicationContext();
		appContext.load("businessContext.xml");
		appContext.load("bindingContext.xml");
		appContext.refresh();
		computerService = appContext.getBean(IComputerService.class);
		CLI();
		appContext.close();
	}

	/**
	 * CLI Menu.
	 * This method must be called at the end of all the other CLI methods.
	 */
	private static void CLI() {

		List<String> choices = new ArrayList<>();
		choices.add("a");
		choices.add("b");
		choices.add("c");
		choices.add("d");
		choices.add("e");
		choices.add("f");
		choices.add("g");
		choices.add("exit");

		System.out.println();
		System.out.println("		Welcome!");
		System.out.println("	- Main menu, type :");
		System.out.println("	- \"a\" to list computers");
		System.out.println("	- \"b\" to list companies");
		System.out.println("	- \"c\" to create a computer");
		System.out.println("	- \"d\" to delete a company");
		System.out.println("	- \"e\" to delete a company");
		System.out.println("	- Or \"exit\" to... you know, exit.");

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
				createComputer();
				break;
			case "d":
				deleteCompany();
				break;
			case "exit":
				System.exit(0);
		}
	}

	private static void showComputers() {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		ArrayList<String> choices = new ArrayList<>();
		choices.add("y");
		choices.add("n");

		List<Computer> computers;
		int page = 1;
		boolean exit = false;

		System.out.println("List computers (pages of 50 computers) :");

		while (true) {
			// GenericType abstract class is passed to methods with parameterized types as arguments.
			// Passing List<YourObject>.class is tempting but is illegal and won't compile
			computers = CLIENT.target(RESTURL + "computer/page?number=" + page + "&pageSize=" + PAGESIZE + "&orderBy=name&ascending=true")
					.request(MediaType.APPLICATION_JSON).get(new GenericType<List<Computer>>() {
					});
			printComputers(computers);
			if (computers.size() < PAGESIZE) {
				System.out.println("List ended.");
				break;
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
		System.out.println(CLIENT.target(RESTURL + "company/all")
				.request(MediaType.APPLICATION_JSON).get(new GenericType<List<Company>>() {
				}));
		CLI();
	}

	public static void createComputer() {
		Scanner scanner = new Scanner(System.in);
		String name = null;
		LocalDateTime introduced;
		LocalDateTime discontinued;

		// "HH:mm:ss" is unused but is required for LocalDateTime class to parse the scanned line.
		// The user isn't required to supply anything more than the year, month and day, so appending " 00:00:00" to the scanned line is needed.
		// TODO Find another workaround for date scanning with LocalDateTime
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");

		while (name == null) {
			System.out.println("Enter computer name : ");
			name = scanner.nextLine();
		}

		String scannedLine;

		System.out.println("Enter computer introduced date (yyyy/MM/dd) or leave blank : ");

		scannedLine = scanDate(scanner);
		introduced = scannedLine == null ? null : LocalDateTime.parse(scannedLine + " 00:00:00", formatter);

		System.out.println("Enter computer discontinued date (yyyy/MM/dd) or leave blank : ");

		scannedLine = scanDate(scanner);
		discontinued = scannedLine == null ? null : LocalDateTime.parse(scannedLine + " 00:00:00", formatter);

		Computer computer = Computer.builder().name(name).introduced(introduced).discontinued(discontinued).build();

		computerService.create(computer);
		System.out.println("Insertion done.");
		CLI();
	}

	private static void deleteCompany() {

		Scanner scanner = new Scanner(System.in);
		String name = null;

		while (name == null) {
			System.out.println("Enter company name : ");
			name = scanner.nextLine();
		}

		Company company = CLIENT.target(RESTURL + "company/byName" + name).request(MediaType.APPLICATION_JSON_TYPE).get(Company.class);
		CLIENT.target(RESTURL + "computer/").request(MediaType.APPLICATION_JSON_TYPE).delete();
		CLI();
	}

	private static void deleteComputer() {

		Scanner scanner = new Scanner(System.in);
		String name = null;

		while (name == null) {
			System.out.println("Enter computer name : ");
			name = scanner.nextLine();
		}

		Company company = CLIENT.target(RESTURL + "computer/byName" + name).request(MediaType.APPLICATION_JSON_TYPE).get(Company.class);
		CLIENT.target(RESTURL + "computer/").request(MediaType.APPLICATION_JSON_TYPE).delete();
		CLI();
	}

	/**
	 * @param scanner
	 * @return scannedLine the scanned line or null if blank line
	 */
	private static String scanDate(Scanner scanner) {

		String scannedLine;
		Matcher matcher;

		while (true) {
			scannedLine = scanner.nextLine();
			if (scannedLine.isEmpty())
				return null;
			matcher = DATE_PATTERN.matcher(scannedLine);
			if (!matcher.matches())
				System.out
						.println("Please type a date with this pattern (yyyy/MM/dd)");
			else
				return scannedLine;
		}
	}

	private static void printComputers(List<Computer> lComputers) {
		lComputers.forEach(System.out::println);
	}

}
