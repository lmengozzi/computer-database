package com.excilys.lmengozzi.cdb.persistence.service;

import java.util.List;

import com.excilys.lmengozzi.cdb.business.Computer;

public interface IService<T> {

	T findById(long id);

	List<T> findAll();
	
	List<T> findRange(int start, int end);
	
	void put(T object);
	
	int getCount();
	
	void delete(long id);
	
	public List<Computer> findPage(int page, int pageSize);
	
	public List<Computer> findPage(int page, int pageSize, String orderBy,
			boolean ascending, String search);
}
