package com.excilys.lmengozzi.cdb.persistence.service;

import java.sql.SQLException;
import java.util.List;

import com.excilys.lmengozzi.cdb.business.Computer;

public interface IService<T> {

	T findById(long id) throws SQLException;

	List<T> findAll() throws SQLException;
	
	List<T> findRange(int start, int end) throws SQLException;
	
	void put(T object) throws SQLException;
	
	int getCount() throws SQLException;
	
	void delete(long id) throws SQLException;
	
	public List<Computer> findPage(int page, int pageSize) throws SQLException;
}
