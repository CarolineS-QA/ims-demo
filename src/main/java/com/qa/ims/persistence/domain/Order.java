package com.qa.ims.persistence.domain;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Order {

	private Long orderId;
	private Long customerId;
	private BigDecimal total;
	// item_orders
	private ArrayList<Long> items;
	private ArrayList<Integer> qty;

	// for SQL order table
	public Order(Long customerId, BigDecimal total) {
		this.customerId = customerId;
		this.total = total;
	}

	public Order(Long id, Long customerId, BigDecimal total) {
		this.orderId = id;
		this.customerId = customerId;
		this.total = total;
	}

	// for item_orders table
	public Order(Long orderId, ArrayList<Long> items, ArrayList<Integer> qty) {
		this.orderId = orderId;
		this.items = items;
		this.qty = qty;
	}

	// new order without total (to calc in dao), reordered to be distinct from
	// item_orders
	public Order(ArrayList<Long> items, ArrayList<Integer> qty, Long customerId) {
		this.items = items;
		this.qty = qty;
		this.customerId = customerId;
	}

	// new order with total
	public Order(Long customerId, BigDecimal total, ArrayList<Long> items, ArrayList<Integer> qty) {
		this.customerId = customerId;
		this.total = total;
		this.items = items;
		this.qty = qty;
	}

	// full 'order' for update
	public Order(Long orderId, Long customerId, BigDecimal total, ArrayList<Long> items, ArrayList<Integer> qty) {
		this.orderId = orderId;
		this.customerId = customerId;
		this.total = total;
		this.items = items;
		this.qty = qty;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public ArrayList<Long> getItems() {
		return items;
	}

	public void setItems(ArrayList<Long> items) {
		this.items = items;
	}

	public ArrayList<Integer> getQty() {
		return qty;
	}

	public void setQty(ArrayList<Integer> qty) {
		this.qty = qty;
	}

	@Override
	public String toString() {
		return "Order id: " + orderId + " |\t Customer id: " + customerId + " |\tTotal: " + total + "\nItems: " + items
				+ " |\t Qty: " + qty;
	}

}
