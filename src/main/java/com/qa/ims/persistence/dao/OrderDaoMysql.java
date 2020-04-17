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

	public OrderDaoMysql(String username, String password, String ip) {
		this.jdbcConnectionUrl = "jdbc:mysql://" + ip + "/ims";
		this.username = username;
		this.password = password;
	}

	public OrderDaoMysql(String jdbcConnectionUrl, String username, String password, String ip) {
		this.jdbcConnectionUrl = jdbcConnectionUrl;
		this.username = username;
		this.password = password;
	}

	Order orderFromResultSet(ResultSet resultSet, Long orderId) throws SQLException {
		Long customerId = resultSet.getLong("orders.customer_id");
		BigDecimal total = resultSet.getBigDecimal("orders.total");
		String query = "SELECT * FROM item_orders WHERE order_id = ?";
		try (Connection conn = DriverManager.getConnection(jdbcConnectionUrl, username, password);
				PreparedStatement pstmt = conn.prepareStatement(query);) {
			ArrayList<Long> itemIds = new ArrayList<>();
			ArrayList<Integer> qtys = new ArrayList<>();
			pstmt.setString(1, orderId.toString());
			try (ResultSet itemRs = pstmt.executeQuery();) {
				while (itemRs.next()) {
					itemIds.add(itemRs.getLong("item_id"));
					qtys.add(itemRs.getInt("qty"));
				}
				return new Order(orderId, customerId, total, itemIds, qtys);
			}
		} catch (SQLException sqle) {
			LOGGER.info("An SQL Exception was thrown in the orderFromResultSet method");
			LOGGER.debug(sqle.getStackTrace());
			LOGGER.error(sqle.getMessage());
		} catch (Exception e) {
			LOGGER.debug(e.getStackTrace());
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	@Override
	public List<Order> readAll() {
		try (Connection conn = DriverManager.getConnection(jdbcConnectionUrl, username, password);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM orders");) {
			ArrayList<Order> orders = new ArrayList<>();
			while (rs.next()) {
				orders.add(orderFromResultSet(rs, rs.getLong("orders.order_id")));
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
				ResultSet rs = stmt.executeQuery(
						"SELECT orders.order_id, orders.customer_id, orders.total, item_orders.item_id, item_orders.qty FROM orders LEFT JOIN item_orders ON orders.order_id=item_orders.order_id ORDER BY order_id DESC LIMIT 1");) {
			rs.next();
			return orderFromResultSet(rs, rs.getLong("orders.order_id"));
		} catch (Exception e) {
			LOGGER.debug(e.getStackTrace());
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	/*
	 * #Catch22 work around. To calculate the order total I use calcTotalPrice which
	 * requires values in the item_orders table So, I insert total as null because I
	 * need an order in orders to insert into the item_orders table! The orderId is
	 * auto-generated so I need to retrieve it to insert into item_orders Only then
	 * I add the calculated total price to the orders table
	 */
	@Override
	public Order create(Order order) {
		String ordersQuery = "INSERT INTO orders(customer_id, total) values(?, null)";
		String getIdQuery = "SELECT order_id FROM orders ORDER BY order_id DESC LIMIT 1";
		String itemOrdersQuery = "INSERT INTO item_orders(order_id, item_id, qty) values(?, ?, ?)";
		String totalQuery = "UPDATE orders SET total = ? WHERE order_id = ?";
		try (Connection conn = DriverManager.getConnection(jdbcConnectionUrl, username, password);
				PreparedStatement ordersPstmt = conn.prepareStatement(ordersQuery);
				PreparedStatement getIdPstmt = conn.prepareStatement(getIdQuery);
				PreparedStatement itemOrdersPstmt = conn.prepareStatement(itemOrdersQuery);
				PreparedStatement totalPstmt = conn.prepareStatement(totalQuery);) {
			ordersPstmt.setLong(1, order.getCustomerId());
			ordersPstmt.executeUpdate();
			try (ResultSet rs = getIdPstmt.executeQuery();) {
				rs.next();
				Long currentOrderId = rs.getLong("order_id");
				ArrayList<Long> itemIds = order.getItems();
				ArrayList<Integer> qty = order.getQty();
				for (Long id : itemIds) {
					itemOrdersPstmt.setString(1, "" + currentOrderId);
					itemOrdersPstmt.setString(2, "" + id);
					itemOrdersPstmt.setString(3, "" + qty.get(itemIds.indexOf(id)));
					itemOrdersPstmt.executeUpdate();
				}
				totalPstmt.setString(1, "" + calcTotalPrice(order.getItems(), order.getQty()));
				totalPstmt.setString(2, "" + currentOrderId);
				totalPstmt.executeUpdate();
				return readLatest();
			}
		} catch (SQLException sqle) {
			LOGGER.info("An SQL Exception was thrown in the create method!");
			LOGGER.debug(sqle.getStackTrace());
			LOGGER.error(sqle.getMessage());
		} catch (Exception e) {
			LOGGER.debug(e.getStackTrace());
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	// TODO add utility to read using customer_id instead of order_id
	public Order readOrder(Long id) {
		String query = "SELECT orders.order_id, orders.customer_id, orders.total, item_orders.item_id, item_orders.qty FROM orders LEFT JOIN item_orders ON orders.order_id=item_orders.order_id WHERE order_id = ?";
		try (Connection conn = DriverManager.getConnection(jdbcConnectionUrl, username, password);
				PreparedStatement pstmt = conn.prepareStatement(query);) {
			pstmt.setString(1, "" + id);
			try (ResultSet rs = pstmt.executeQuery();) {
				rs.next();
				return orderFromResultSet(rs, rs.getLong("orders.order_id"));
			}
		} catch (Exception e) {
			LOGGER.info("There was a problem trying to read your order.");
			LOGGER.debug(e.getStackTrace());
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	@Override
	public Order update(Order order) {
		delete(order.getOrderId());
		create(order);
		return readOrder(order.getOrderId());
	}

	@Override
	public void delete(long id) {
		String itemOrdersQuery = "DELETE FROM item_orders WHERE order_id = ?";
		String ordersQuery = "DELETE FROM orders WHERE order_id = ?";
		try (Connection conn = DriverManager.getConnection(jdbcConnectionUrl, username, password);
				PreparedStatement itemOrdersPstmt = conn.prepareStatement(itemOrdersQuery);
				PreparedStatement ordersPstmt = conn.prepareStatement(ordersQuery);) {
			itemOrdersPstmt.setString(1, "" + id);
			itemOrdersPstmt.executeUpdate();
			ordersPstmt.setString(1, "" + id);
			ordersPstmt.executeUpdate();
		} catch (Exception e) {
			LOGGER.info("There was a problem deleting your order.");
			LOGGER.debug(e.getStackTrace());
			LOGGER.error(e.getMessage());
		}
	}

	public BigDecimal calcTotalPrice(ArrayList<Long> itemIds, ArrayList<Integer> qty) {
		BigDecimal total = BigDecimal.valueOf(0);
		for (Long id : itemIds) {
			String priceQuery = "SELECT price FROM items WHERE item_id = ?";
			try (Connection conn = DriverManager.getConnection(jdbcConnectionUrl, username, password);
					PreparedStatement pstmtPrice = conn.prepareStatement(priceQuery);) {
				pstmtPrice.setString(1, "" + id);
				try (ResultSet rsPrice = pstmtPrice.executeQuery();) {
					rsPrice.next();
					Long price = rsPrice.getLong(1);
					Integer aQty = qty.get(itemIds.indexOf(id));
					Long totalItemPrice = price * aQty;
					total = total.add(BigDecimal.valueOf(totalItemPrice));
				}
			} catch (SQLException sqle) {
				LOGGER.info("An SQL Exception was thrown in the calcTotalPrice method.");
				LOGGER.debug(sqle.getStackTrace());
				LOGGER.error(sqle.getMessage());
			} catch (Exception e) {
				LOGGER.debug(e.getStackTrace());
				LOGGER.error(e.getMessage());
			}
		}
		return total;
	}

}
