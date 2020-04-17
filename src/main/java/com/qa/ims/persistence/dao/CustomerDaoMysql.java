package com.qa.ims.persistence.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.qa.ims.persistence.domain.Customer;

// expand for all imports
public class CustomerDaoMysql implements CrudableDao<Customer> {

	public static final Logger LOGGER = Logger.getLogger(CustomerDaoMysql.class);

	private String jdbcConnectionUrl;
	private String username;
	private String password;
	@SuppressWarnings("unused")
	private String ip;

	public CustomerDaoMysql(String username, String password, String ip) {
		this.ip = ip;
		this.jdbcConnectionUrl = "jdbc:mysql://" + ip + "/ims";
		this.username = username;
		this.password = password;

	}

	public CustomerDaoMysql(String jdbcConnectionUrl, String username, String password, String ip) {
		this.ip = ip;
		this.jdbcConnectionUrl = jdbcConnectionUrl;
		this.username = username;
		this.password = password;

	}

	Customer customerFromResultSet(ResultSet resultSet) throws SQLException {
		Long id = resultSet.getLong("customer_id");
		String firstName = resultSet.getString("first_name");
		String surname = resultSet.getString("surname");
		return new Customer(id, firstName, surname);
	}

	/**
	 * Reads all customers from the database
	 * 
	 * @return A list of customers
	 */
	@Override
	public List<Customer> readAll() {
		try (Connection connection = DriverManager.getConnection(jdbcConnectionUrl, username, password);
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM customers");) {
			ArrayList<Customer> customers = new ArrayList<>();
			while (resultSet.next()) {
				customers.add(customerFromResultSet(resultSet));
			}
			return customers;
		} catch (SQLException e) {
			LOGGER.debug(e.getStackTrace());
			LOGGER.error(e.getMessage());
		}
		return new ArrayList<>();
	}

	public Customer readLatest() {
		try (Connection connection = DriverManager.getConnection(jdbcConnectionUrl, username, password);
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement
						.executeQuery("SELECT * FROM customers ORDER BY customer_id DESC LIMIT 1");) {
			resultSet.next();
			return customerFromResultSet(resultSet);
		} catch (Exception e) {
			LOGGER.debug(e.getStackTrace());
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	/**
	 * Creates a customer in the database
	 * 
	 * @param customer - takes in a customer object. id will be ignored
	 */
	@Override
	public Customer create(Customer customer) {
		String query = "INSERT INTO customers(first_name, surname) values(? , ?)";
		try (Connection connection = DriverManager.getConnection(jdbcConnectionUrl, username, password);
				PreparedStatement pstatement = connection.prepareStatement(query)) {
			pstatement.setString(1, customer.getFirstName());
			pstatement.setString(2, customer.getSurname());
			pstatement.executeUpdate();
			return readLatest();
		} catch (Exception e) {
			LOGGER.debug(e.getStackTrace());
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	// These read methods are not part of the interface
	public Customer readCustomer(Long id) {
		String query = "SELECT * FROM customers WHERE customer_id = ?";
		try (Connection connection = DriverManager.getConnection(jdbcConnectionUrl, username, password);
				PreparedStatement pstatement = connection.prepareStatement(query)) {
			pstatement.setLong(1, id);
			try (ResultSet resultSet = pstatement.executeQuery();) {
				resultSet.next();
				return customerFromResultSet(resultSet);
			}
		} catch (Exception e) {
			LOGGER.debug(e.getStackTrace());
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	// Overloaded to read via a name instead of ID number - test as may only return
	// one?
	public Customer readCustomer(String name) {
		String query = "SELECT FROM customers WHERE first_name = ? OR surname = ?";
		try (Connection connection = DriverManager.getConnection(jdbcConnectionUrl, username, password);
				PreparedStatement statement = connection.prepareStatement(query);) {
			statement.setString(1, name);
			statement.setString(2, name);
			try (ResultSet rs = statement.executeQuery()) {
				rs.next();
				return customerFromResultSet(rs);
			}
		} catch (Exception e) {
			LOGGER.debug(e.getStackTrace());
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	/**
	 * Updates a customer in the database
	 * 
	 * @param customer - takes in a customer object, the id field will be used to
	 *                 update that customer in the database
	 * @return
	 */
	@Override
	public Customer update(Customer customer) {
		String sql = "UPDATE customers SET first_name = ?, surname = ? WHERE customer_id = ?";
		try (Connection connection = DriverManager.getConnection(jdbcConnectionUrl, username, password);
				PreparedStatement pstatement = connection.prepareStatement(sql);) {
			pstatement.setString(1, customer.getFirstName());
			pstatement.setString(1, customer.getSurname());
			pstatement.setString(1, "" + customer.getId());
			pstatement.executeUpdate();
			return readCustomer(customer.getId());
		} catch (Exception e) {
			LOGGER.debug(e.getStackTrace());
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	/**
	 * Deletes a customer in the database
	 * 
	 * @param id - id of the customer
	 */
	@Override
	public void delete(long id) {
		String sql = "DELETE FROM customers WHERE customer_id = ?";
		try (Connection connection = DriverManager.getConnection(jdbcConnectionUrl, username, password);
				PreparedStatement pstatement = connection.prepareStatement(sql)) {
			pstatement.setLong(1, id);
			pstatement.executeUpdate();
		} catch (Exception e) {
			LOGGER.debug(e.getStackTrace());
			LOGGER.error(e.getMessage());
		}
	}

}
