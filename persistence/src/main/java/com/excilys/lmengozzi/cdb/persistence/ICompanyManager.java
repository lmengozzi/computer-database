package com.excilys.lmengozzi.cdb.persistence;

public interface ICompanyManager extends IDAO<String>{
	public long findByName(String name);

}
