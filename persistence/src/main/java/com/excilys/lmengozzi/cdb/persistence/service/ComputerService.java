package com.excilys.lmengozzi.cdb.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.excilys.lmengozzi.cdb.business.Computer;
import com.excilys.lmengozzi.cdb.persistence.ComputerManager;
import com.excilys.lmengozzi.cdb.persistence.IComputerManager;

@Service
@Primary
@Qualifier("computerService")
public class ComputerService implements IComputerService {

	private static ComputerService instance;

	@Autowired
	private IComputerManager manager;

	private ComputerService() {
		manager = ComputerManager.getInstance();
	}

	public static ComputerService getInstance() {
		if (instance == null) {
			instance = new ComputerService();
		}
		return instance;
	}

	/**
	 * @return A list of at most <code>pageSize</code> computers at index
	 *         <code>page</code> in the computer database.
	 */
	@Override
	public List<Computer> findPage(int page, int pageSize) {
		return manager.findRange(pageSize * page, pageSize * page + pageSize);
	}

	@Override
	public List<Computer> findPage(int page, int pageSize, String orderBy,
			boolean ascending, String search) {
		return manager.findPage(page, pageSize, orderBy, ascending, search);
	}

	@Override
	public Computer findById(long id) {
		return manager.findById(id);
	}

	@Override
	public List<Computer> findAll() {
		return manager.findAll();
	}

	@Override
	public List<Computer> findRange(int start, int end) {
		return manager.findRange(start, end);
	}

	@Override
	public void put(Computer object) {
		manager.put(object);
	}

	@Override
	public int getCount() {
		return manager.getCount();
	}

	@Override
	public void delete(long id) {
		manager.delete(id);
	}


}
