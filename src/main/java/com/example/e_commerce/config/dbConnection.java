package com.example.e_commerce.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbConnection {
	
	Connection conn = null;
	
	public static Connection getConnection() {
		try {
			return DriverManager.getConnection(dbInformation.URL, dbInformation.USERNAME, dbInformation.PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}