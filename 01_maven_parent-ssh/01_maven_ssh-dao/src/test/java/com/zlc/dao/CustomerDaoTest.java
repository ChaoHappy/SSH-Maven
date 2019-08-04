package com.zlc.dao;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zlc.domain.Customer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext-*.xml")
public class CustomerDaoTest {
	
	@Autowired
	private CustomerDao customerDao;

	@Test
	public void testFindOne() {
		Customer customer = customerDao.findOne(1);
		System.out.println("姓名："+customer.getCusName());
	}

}
