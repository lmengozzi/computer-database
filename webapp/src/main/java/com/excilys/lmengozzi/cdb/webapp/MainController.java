package com.excilys.lmengozzi.cdb.webapp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.excilys.lmengozzi.cdb.business.service.ICompanyService;
import com.excilys.lmengozzi.cdb.business.service.IComputerService;
import com.excilys.lmengozzi.cdb.binding.dto.mapper.ComputerDTOMapper;
import com.excilys.lmengozzi.cdb.binding.dto.validator.ComputerDTOValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.lmengozzi.cdb.entity.Computer;
import com.excilys.lmengozzi.cdb.binding.dto.ComputerDTO;

import javax.validation.Valid;

@Controller
public class MainController {

	final Logger logger = LoggerFactory.getLogger(MainController.class);
	private static final int PAGE_NUMBER = 5;

	@Autowired
	private ComputerDTOMapper computerDTOMapper;
	@Autowired
	private ComputerDTOValidator computerDTOValidator;
	@Autowired
	private IComputerService computerService;
	@Autowired
	private ICompanyService companyService;

	private Map<String, String> orderByStrings = new HashMap<>();

	public MainController() {
		super();
		orderByStrings.put("id", "computer.id");
		orderByStrings.put("name", "computer.name");
		orderByStrings.put("introduced", "computer.introduced");
		orderByStrings.put("discontinued", "computer.discontinued");
		orderByStrings.put("company", "company.name");
	}

	@RequestMapping(value = {"/", "dashboard"},method = RequestMethod.GET)
	public String showDashboard(
			@RequestParam(required = false) Integer pageSize,
			@RequestParam(required = false) Integer page,
			@RequestParam(required = false) String search,
			@RequestParam(required = false) String orderBy,
			@RequestParam(required = false) Boolean asc,
			Model model) {
		logger.trace("GET Dashboard : initializing...");
		long pageAmount;
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
		computerPage = computerService.findPage(page, pageSize, orderBy, asc, search);
		pageAmount = computerService.getCount();
		// if amount of pages doesn't divide amount of items, add a page
		pageAmount += (pageAmount % pageSize) == 0 ? 0 : 1;

		model.addAttribute("computerCount", pageAmount);
		model.addAttribute("items", computerPage.stream()
				.map(ComputerDTO::new).collect(Collectors.toList()));
		model.addAttribute("paginationStart",
				Math.max(1, page - PAGE_NUMBER));
		model.addAttribute(
				"paginationEnd",
				Math.min(page + PAGE_NUMBER,
						pageAmount + 1));
		model.addAttribute("page", page);
		model.addAttribute("pageAmount",
				pageAmount + 1);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("search", search);
		model.addAttribute("orderBy", orderBy);
		model.addAttribute("asc", asc);
		logger.trace("Dashboard ready.");
		return "dashboard";
	}

	@RequestMapping(value = "/addComputer", method = RequestMethod.GET)
	public String showPage(Model model) {
		logger.trace("GET /addComputer : initializing...");
		model.addAttribute("computerDTO", new ComputerDTO());
		model.addAttribute("show", false);
		model.addAttribute("companies", companyService.findAll());
		logger.trace("/addComputer ready.");
		return "addComputer";
	}

	@RequestMapping(value = "/addComputer", method = RequestMethod.POST)
	public String addComputer(
			@ModelAttribute("computerDTO") @Valid ComputerDTO computerDTO,
			BindingResult bindingResult,
			Model model) {
		computerDTOValidator.validate(computerDTO, bindingResult);
		if (!bindingResult.hasErrors()) {
			Computer computer = computerDTOMapper.toComputer(computerDTO);
			computerService.create(computer);
			model.addAttribute("show", true);
			model.addAttribute("showSuccess", true);
			model.addAttribute("message", "Ordinateur ajouté. " + computer.toString());
			model.addAttribute("companies", companyService.findAll());
			model.addAttribute("computerDTO", new ComputerDTO());
		} else {
			model.addAttribute("show", true);
			model.addAttribute("showSuccess", false);
			model.addAttribute("companies", companyService.findAll());
			model.addAttribute("message", bindingResult.getAllErrors());
		}
		return "addComputer";
	}

}