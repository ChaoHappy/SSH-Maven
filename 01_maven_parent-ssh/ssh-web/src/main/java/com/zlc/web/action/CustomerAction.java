package com.zlc.web.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.zlc.domain.Customer;
import com.zlc.service.CustomerService;

public class CustomerAction extends ActionSupport {
	
	private CustomerService customerService;
	
	private Integer cusId;
	
	public CustomerService getCustomerService() {
		return customerService;
	}

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}
	
	
	

	public Integer getCusId() {
		return cusId;
	}

	public void setCusId(Integer cusId) {
		this.cusId = cusId;
	}

	public String save() throws Exception {
		System.out.println("CustomerAction调用了save方法");
		return SUCCESS;
	}
	
	/**
	 * 根据主键查询
	 * @return
	 * @throws Exception
	 */
	public String findOne() throws Exception {
		Customer customer = customerService.findOne(cusId);
		ActionContext.getContext().getValueStack().push(customer);
		return "customerInfo";
	}

	
	
	
}
