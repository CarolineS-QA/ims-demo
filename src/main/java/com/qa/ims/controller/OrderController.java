package com.qa.ims.controller;

import java.util.ArrayList;
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
		LOGGER.info("");
		return orders;
	}

	// Does not include total, this is worked out in dao.
	@Override
	public Order create() {
		ArrayList<Long> items = new ArrayList<>();
		ArrayList<Integer> qty = new ArrayList<>();
		LOGGER.info("Please enter a customer id for the order:");
		Long customerId = Long.valueOf(getInput());
		String moreItems = "Y";
		while (moreItems.equalsIgnoreCase("Y")) {
			LOGGER.info("Please enter an itemId to add to the order:");
			LOGGER.warn("The item must exist in the item table.");
			Long anItem = Long.valueOf(getInput());
			items.add(anItem);
			LOGGER.info("Please enter the qty of this item for the order: ");
			Integer aQty = Integer.parseInt(getInput());
			qty.add(aQty);
			LOGGER.info("Would you like to add another item to this order? \n(Enter Y to continue adding items)");
			moreItems = getInput();
		}
		Order order = orderService.create(new Order(items, qty, customerId));
		LOGGER.info("Order created!");
		return order;
	}

	@Override
	public Order update() {
		ArrayList<Long> items = new ArrayList<>();
		ArrayList<Integer> qty = new ArrayList<>();
		LOGGER.info("Please enter the id of the order you would like to update:");
		Long orderId = Long.valueOf(getInput());
		LOGGER.info("Please enter a customer id to associate with the order:");
		Long customerId = Long.valueOf(getInput());
		String moreItems = "Y";
		while (moreItems.equalsIgnoreCase("Y")) {
			LOGGER.info("Please enter an itemId to add to the order:");
			LOGGER.warn("The item must exist in the item table.");
			Long anItem = Long.valueOf(getInput());
			items.add(anItem);
			LOGGER.info("Please enter the qty of this item for the order: ");
			Integer aQty = Integer.parseInt(getInput());
			qty.add(aQty);
			LOGGER.info("Would you like to add another item to this order? \n(Enter Y to continue adding items)");
			moreItems = getInput();
		}
		Order order = orderService.update(new Order(items, qty, customerId, orderId));
		LOGGER.info("Order updated! - A new order id has been assigned.");
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
