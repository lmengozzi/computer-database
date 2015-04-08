package com.excilys.lmengozzi.cdb.persistence.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.lmengozzi.cdb.business.Computer;
import com.excilys.lmengozzi.cdb.persistence.ComputerManager;
import com.excilys.lmengozzi.cdb.persistence.IComputerManager;

@Component
@Transactional
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
	public List<Computer> findPage(int page, int pageSize) throws SQLException {
		return manager.findRange(pageSize * page, pageSize * page + pageSize);
	}

	@Override
	public Computer findById(long id) throws SQLException {
		return manager.findById(id);
	}

	@Override
	public List<Computer> findAll() throws SQLException {
		return manager.findAll();
	}

	@Override
	public List<Computer> findRange(int start, int end) throws SQLException {
		return manager.findRange(start, end);
	}

	@Override
	public void put(Computer object) throws SQLException {
		manager.put(object);
	}

	@Override
	public int getCount() throws SQLException {
		return manager.getCount();
	}

	@Override
	public void delete(long id) throws SQLException {
		manager.delete(id);
	}

}
