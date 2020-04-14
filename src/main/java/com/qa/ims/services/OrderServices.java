package com.qa.ims.services;

import java.util.List;

import com.qa.ims.persistence.dao.CrudableDao;
import com.qa.ims.persistence.domain.Order;

public class OrderServices implements CrudableServices<Order> {

	private CrudableDao<Order> orderDao;

	public OrderServices(CrudableDao<Order> orderDao) {
		this.orderDao = orderDao;
	}

	@Override
	public List<Order> readAll() {
		return orderDao.readAll();
	}

	@Override
	public Order create(Order order) {
		return orderDao.create(order);
	}

	@Override
	public Order update(Order order) {
		return orderDao.update(order);
	}

	@Override
	public void delete(Long id) {
		orderDao.delete(id);

	}

}
