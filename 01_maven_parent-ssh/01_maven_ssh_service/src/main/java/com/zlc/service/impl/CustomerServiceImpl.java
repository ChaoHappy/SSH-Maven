package com.zlc.service.impl;

import com.zlc.dao.CustomerDao;
import com.zlc.domain.Customer;
import com.zlc.service.CustomerService;

public class CustomerServiceImpl implements CustomerService {
	
	private CustomerDao customerDao;

	public CustomerDao getCustomerDao() {
		return customerDao;
	}

	public void setCustomerDao(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}

	@Override
	public Customer findOne(Integer cusId) {
		Customer customer = customerDao.findOne(cusId);
		return customer;
	}
	

}
