package com.qa.ims.persistence.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

public class OrderTest {

	private Order order;
	private Order other;

	// Need to update this!
	@Before
	public void setUp() {
		order = new Order(1L, 1L, BigDecimal.valueOf(7.99));
		other = new Order(1L, 1L, BigDecimal.valueOf(7.99));
	}

	// Need to update this!
	@Test
	public void settersTest() {
		assertNotNull(order.getId());
		assertNotNull(order.getCustomerId());
		assertNotNull(order.getTotal());

		order.setId(null);
		assertNull(order.getId());
		order.setCustomerId(null);
		assertNull(order.getCustomerId());
		order.setTotal(null);
		assertNull(order.getTotal());

	}

	@Test
	public void equalsWithNull() {
		assertFalse(order.equals(null));
	}

	@Test
	public void equalsWithDifferentObject() {
		assertFalse(order.equals(new Object()));
	}

	// Need to update this!
	@Test
	public void createItemWithId() {
		assertEquals(1L, order.getId(), 0);
		assertEquals(1L, order.getCustomerId(), 0);
		assertEquals(BigDecimal.valueOf(7.99), order.getTotal());
	}

	@Test
	public void checkEquality() {
		assertTrue(order.equals(order));
	}

	@Test
	public void checkEqualityBetweenDifferentObjects() {
		assertTrue(order.equals(other));
	}

	// Add more tests here!
	@Test
	public void toStringTest() {
		String toString = "Item id: 1 |\tName: Testing for Dummies |\tPrice: 7.99 |\tStock: 50";
		assertEquals(toString, order.toString());
	}

}
