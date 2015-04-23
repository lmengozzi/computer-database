package com.excilys.lmengozzi.cdb.persistence.service;

import java.util.List;

import com.excilys.lmengozzi.cdb.persistence.entity.Computer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.excilys.lmengozzi.cdb.persistence.IComputerManager;

import javax.transaction.Transactional;

@Service
@Primary
@Transactional
public class ComputerService implements IComputerService {

	@Autowired
	private IComputerManager manager;

	@Override
	public List<Computer> findPage(int page, int pageSize) {
		return manager.findRange(pageSize * page, pageSize * page + pageSize);
	}

	@Override
	public List<Computer> findPage(int page, int pageSize, String orderBy,
								   boolean ascending, String search) {
		return manager.findPage(page-1, pageSize, orderBy, ascending, search);
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
	public void create(Computer object) {
		manager.create(object);
	}

	@Override
	public long getCount() {
		return manager.getCount();
	}

	@Override
	public void delete(long id) {
		manager.delete(id);
	}
}
