package com.excilys.lmengozzi.cdb.persistence;

import java.util.List;

public interface IDAO<T> {

	T findById(long id);

	List<T> findAll();
	
	List<T> findRange(int start, int end);
	
	void put(T object);
	
	int getCount();
	
	void delete(long id);
}
