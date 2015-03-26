package com.excilys.lmengozzi.cdb.webapp;

import com.excilys.lmengozzi.cdb.business.Computer;
import com.excilys.lmengozzi.cdb.business.validation.ComputerValidator;
import com.excilys.lmengozzi.cdb.persistence.ComputerManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet("/addComputer")
public class AddComputerServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected ComputerManager computerManager;

	public AddComputerServlet() {
		this(ComputerManager.getInstance());
	}

	protected AddComputerServlet(ComputerManager computerManager) {
		this.computerManager = computerManager;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		super.doGet(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String name = req.getParameter("computerName").trim();
		String introduced = req.getParameter("introduced").trim();
		introduced = introduced.isEmpty() ? null : introduced;
		String discontinued = req.getParameter("discontinued").trim();
		discontinued = discontinued.isEmpty() ? null : discontinued;
		String companyName = req.getParameter("companyName").trim();
		companyName = companyName == "" ? null : companyName;

		ComputerValidator validator = ComputerValidator.getInstance();
		validator.name(name);
		validator.introduced(introduced);
		validator.discontinued(discontinued);

		if (validator.name(name) && validator.introduced(introduced)
				&& validator.discontinued(discontinued)) {
			System.out.println();
		} else {
			DateTimeFormatter formatter = DateTimeFormatter
					.ofPattern("dd-MM-uuu HH:mm:ss");
			computerManager.put(new Computer(name, LocalDateTime.parse(
					introduced + " 00:00:00", formatter), LocalDateTime.parse(
					discontinued + " 00:00:00", formatter), null));
		}
		req.getRequestDispatcher("addComputer.jsp").forward(req, resp);
	}
}
