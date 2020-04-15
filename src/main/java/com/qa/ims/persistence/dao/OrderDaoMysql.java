package com.qa.ims.persistence.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.qa.ims.persistence.domain.Order;

public class OrderDaoMysql implements CrudableDao<Order> {

	public static final Logger LOGGER = Logger.getLogger(CustomerDaoMysql.class);

	private String jdbcConnectionUrl;
	private String username;
	private String password;
	@SuppressWarnings("unused")
	private String ip;

	public OrderDaoMysql(String username, String password, String ip) {
		this.ip = ip;
		this.jdbcConnectionUrl = "jdbc:mysql://" + ip + "/ims";
		this.username = username;
		this.password = password;
	}

	public OrderDaoMysql(String jdbcConnectionUrl, String username, String password, String ip) {
		this.ip = ip;
		this.jdbcConnectionUrl = jdbcConnectionUrl;
		this.username = username;
		this.password = password;
	}

	Order orderFromResultSet(ResultSet resultSet) throws SQLException {
		Long id = resultSet.getLong("order_id");
		Long customerId = resultSet.getLong("customer_id");
		BigDecimal total = resultSet.getBigDecimal("total");
		return new Order(id, customerId, total);
	}

	@Override
	public List<Order> readAll() {
		try (Connection conn = DriverManager.getConnection(jdbcConnectionUrl, username, password);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM orders");) {
			ArrayList<Order> orders = new ArrayList<>();
			while (rs.next()) {
				orders.add(orderFromResultSet(rs));
			}
			return orders;
		} catch (SQLException sqle) {
			LOGGER.debug(sqle.getStackTrace());
			LOGGER.error(sqle.getMessage());
		}
		return new ArrayList<>();
	}

	public Order readLatest() {
		try (Connection conn = DriverManager.getConnection(jdbcConnectionUrl, username, password);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM orders ORDER BY order_id DESC LIMIT 1");) {
			rs.next();
			return orderFromResultSet(rs);
		} catch (Exception e) {
			LOGGER.debug(e.getStackTrace());
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	@Override
	public Order create(Order order) {
		String query = "INSERT INTO orders(customer_id, total) values(?, ?)";
		try (Connection conn = DriverManager.getConnection(jdbcConnectionUrl, username, password);
				PreparedStatement pstmt = conn.prepareStatement(query);) {
			pstmt.setLong(1, order.getCustomerId());
			pstmt.setString(2, "" + order.getTotal());
			pstmt.executeUpdate();
			return readLatest();
		} catch (Exception e) {
			LOGGER.debug(e.getStackTrace());
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	// TODO add utility to read using customer_id instead of order_id
	public Order readOrder(Long id) {
		String query = "SELECT * FROM orders WHERE order_id = '?'";
		try (Connection conn = DriverManager.getConnection(jdbcConnectionUrl, username, password);
				PreparedStatement pstmt = conn.prepareStatement(query);) {
			pstmt.setString(1, "" + id);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			return orderFromResultSet(rs);
		} catch (Exception e) {
			LOGGER.debug(e.getStackTrace());
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	@Override
	public Order update(Order order) {
		String query = "UPDATE orders SET customer_id ='?', total ='?' where order_id = '?'";
		try (Connection conn = DriverManager.getConnection(jdbcConnectionUrl, username, password);
				PreparedStatement pstmt = conn.prepareStatement(query);) {
			pstmt.setString(1, "" + order.getCustomerId());
			pstmt.setString(2, "" + order.getTotal());
			pstmt.setString(3, "" + order.getOrderId());
			pstmt.executeUpdate();
			return readOrder(order.getOrderId());
		} catch (Exception e) {
			LOGGER.debug(e.getStackTrace());
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	@Override
	public void delete(long id) {
		String query = "DELETE FROM orders WHERE order_id = '?'";
		try (Connection conn = DriverManager.getConnection(jdbcConnectionUrl, username, password);
				PreparedStatement pstmt = conn.prepareStatement(query);) {
			pstmt.setString(1, "" + id);
			pstmt.executeUpdate();
		} catch (Exception e) {
			LOGGER.debug(e.getStackTrace());
			LOGGER.error(e.getMessage());
		}
	}

}
