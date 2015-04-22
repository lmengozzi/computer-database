package com.excilys.lmengozzi.cdb.persistence;

import java.util.List;

import com.excilys.lmengozzi.cdb.persistence.entity.Company;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class CompanyDAO implements ICompanyManager {

	public static final Logger LOGGER = LoggerFactory
			.getLogger(CompanyDAO.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Company findByName(String name) {
		return (Company) createCriteria().add(Restrictions.eq("name", name)).uniqueResult();
	}

	@Override
	public Company findById(long id) {
		return (Company) createCriteria().add(Restrictions.eq("id", id)).uniqueResult();
	}

	@Override
	public List<Company> findAll() {
		return createCriteria().list();
	}

	@Override
	public List<Company> findRange(int start, int end) {
		return createCriteria().setFirstResult(start).setMaxResults(end - start).list();
	}
	
	@Override
	public List<Company> findPage(int page, int pageSize, String orderBy,
			boolean ascending, String search) {
		return findRange(page * pageSize, page * pageSize + pageSize);
	}

	@Override
	public void create(Company company) {
		sessionFactory.getCurrentSession().save(company);
	}

	@Override
	public long getCount() {
		return (long) createCriteria().setProjection(Projections.rowCount()).uniqueResult();
	}

	@Override
	public void delete(long id) {
		Company company = new Company();
		company.setId(id);
		sessionFactory.getCurrentSession().delete(company);
	}

	private Criteria createCriteria() {
		return sessionFactory.getCurrentSession().createCriteria(Company.class);
	}
}
