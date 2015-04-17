package com.excilys.lmengozzi.cdb.persistence.service;

import java.util.List;

import com.excilys.lmengozzi.cdb.business.Computer;

public interface IService<T> {

	T findById(long id);

	List<T> findAll();
	
	List<T> findRange(int start, int end);
	
	void put(T object);
	
	long getCount();
	
	void delete(long id);
	
	List<Computer> findPage(int page, int pageSize);
	
	List<Computer> findPage(int page, int pageSize, String orderBy,
			boolean ascending, String search);
}
