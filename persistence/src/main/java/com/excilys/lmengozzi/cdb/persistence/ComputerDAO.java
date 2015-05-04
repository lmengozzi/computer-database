package com.excilys.lmengozzi.cdb.persistence;

import com.excilys.lmengozzi.cdb.entity.Company;
import com.excilys.lmengozzi.cdb.entity.Computer;
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
public class ComputerDAO implements IComputerManager {

	public static final Logger LOGGER = LoggerFactory.getLogger(ComputerDAO.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Computer findById(long id) {
		return (Computer) createCriteria().add(Restrictions.eq("id", id)).uniqueResult();
	}

	@Override
	public List<Computer> findPage(int page, int pageSize, String orderBy,
								   boolean ascending, String search) {
		return createCriteria()
				.add(Restrictions.like("name", search, MatchMode.ANYWHERE))
				.setFirstResult(page * pageSize).setMaxResults(pageSize)
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
	public long create(Computer computer) {
		return (long) sessionFactory.getCurrentSession().save(computer);
	}

	@Override
	public void delete(long id) {
		Computer c = Computer.builder().id(id).build();
		sessionFactory.getCurrentSession().delete(c);
	}

	@Override
	public long getCount() {
		return (long) createCriteria().setProjection(Projections.rowCount()).uniqueResult();
	}

	private Criteria createCriteria() {
		return sessionFactory.getCurrentSession().createCriteria(Computer.class);
	}

}
