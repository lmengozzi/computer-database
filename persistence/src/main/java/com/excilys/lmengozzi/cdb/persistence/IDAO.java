package com.excilys.lmengozzi.cdb.persistence;

import java.util.List;

public interface IDAO<T> {

	T findById(long id);

	List<T> findAll();
	
	List<T> findRange(int start, int end);

	List<T> findPage(int page, int pageSize, String orderBy,
			boolean ascending, String search);
	
	long create(T object);
	
	long getCount();
	
	void delete(long id);

}
