package com.excilys.lmengozzi.cdb.webapp;

import java.sql.SQLException;
import java.util.List;

import com.excilys.lmengozzi.cdb.persistence.ComputerManager;
import com.excilys.lmengozzi.cdb.business.Computer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ComputerBackingBean {
	
    private static final Logger LOGGER = LoggerFactory.getLogger(ComputerBackingBean.class);
    
    private int pageSize = 15;
    
	private int page;
	private int pageCount;
    private int computerCount;
    private List<Computer> activePage;
    private ComputerManager manager = ComputerManager.getInstance();
	
	public ComputerBackingBean() {
       init(0);
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
            computerCount = manager.getCount();
            if(computerCount  % pageSize == 0) {
                pageCount = computerCount  / pageSize;
            }
            else {
            	pageCount = computerCount  / pageSize + 1;
            }
            n = n < 0 ? 0 : n;
            n = n > pageCount-1 ? pageCount-1 : n;
            activePage = manager.findPage(n);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public int getPaginationStart() {
        if(page + 5 > pageCount-1) {
            page = pageCount-1 - 5;
        }
        return page - 5 < 0 ? 0 : page - 5;
    }

    public int getPaginationEnd() {
        if(page - 5 < 0) {
        	page = 5;
        }
        return page + 5 > pageCount-1 ? pageCount-1 : page+5;
    }
    
    public int getPageSize() {
		return pageSize;
	}

    public String activatePage(int i) {
        try {
        	page = i;
            activePage = manager.findPage(i);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return "#";
    }

}
