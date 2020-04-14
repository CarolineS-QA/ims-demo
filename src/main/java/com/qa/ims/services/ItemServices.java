package com.qa.ims.services;

import java.util.List;

import com.qa.ims.persistence.dao.CrudableDao;
import com.qa.ims.persistence.domain.Item;

public class ItemServices implements CrudableServices<Item> {

	private CrudableDao<Item> itemDao;

	public ItemServices() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Item> readAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Item create(Item t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Item update(Item t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

}
