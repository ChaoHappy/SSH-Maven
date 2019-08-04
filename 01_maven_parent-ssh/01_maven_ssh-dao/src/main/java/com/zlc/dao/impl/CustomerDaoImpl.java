package com.zlc.dao.impl;

import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.zlc.dao.CustomerDao;
import com.zlc.domain.Customer;

public class CustomerDaoImpl extends HibernateDaoSupport implements CustomerDao{

	@Override
	public Customer findOne(Integer cusId) {
		Customer customer = null;
		try {
			customer = this.getHibernateTemplate().get(Customer.class, cusId);
		} catch (Exception e) {
			System.out.println(e);
		}
		return customer;
	}

}
