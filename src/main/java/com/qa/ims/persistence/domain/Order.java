package com.qa.ims.persistence.domain;

import java.math.BigDecimal;

public class Order {

	private Long orderId;
	private Long customerId;
	private BigDecimal total;
	// item_orders
	private Long itemId;
	private Integer qty;

	// for order table
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
	public Order(Long orderId, Long itemId, Integer qty) {
		this.orderId = orderId;
		this.itemId = itemId;
		this.qty = qty;
	}

	public Order(Long orderId, Long customerId, Long itemId, Integer qty) {
		this.orderId = orderId;
		this.customerId = customerId;
		this.itemId = itemId;
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

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	@Override
	public String toString() {
		return "Order id: " + orderId + " |\t Customer id: " + customerId + " |\tTotal: " + total + "\nItemId: "
				+ itemId + " |\t Qty: " + qty;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((customerId == null) ? 0 : customerId.hashCode());
		result = prime * result + ((itemId == null) ? 0 : itemId.hashCode());
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
		if (itemId == null) {
			if (other.itemId != null)
				return false;
		} else if (!itemId.equals(other.itemId))
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
