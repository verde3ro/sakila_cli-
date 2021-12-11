package com.appstracta.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

	private Connection connection;
	private static final String URL = "jdbc:mysql://localhost:3306/sakila?useSSL=false";
	private static final String USER = "test";
	private static final String PASSWORD = "123456789";

	public void connectar() {
		try {
			// LE dcimos a java a que base nos conectamos
			Class.forName("com.mysql.cj.jdbc.Driver"); //Es para la version 8 de mysql
			this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
		}
	}

	public void cerrar() {
		if (this.connection != null) {
			try {
				this.connection.close();
			} catch (SQLException ex ) {
				ex.printStackTrace();
			}
		}
	}

	public Connection getConnection() {
		return connection;
	}

}
