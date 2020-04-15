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

import com.qa.ims.persistence.domain.Item;

// expand for all imports
public class ItemDaoMysql implements CrudableDao<Item> {

	public static final Logger LOGGER = Logger.getLogger(ItemDaoMysql.class);

	private String jdbcConnectionUrl;
	private String username;
	private String password;
	@SuppressWarnings("unused")
	private String ip;

	public ItemDaoMysql(String username, String password, String ip) {
		this.ip = ip;
		this.jdbcConnectionUrl = "jdbc:mysql://" + ip + "/ims";
		this.username = username;
		this.password = password;

	}

	public ItemDaoMysql(String jdbcConnectionUrl, String username, String password, String ip) {
		this.ip = ip;
		this.jdbcConnectionUrl = jdbcConnectionUrl;
		this.username = username;
		this.password = password;

	}

	Item itemFromResultSet(ResultSet resultSet) throws SQLException {
		Long itemId = resultSet.getLong("item_id");
		String itemName = resultSet.getString("item_name");
		BigDecimal price = resultSet.getBigDecimal("price");
		Integer stock = resultSet.getInt("stock");
		return new Item(itemId, itemName, price, stock);
	}

	@Override
	public List<Item> readAll() {
		try (Connection connection = DriverManager.getConnection(jdbcConnectionUrl, username, password);
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM items");) {
			ArrayList<Item> items = new ArrayList<>();
			while (resultSet.next()) {
				items.add(itemFromResultSet(resultSet));
			}
			return items;
		} catch (SQLException e) {
			LOGGER.debug(e.getStackTrace());
			LOGGER.error(e.getMessage());
		}
		return new ArrayList<>();
	}

	public Item readLatest() {
		try (Connection conn = DriverManager.getConnection(jdbcConnectionUrl, username, password);
				Statement stmt = conn.createStatement();
				ResultSet resultSet = stmt.executeQuery("SELECT * FROM items ORDER BY item_id DESC LIMIT 1");) {
			resultSet.next();
			return itemFromResultSet(resultSet);
		} catch (Exception e) {
			LOGGER.debug(e.getStackTrace());
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	/*
	 * Attempting to use a prepared statment for creating an item in the database
	 */
	@Override
	public Item create(Item item) {
		String query = "INSERT INTO items(item_name, price, stock) values(?, ?, ?)";
		try (Connection conn = DriverManager.getConnection(jdbcConnectionUrl, username, password);
				PreparedStatement pstmt = conn.prepareStatement(query);) {
			pstmt.setString(1, item.getItemName());
			pstmt.setString(2, "" + item.getPrice());
			pstmt.setString(3, "" + item.getStock());
			pstmt.executeUpdate();
			return readLatest();
		} catch (Exception e) {
			LOGGER.debug(e.getStackTrace());
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	// This read method are not part of the interface
	// TODO add overloaded redItem method for item name
	public Item readItem(Long id) {
		String query = "SELECT * FROM items WHERE item_id = '?'";
		try (Connection connection = DriverManager.getConnection(jdbcConnectionUrl, username, password);
				PreparedStatement pstmt = connection.prepareStatement(query);) {
			pstmt.setString(1, "" + id);
			ResultSet resultSet = pstmt.executeQuery();
			resultSet.next();
			return itemFromResultSet(resultSet);
		} catch (Exception e) {
			LOGGER.debug(e.getStackTrace());
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	@Override
	public Item update(Item item) {
		String query = "UPDATE items SET item_name ='?', price ='?', stock = '?' WHERE item_id = '?'";
		try (Connection connection = DriverManager.getConnection(jdbcConnectionUrl, username, password);
				PreparedStatement pstmt = connection.prepareStatement(query);) {
			pstmt.setString(1, item.getItemName());
			pstmt.setString(2, "" + item.getPrice());
			pstmt.setString(3, "" + item.getStock());
			pstmt.setString(4, "" + item.getId());
			pstmt.executeUpdate();
			return readItem(item.getId());
		} catch (Exception e) {
			LOGGER.debug(e.getStackTrace());
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	@Override
	public void delete(long id) {
		String query = "DELETE FROM items WHERE item_id = '?'";
		try (Connection connection = DriverManager.getConnection(jdbcConnectionUrl, username, password);
				PreparedStatement pstmt = connection.prepareStatement(query);) {
			pstmt.setString(1, "" + id);
			pstmt.executeUpdate();
		} catch (Exception e) {
			LOGGER.debug(e.getStackTrace());
			LOGGER.error(e.getMessage());
		}
	}

}
