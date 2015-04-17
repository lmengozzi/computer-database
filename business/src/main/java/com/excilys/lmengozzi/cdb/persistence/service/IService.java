package com.excilys.lmengozzi.cdb.persistence.service;

import java.util.List;

public interface IService<T> {

	T findById(long id);

	List<T> findAll();
	
	List<T> findRange(int start, int end);
	
	void create(T object);
	
	long getCount();
	
	void delete(long id);
	
	List<T> findPage(int page, int pageSize);
	
	List<T> findPage(int page, int pageSize, String orderBy,
			boolean ascending, String search);
}
