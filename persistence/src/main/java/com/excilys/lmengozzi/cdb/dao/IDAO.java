package com.excilys.lmengozzi.cdb.dao;

import java.sql.SQLException;
import java.util.List;

public interface IDAO<T> {

	T findById(long id) throws SQLException;
	
	List<T> findPage(int page) throws SQLException;

	List<T> findAll() throws SQLException;
	
	void put(T object) throws SQLException;
	
	void delete(long id) throws SQLException;
}
