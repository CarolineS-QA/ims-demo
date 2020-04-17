package com.qa.ims.persistence.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class OrderTest {

	private Order order;
	private Order other;
	ArrayList<Long> items = new ArrayList<>();
	ArrayList<Integer> qty = new ArrayList<>();

	@Before
	public void setUp() {
		order = new Order(1L, 1L, BigDecimal.valueOf(17.99), items, qty);
		other = new Order(1L, 1L, BigDecimal.valueOf(17.99), items, qty);
	}

	@Test
	public void settersTest() {
		assertNotNull(order.getOrderId());
		assertNotNull(order.getCustomerId());
		assertNotNull(order.getTotal());
		assertNotNull(order.getItems());
		assertNotNull(order.getQty());

		order.setOrderId(null);
		assertNull(order.getOrderId());
		order.setCustomerId(null);
		assertNull(order.getCustomerId());
		order.setTotal(null);
		assertNull(order.getTotal());
		order.setItems(null);
		assertNull(order.getItems());
		order.setQty(null);
		assertNull(order.getQty());

	}

	@Test
	public void equalsWithNull() {
		assertFalse(order.equals(null));
	}

	@Test
	public void equalsWithDifferentObject() {
		assertFalse(order.equals(new Object()));
	}

	@Test
	public void createOrderWithId() {
		assertEquals(1L, order.getOrderId(), 0);
		assertEquals(1L, order.getCustomerId(), 0);
		assertEquals(BigDecimal.valueOf(17.99), order.getTotal());
		assertEquals(items, order.getItems());
		assertEquals(qty, order.getQty());
	}

	@Test
	public void checkEquality() {
		assertTrue(order.equals(order));
	}

	@Test
	public void checkEqualityBetweenDifferentObjects() {
		assertTrue(order.equals(other));
	}

	@Test
	public void customerIdNullButOtherCustomerIdNotNull() {
		order.setCustomerId(null);
		assertFalse(order.equals(other));
	}

	@Test
	public void customerIdNotEqual() {
		other.setCustomerId(3L);
		assertFalse(order.equals(other));
	}

	@Test
	public void checkEqualityBetweenDifferentObjectsNullCustomerId() {
		order.setCustomerId(null);
		other.setCustomerId(null);
		assertTrue(order.equals(other));
	}

	@Test
	public void nullOrderId() {
		order.setOrderId(null);
		assertFalse(order.equals(other));
	}

	@Test
	public void nullOrderIdOnBoth() {
		order.setOrderId(null);
		other.setOrderId(null);
		assertTrue(order.equals(other));
	}

	@Test
	public void otherOrderIdDifferent() {
		other.setOrderId(2L);
		assertFalse(order.equals(other));
	}

	@Test
	public void nullTotal() {
		order.setTotal(null);
		assertFalse(order.equals(other));
	}

	@Test
	public void nullTotalOnBoth() {
		order.setTotal(null);
		other.setTotal(null);
		assertTrue(order.equals(other));
	}

	@Test
	public void otherTotalDifferent() {
		other.setTotal(BigDecimal.valueOf(99.99));
		assertFalse(order.equals(other));
	}

	@Test
	public void constructorWithoutId() {
		Order order = new Order(1L, BigDecimal.valueOf(17.99), items, qty);
		assertNull(order.getOrderId());
		assertNotNull(order.getCustomerId());
		assertNotNull(order.getTotal());
		assertNotNull(order.getItems());
		assertNotNull(order.getQty());
	}

	@Test
	public void hashCodeTest() {
		assertEquals(order.hashCode(), other.hashCode());
	}

	@Test
	public void hashCodeTestWithNull() {
		Order order = new Order(null, null, null, null, null);
		Order other = new Order(null, null, null, null, null);
		assertEquals(order.hashCode(), other.hashCode());
	}

	@Test
	public void toStringTest() {
		String toString = "Order id: 1 |\t Customer id: 1 |\tTotal: 17.99\nItems: [] |\t Qty: []";
		assertEquals(toString, order.toString());
	}

}
