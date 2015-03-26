package com.excilys.lmengozzi.cdb.webapp;

import java.sql.SQLException;
import java.util.List;

import com.excilys.lmengozzi.cdb.persistence.service.ComputerService;
import com.excilys.lmengozzi.cdb.business.Computer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ComputerBackingBean {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ComputerBackingBean.class);

	private int pageSize = 15;

	private int page = 0;
	private int pageCount;
	private int computerCount;
	private List<Computer> activePage;
	private ComputerService service = ComputerService.getInstance();

	public ComputerBackingBean() {
		init(0);
	}

	public int getPage() {
		return page;
	}

	public int getPageCount() {
		return pageCount;
	}

	public int getComputerCount() {
		return computerCount;
	}

	public List<Computer> getActivePage() {
		return activePage;
	}

	public void init(int n) {
		try {
			computerCount = service.getCount();
			System.out.println("computerCount: " + computerCount);
			if (computerCount % pageSize == 0) {
				pageCount = computerCount / pageSize;
			} else {
				pageCount = computerCount / pageSize + 1;
			}
			n = n < 0 ? 0 : n;
			n = n > pageCount - 1 ? pageCount - 1 : n;
			activePage = service.findPage(n, pageSize);
		} catch (SQLException e) {
			LOGGER.error(e.getMessage());
		}
	}

	public int getPaginationStart() {
		int n = page;
		if (n + 5 > pageCount - 1) {
			n = pageCount - 1 - 5;
		}
		System.out.println("page: " + n);
		System.out.println("pageCount: " + pageCount);
		return n - 5 < 0 ? 0 : n - 5;
	}

	public int getPaginationEnd() {
		int n = page;
		if (n - 5 < 0) {
			n = 5;
		}
		System.out.println("page: " + n);
		System.out.println("pageCount: " + pageCount);
		return n + 5 > pageCount - 1 ? pageCount - 1 : n + 5;
	}

	public int getPageSize() {
		return pageSize;
	}

	public String activatePage(int i) {
		try {
			page = i;
			activePage = service.findPage(i, pageSize);
		} catch (SQLException e) {
			LOGGER.error(e.getMessage());
		}
		return "#";
	}

}