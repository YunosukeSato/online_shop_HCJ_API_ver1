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
import com.example.e_commerce.entity.Cart;

public class CartModel {

	
	Connection conn = null;
	
	public CartModel() {
		this.conn = dbConnection.getConnection();
		
		if(this.conn == null) {
			System.exit(1);
		}
	}
	
	public boolean isDatabaseConnected() {
		return this.conn != null;
	}
	
	public JSONArray allItems() throws SQLException, JSONException {
		
//		Initialize json array to store json objects
		JSONArray result = new JSONArray();
		try {
			
			Statement stmt = conn.createStatement();
			ResultSet resultSet = stmt.executeQuery("SELECT * FROM e_commerce_carts");

//			Get the information about the types and properties of the column in the ResultSet object
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
	
	public JSONObject getItem(Cart cart) throws SQLException, JSONException {
		
			JSONObject row = new JSONObject();
			
			PreparedStatement statement = null;
			
			String query = "SELECT * FROM e_commerce_carts WHERE id = ?";
				try {
					
					statement = this.conn.prepareStatement(query);
					
					statement.setInt(1, cart.getId());
					
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
						
		//				Initialize a json object
						
		//				Retrieve table row and put it in json object
						colNames.forEach(cn -> {
							try {
								row.put(cn, resultSet.getObject(cn));
							} catch (JSONException | SQLException e) {
								e.printStackTrace();
							}
						});
		//				result.put(row);
						
						
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return row;
			}

}
