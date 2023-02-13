package com.example.e_commerce.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.e_commerce.config.dbConnection;
import com.example.e_commerce.dto.CartRequest;
import com.example.e_commerce.entity.Item;

public class HomeModel {
	
	
	Connection conn = null;
	
	public HomeModel() {
		this.conn = dbConnection.getConnection();
		
		if(this.conn == null) {
			System.exit(1);
		}
	}
	
	public boolean isDatabaseConnected() {
		return this.conn != null;
	}

	public JSONArray getItems() throws SQLException, JSONException {
		
		JSONArray result = new JSONArray();
		try {
			
			Statement stmt = conn.createStatement();
			ResultSet resultSet = stmt.executeQuery("SELECT * FROM e_commerce_items");
			
//			Get the information about the types and properties of the columns in a ResultSet object
			ResultSetMetaData md = resultSet.getMetaData();
			
//			Serch how many columns it has
			int numCols = md.getColumnCount();
			
//			Store all column names in a List
			List<String> colNames = IntStream.range(0, numCols).mapToObj(i -> {
				try {
					return md.getColumnName(i + 1);
				} catch(SQLException e) {
					e.printStackTrace();
					return "?";
				}
			}).collect(Collectors.toList());
			
			while(resultSet.next()) {
				
//				Initialize a json object
				JSONObject row = new JSONObject();
				
//				Retrieve table row and put it in json object
				colNames.forEach(cn -> {
					try {
						row.put(cn, resultSet.getObject(cn));
					} catch (JSONException | SQLException e) {
						e.printStackTrace();
					}
				});
				result.put(row);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public JSONObject getItemById(Item item) throws SQLException, JSONException {
			
	//		JSONArray result = new JSONArray();
		JSONObject row = new JSONObject();
		
		PreparedStatement statement = null;
		
		String query = "SELECT * FROM e_commerce_items WHERE id = ?";
			try {
				
				statement = this.conn.prepareStatement(query);
				
				statement.setInt(1, item.getId());
				
				ResultSet resultSet = statement.executeQuery();
				
	//			Get the information about the types and properties of the columns in a ResultSet object
				ResultSetMetaData md = resultSet.getMetaData();
				
	//			Serch how many columns it has
				int numCols = md.getColumnCount();
				
	//			Store all column names in a List
				List<String> colNames = IntStream.range(0, numCols).mapToObj(i -> {
					try {
						return md.getColumnName(i + 1);
					} catch(SQLException e) {
						e.printStackTrace();
						return "?";
					}
				}).collect(Collectors.toList());
				
				while(resultSet.next()) {
					
	//				Retrieve table row and put it in json object
					colNames.forEach(cn -> {
						try {
							row.put(cn, resultSet.getObject(cn));
						} catch (JSONException | SQLException e) {
							e.printStackTrace();
						}
					});
					
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return row;
		}
	
	public JSONObject addedItem(CartRequest cartRequest) throws SQLException, JSONException {
		
		JSONObject row = new JSONObject();
		
		PreparedStatement statement = null;
		
		String query = "SELECT * FROM e_commerce_carts WHERE brand = ? AND name = ? AND image = ? AND price = ?";
			try {
				
				statement = this.conn.prepareStatement(query);
				
				statement.setString(1, cartRequest.getBrand());
				statement.setString(2, cartRequest.getName());
				statement.setString(3, cartRequest.getImage());
				statement.setInt(4, cartRequest.getPrice());
				
				ResultSet resultSet = statement.executeQuery();
				
	//			Get the information about the types and properties of the columns in a ResultSet object
				ResultSetMetaData md = resultSet.getMetaData();
				
	//			Serch how many columns it has
				int numCols = md.getColumnCount();
				
	//			Store all column names in a List
				List<String> colNames = IntStream.range(0, numCols).mapToObj(i -> {
					try {
						return md.getColumnName(i + 1);
					} catch(SQLException e) {
						e.printStackTrace();
						return "?";
					}
				}).collect(Collectors.toList());
				
				while(resultSet.next()) {
					
	//				Retrieve table row and put it in json object
					colNames.forEach(cn -> {
						try {
							row.put(cn, resultSet.getObject(cn));
						} catch (JSONException | SQLException e) {
							e.printStackTrace();
						}
					});
					
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return row;
	}

}
