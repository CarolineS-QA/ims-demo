package com.qa.ims.services;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.qa.ims.persistence.dao.CrudableDao;
import com.qa.ims.persistence.domain.Order;

@RunWith(MockitoJUnitRunner.class)
public class OrderServicesTest {

	@Mock
	private CrudableDao<Order> orderDao;

	@InjectMocks
	private OrderServices orderServices;

	@Test
	public void orderServicesCreate() {
		ArrayList<Long> items = new ArrayList<>();
		ArrayList<Integer> qty = new ArrayList<>();
		Order order = new Order(items, qty, 1L);
		orderServices.create(order);
		Mockito.verify(orderDao, Mockito.times(1)).create(order);
	}

	@Test
	public void orderServicesRead() {
		orderServices.readAll();
		Mockito.verify(orderDao, Mockito.times(1)).readAll();
	}

	@Test
	public void orderServicesUpdate() {
		ArrayList<Long> items = new ArrayList<>();
		ArrayList<Integer> qty = new ArrayList<>();
		Order order = new Order(items, qty, 1L, 1L);
		orderServices.update(order);
		Mockito.verify(orderDao, Mockito.times(1)).update(order);
	}

	@Test
	public void orderServicesDelete() {
		orderServices.delete(1L);
		Mockito.verify(orderDao, Mockito.times(1)).delete(1L);
	}

}
