package com.qa.ims.services;

import java.util.List;

import com.qa.ims.persistence.dao.CrudableDao;
import com.qa.ims.persistence.domain.Item;

public class ItemServices implements CrudableServices<Item> {

	private CrudableDao<Item> itemDao;

	public ItemServices(CrudableDao<Item> itemDao) {
		this.itemDao = itemDao;
	}

	@Override
	public List<Item> readAll() {
		return itemDao.readAll();
	}

	@Override
	public Item create(Item item) {
		return itemDao.create(item);
	}

	@Override
	public Item update(Item item) {
		return itemDao.update(item);
	}

	@Override
	public void delete(Long id) {
		itemDao.delete(id);

	}

}
