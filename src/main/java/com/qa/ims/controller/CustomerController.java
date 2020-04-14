package com.qa.ims.controller;

import java.util.List;

import org.apache.log4j.Logger;

import com.qa.ims.persistence.domain.Customer;
import com.qa.ims.services.CrudableServices;
import com.qa.ims.utils.Utils;

/**
 * TODO add input validation for crud methods
 * 
 */
public class CustomerController implements CrudableController<Customer> {

	public static final Logger LOGGER = Logger.getLogger(CustomerController.class);

	private CrudableServices<Customer> customerService;

	public CustomerController(CrudableServices<Customer> customerService) {
		this.customerService = customerService;
	}

	String getInput() {
		return Utils.getInput();
	}

	/**
	 * Reads all customers to the logger
	 */
	@Override
	public List<Customer> readAll() {
		List<Customer> customers = customerService.readAll();
		for (Customer customer : customers) {
			LOGGER.info(customer.toString());
		}
		return customers;
	}

	/**
	 * Creates a customer by taking in user input
	 */
	@Override
	public Customer create() {
		LOGGER.info("Please enter a first name:");
		String firstName = getInput();
		LOGGER.info("Please enter a surname:");
		String surname = getInput();
		Customer customer = customerService.create(new Customer(firstName, surname));
		LOGGER.info("Customer created!");
		return customer;
	}

	/**
	 * Updates an existing customer by taking in user input
	 */
	@Override
	public Customer update() {
		LOGGER.info("Please enter the id of the customer you would like to update:");
		Long id = Long.valueOf(getInput());
		LOGGER.info("Please enter a first name:");
		String firstName = getInput();
		LOGGER.info("Please enter a surname:");
		String surname = getInput();
		Customer customer = customerService.update(new Customer(id, firstName, surname));
		LOGGER.info("Customer updated!");
		return customer;
	}

	/**
	 * Deletes an existing customer by the id of the customer
	 */
	@Override
	public void delete() {
		LOGGER.info("Please enter the id of the customer you would like to delete:");
		Long id = Long.valueOf(getInput());
		customerService.delete(id);
		LOGGER.info("Customer deleted.");
	}

}
