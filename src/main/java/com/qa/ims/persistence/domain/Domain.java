package com.qa.ims.persistence.domain;

import org.apache.log4j.Logger;

import com.qa.ims.utils.Utils;

public enum Domain {

	CUSTOMERS("Information about customers"), ITEMS("Information about items"), ORDERS("Purchases of items"),
	STOP("To close the application");

	public static final Logger LOGGER = Logger.getLogger(Domain.class);

	private String description;

	private Domain(String description) {
		this.description = description;
	}

	public String getDescription() {
		return this.name() + ": " + this.description;
	}

	public static void printDomains() {
		for (Domain domain : Domain.values()) {
			LOGGER.info(domain.getDescription());
		}
	}

	public static Domain getDomain() {
		Domain domain;
		while (true) {
			try {
				domain = Domain.valueOf(Utils.getInput().toUpperCase());
				break;
			} catch (IllegalArgumentException e) {
				LOGGER.error("Invalid selection please try again.");
			}
		}
		return domain;
	}

}
