package com.excilys.lmengozzi.cdb.webapp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.lmengozzi.cdb.business.Computer;
import com.excilys.lmengozzi.cdb.persistence.service.ComputerService;
import com.excilys.lmengozzi.cdb.webapp.dto.ComputerDTO;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {
	final Logger logger = LoggerFactory.getLogger(DashboardController.class);
	private static final int PAGE_NUMBER = 5;
	@Autowired
	private ComputerService computerService;
	private Map<String, String> orderByStrings = new HashMap<>();

	public DashboardController() {
		super();
		orderByStrings.put("id", "computer.id");
		orderByStrings.put("name", "computer.name");
		orderByStrings.put("introduced", "computer.introduced");
		orderByStrings.put("discontinued", "computer.discontinued");
		orderByStrings.put("company", "company.name");
	}

	@RequestMapping(method = RequestMethod.GET)
	public String showDashboard(
			@RequestParam(required = false) Integer pageSize,
			@RequestParam(required = false) Integer page,
			@RequestParam(required = false) String search,
			@RequestParam(required = false) String orderBy,
			@RequestParam(required = false) Boolean asc, Model model) {
		logger.trace("GET called on /dashboard");
		int pageAmount;
		List<Computer> computerPage;
		if (page == null) {
			page = 1;
		}
		if (pageSize == null) {
			pageSize = 50;
		}
		if (search == null) {
			search = "";
		}
		if (asc == null) {
			asc = true;
		}
		if (orderBy == null) {
			orderBy = "name";
		}
		computerPage = computerService.findPage(page, pageSize, orderBy, asc,
				search);
		pageAmount = computerService.getCount();
		// if amount of pages doesn't divide amount of items, add a page
		pageAmount += (pageAmount % pageSize) == 0 ? 0 : 1;

		model.addAttribute("computerCount", pageAmount);
		model.addAttribute("items", computerPage.stream()
				.map(ComputerDTO::new).collect(Collectors.toList()));
		model.addAttribute("paginationStart",
				Math.max(1, page - PAGE_NUMBER));
		model.addAttribute(
				"paginationFinish",
				Math.min(page + PAGE_NUMBER,
						pageAmount + 1));
		model.addAttribute("currentPageNumber", page + 1);
		model.addAttribute("totalPageNumber",
				pageAmount + 1);
		model.addAttribute("resultsPerPage", pageSize);
		model.addAttribute("search", search);
		model.addAttribute("orderBy", orderBy);
		model.addAttribute("asc", asc);
		logger.trace("GET called on /dashboard : Showing dashboard, response sent");
		return "dashboard";
	}
}