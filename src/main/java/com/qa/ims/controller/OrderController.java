package com.qa.ims.controller;

import java.math.BigDecimal;
import java.util.List;

import org.apache.log4j.Logger;

import com.qa.ims.persistence.domain.Order;
import com.qa.ims.services.CrudableServices;
import com.qa.ims.utils.Utils;

public class OrderController implements CrudableController<Order> {

	public static final Logger LOGGER = Logger.getLogger(OrderController.class);

	private CrudableServices<Order> orderService;

	public OrderController(CrudableServices<Order> orderService) {
		this.orderService = orderService;
	}

	String getInput() {
		return Utils.getInput();
	}

	@Override
	public List<Order> readAll() {
		List<Order> orders = orderService.readAll();
		for (Order order : orders) {
			LOGGER.info(order.toString());
		}
		return orders;
	}

	@Override
	public Order create() {
		LOGGER.info("Please enter a customer id for the order:");
		Long customerId = Long.valueOf(getInput());
		LOGGER.info("Please enter a total:");
		BigDecimal total = BigDecimal.valueOf(Double.parseDouble(getInput()));
		Order order = orderService.update(new Order(customerId, total));
		LOGGER.info("Order created!");
		return order;
	}

	@Override
	public Order update() {
		LOGGER.info("Please enter the id of the order you would like to update:");
		Long orderId = Long.valueOf(getInput());
		LOGGER.info("Please enter a customer id:");
		Long customerId = Long.valueOf(getInput());
		LOGGER.info("Please enter a total:");
		BigDecimal total = BigDecimal.valueOf(Double.parseDouble(getInput()));
		Order order = orderService.update(new Order(orderId, customerId, total));
		LOGGER.info("Order updated!");
		return order;
	}

	@Override
	public void delete() {
		LOGGER.info("Please enter the id of the order you would like to delete:");
		Long orderId = Long.valueOf(getInput());
		orderService.delete(orderId);
		LOGGER.info("Order deleted.");

	}

}
