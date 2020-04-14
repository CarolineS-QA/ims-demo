package com.qa.ims.controller;

import java.math.BigDecimal;
import java.util.List;

import org.apache.log4j.Logger;

import com.qa.ims.persistence.domain.Item;
import com.qa.ims.services.CrudableServices;
import com.qa.ims.utils.Utils;

// TODO add input validation for crud methods

public class ItemController implements CrudableController<Item> {

	public static final Logger LOGGER = Logger.getLogger(ItemController.class);

	private CrudableServices<Item> itemService;

	public ItemController(CrudableServices<Item> itemService) {
		this.itemService = itemService;
	}

	String getInput() {
		return Utils.getInput();
	}

	@Override
	public List<Item> readAll() {
		List<Item> items = itemService.readAll();
		for (Item item : items) {
			LOGGER.info(item.toString());
		}
		return items;
	}

	@Override
	public Item create() {
		LOGGER.info("Please enter an item name:");
		String itemName = getInput();
		LOGGER.info("Please enter a price:");
		BigDecimal price = BigDecimal.valueOf(Double.parseDouble(getInput()));
		Item item = itemService.create(new Item(itemName, price));
		LOGGER.info("Item created!");
		return item;
	}

	@Override
	public Item update() {
		LOGGER.info("Please enter the id of the item you would like to update:");
		Long id = Long.valueOf(getInput());
		LOGGER.info("Please enter an item name:");
		String itemName = getInput();
		LOGGER.info("Please enter a price:");
		BigDecimal price = BigDecimal.valueOf(Double.parseDouble(getInput()));
		Item item = itemService.update(new Item(id, itemName, price));
		LOGGER.info("Item updated!");
		return item;
	}

	@Override
	public void delete() {
		LOGGER.info("Please enter the id of the item you would like to delete:");
		Long id = Long.valueOf(getInput());
		itemService.delete(id);
		LOGGER.info("Item deleted.");

	}

}
