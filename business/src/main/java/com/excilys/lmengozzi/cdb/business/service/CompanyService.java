package com.excilys.lmengozzi.cdb.business.service;

import com.excilys.lmengozzi.cdb.persistence.ICompanyManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import com.excilys.lmengozzi.cdb.entity.Company;

import java.util.List;

@Service
@Primary
@Transactional
public class CompanyService implements ICompanyService {

	@Autowired
	ICompanyManager companyManager;

	public Company findByName(String name) {
		return companyManager.findByName(name);
	}

	@Override
	public long create(Company company) {
		return companyManager.create(company);
	}

	@Override
	public void delete(long id) {
		companyManager.delete(id);
	}

	@Override
	public Company findById(long id) {
		return companyManager.findById(id);
	}

	@Override
	public List<Company> findAll() {
		return companyManager.findAll();
	}

	@Override
	public List<Company> findRange(int start, int end) {
		return companyManager.findRange(start, end);
	}

	@Override
	public List<Company> findPage(int page, int pageSize) {
		return companyManager.findRange(page * pageSize, page * pageSize + pageSize);
	}

	@Override
	public List<Company> findPage(int page, int pageSize, String orderBy, boolean ascending, String search) {
		return companyManager.findPage(page, pageSize, orderBy, ascending, search);
	}

	@Override
	public long getCount() {
		return companyManager.getCount();
	}
}
