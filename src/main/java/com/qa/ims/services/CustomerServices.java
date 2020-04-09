package com.qa.ims.services;

import java.util.List;

import com.qa.ims.persistence.dao.CrudableDao;
import com.qa.ims.persistence.domain.Customer;

public class CustomerServices implements CrudableServices<Customer> {

	private CrudableDao<Customer> customerDao;
	
	public CustomerServices(CrudableDao<Customer> customerDao) {
		this.customerDao = customerDao;
	}
	
	public List<Customer> readAll() {
		return customerDao.readAll();
	}

	public Customer create(Customer customer) {
		return customerDao.create(customer);
	}

	public Customer update(Customer customer) {
		return customerDao.update(customer);
	}

	public void delete(Long id) {
		customerDao.delete(id);
	}

}
