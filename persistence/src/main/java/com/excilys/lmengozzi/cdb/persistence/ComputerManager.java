package com.excilys.lmengozzi.cdb.persistence;

import com.excilys.lmengozzi.cdb.business.Company;
import com.excilys.lmengozzi.cdb.business.Computer;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Primary
public class ComputerManager implements IComputerManager {

	public static final Logger LOGGER = LoggerFactory
			.getLogger(ComputerManager.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Computer findById(long id) {
		return (Computer) createCriteria().add(Restrictions.eq("id", id)).uniqueResult();
	}

	@Override
	public List<Computer> findPage(int start, int end, String orderBy,
								   boolean ascending, String search) {
		return createCriteria()
				.add(Restrictions.like("name", search, MatchMode.ANYWHERE))
				.setFirstResult(start).setMaxResults(end - start)
				.addOrder(ascending ? Order.asc("name") : Order.desc("name"))
				.list();
	}

	@Override
	public List<Computer> findRange(int start, int end) {
		return createCriteria().setFirstResult(start).setMaxResults(end - start).list();
	}

	@Override
	public List<Computer> findAll() {
		return createCriteria().list();
	}

	@Override
	public List<Computer> findAllInCompany(Company company) {
		return createCriteria().add(Restrictions.eq("company_id", company.getId())).list();
	}

	@Override
	// TODO put using JdbcTemplate...
	public void create(Computer computer) {
		sessionFactory.getCurrentSession().save(computer);
	}

	@Override
	public void delete(long id) {
		Computer c = new Computer();
		c.setId(id);
		sessionFactory.getCurrentSession().save(c);
	}

	@Override
	public long getCount() {
		return (long) createCriteria().setProjection(Projections.rowCount()).uniqueResult();
	}

	private Criteria createCriteria() {
		return sessionFactory.getCurrentSession().createCriteria(Computer.class);
	}

}
