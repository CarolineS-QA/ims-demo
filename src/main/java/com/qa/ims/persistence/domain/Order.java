package com.qa.ims.persistence.domain;

import java.math.BigDecimal;
import java.util.List;

public class Order {

	private Long orderId;
	private Long customerId;
	private BigDecimal total;
	// item_orders
	private List<Long> items;
	private List<Integer> qty;

	// create new order without total (to calc in dao)
	// reordered to be distinct from item_orders
	public Order(List<Long> items, List<Integer> qty, Long customerId) {
		this.items = items;
		this.qty = qty;
		this.customerId = customerId;
	}

	// create new order with total
	public Order(Long customerId, BigDecimal total, List<Long> items, List<Integer> qty) {
		this.customerId = customerId;
		this.total = total;
		this.items = items;
		this.qty = qty;
	}

	// update new order without total
	public Order(List<Long> items, List<Integer> qty, Long customerId, Long orderId) {
		this.items = items;
		this.qty = qty;
		this.customerId = customerId;
		this.orderId = orderId;
	}

	// full 'order' display for ordersFromResultSet
	public Order(Long orderId, Long customerId, BigDecimal total, List<Long> items, List<Integer> qty) {
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

	public List<Long> getItems() {
		return items;
	}

	public void setItems(List<Long> items) {
		this.items = items;
	}

	public List<Integer> getQty() {
		return qty;
	}

	public void setQty(List<Integer> qty) {
		this.qty = qty;
	}

	@Override
	public String toString() {
		return "Order id: " + orderId + " |\t Customer id: " + customerId + " |\tTotal: " + total + "\nItems: " + items
				+ " |\t Qty: " + qty;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((customerId == null) ? 0 : customerId.hashCode());
		result = prime * result + ((items == null) ? 0 : items.hashCode());
		result = prime * result + ((orderId == null) ? 0 : orderId.hashCode());
		result = prime * result + ((qty == null) ? 0 : qty.hashCode());
		result = prime * result + ((total == null) ? 0 : total.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		if (customerId == null) {
			if (other.customerId != null)
				return false;
		} else if (!customerId.equals(other.customerId))
			return false;
		if (items == null) {
			if (other.items != null)
				return false;
		} else if (!items.equals(other.items))
			return false;
		if (orderId == null) {
			if (other.orderId != null)
				return false;
		} else if (!orderId.equals(other.orderId))
			return false;
		if (qty == null) {
			if (other.qty != null)
				return false;
		} else if (!qty.equals(other.qty))
			return false;
		if (total == null) {
			if (other.total != null)
				return false;
		} else if (!total.equals(other.total))
			return false;
		return true;
	}

}
