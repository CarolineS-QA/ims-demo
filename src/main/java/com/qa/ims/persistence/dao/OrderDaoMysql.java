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
		Long orderId = resultSet.getLong("orders.order_id");
		Long customerId = resultSet.getLong("orders.customer_id");
		BigDecimal total = resultSet.getBigDecimal("orders.total");
		Long itemId = resultSet.getLong("item_orders.item_id");
		Integer qty = resultSet.getInt("item_orders.qty");
		return new Order(orderId, customerId, total, itemId, qty);
	}

	@Override
	public List<Order> readAll() {
		try (Connection conn = DriverManager.getConnection(jdbcConnectionUrl, username, password);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(
						"SELECT orders.order_id, orders.customer_id, orders.total, item_orders.item_id, item_orders.qty FROM orders LEFT JOIN item_orders ON orders.order_id=item_orders.order_id");) {
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
		String ordersQuery = "INSERT INTO orders(customer_id, total) values(?, ?)";
		String item_ordersQuery = "INSERT INTO item_orders(qty) values(?) WHERE item_id = '?'";
		try (Connection conn = DriverManager.getConnection(jdbcConnectionUrl, username, password);
				PreparedStatement pstmtOrders = conn.prepareStatement(ordersQuery);
				PreparedStatement pstmtItem_Orders = conn.prepareStatement(item_ordersQuery);) {
			pstmtOrders.setLong(1, order.getCustomerId());
			pstmtOrders.setString(2, "" + calcTotalPrice(order.getItems()));
			pstmtOrders.executeUpdate();
			ArrayList<Long> itemIds = order.getItems();
			ArrayList<Integer> qty = order.getQty();
			for (Long id : itemIds) {
				pstmtItem_Orders.setString(1, "" + qty.get(id.intValue()));
				pstmtItem_Orders.setString(2, "" + id);
				pstmtItem_Orders.executeUpdate();
			}
			return readLatest();
		} catch (Exception e) {
			LOGGER.debug(e.getStackTrace());
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	// TODO add utility to read using customer_id instead of order_id
	public Order readOrder(Long id) {
		String query = "SELECT orders.order_id, orders.customer_id, orders.total, item_orders.item_id, item_orders.qty FROM orders LEFT JOIN item_orders ON orders.order_id=item_orders.order_id WHERE order_id = '?'";
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
		String query = "UPDATE orders SET customer_id ='?', total ='?' WHERE order_id = '?'";
		String qtyQuery = "UPDATE item_orders SET qty = '?' WHERE item_id = '?'";
		try (Connection conn = DriverManager.getConnection(jdbcConnectionUrl, username, password);
				PreparedStatement pstmt = conn.prepareStatement(query);
				PreparedStatement pstmtItem_Orders = conn.prepareStatement(qtyQuery);) {
			pstmt.setString(1, "" + order.getCustomerId());
			pstmt.setString(2, "" + calcTotalPrice(order.getItems()));
			pstmt.setString(3, "" + order.getOrderId());
			pstmt.executeUpdate();
			ArrayList<Long> itemIds = order.getItems();
			ArrayList<Integer> qty = order.getQty();
			for (Long id : itemIds) {
				pstmtItem_Orders.setString(1, "" + qty.get(id.intValue()));
				pstmtItem_Orders.setString(2, "" + id);
				pstmtItem_Orders.executeUpdate();
			}
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

	public BigDecimal calcTotalPrice(ArrayList<Long> itemIds) {
		BigDecimal total = BigDecimal.valueOf(0);
		for (Long id : itemIds) {
			String priceQuery = "SELECT price FROM items WHERE item_id = '?'";
			String qtyQuery = "SELECT qty FROM item_orders WHERE item_id = '?'";
			try (Connection conn = DriverManager.getConnection(jdbcConnectionUrl, username, password);
					PreparedStatement pstmtPrice = conn.prepareStatement(priceQuery);
					PreparedStatement pstmtQty = conn.prepareStatement(qtyQuery);) {
				pstmtPrice.setString(1, "" + id);
				ResultSet rsPrice = pstmtPrice.executeQuery();
				Long price = rsPrice.getLong(1);
				pstmtQty.setString(1, "" + id);
				ResultSet rsQty = pstmtQty.executeQuery();
				Integer qty = rsQty.getInt(1);
				Long totalItemPrice = price * qty;
				total = total.add(BigDecimal.valueOf(totalItemPrice));
			} catch (Exception e) {
				LOGGER.debug(e.getStackTrace());
				LOGGER.error(e.getMessage());
			}
		}
		return total;
	}

}
