package com.pedagogiksolution.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.google.appengine.api.utils.SystemProperty;

public class DatabaseConnector {
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.GoogleDriver";
	static final String DB_URL = "jdbc:google:mysql://zeta-anthem-715:poolavie/poolavie";
	static final String DB_URL2 = "jdbc:mysql://127.0.0.1:3306/poolavie";

	// Database credentials
	static final String USER = "root";
	static final String USER2 = "root";
	static final String PASS = "";
	static final String PASS2 = "root";
	Connection conn;

	public Connection open() {

		/*
		 * vérification si en mode développement ou production pour initialiser
		 * le bon url afin de se connecter aux bdd de google cloud sql ou en
		 * local sur MySql
		 */
		try {
			if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
				Class.forName(JDBC_DRIVER);
			} else {
				Class.forName("com.mysql.jdbc.Driver");
				
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			// to do ----> si cette erreur ce produit, loguer le tout quelques
			// part et retourner une action pour gérer le plantage de la
			// connexion
		}

		try {
			if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			} else {
			conn = DriverManager.getConnection(DB_URL2, USER2, PASS2);	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return conn;

	}

	public void close(Connection c) {
		try {
			if (c != null) {
				c.close();
			}
		} catch (Exception e) {
			// Trouver et gérer les types d'Exceptions pouvant ce produire (si
			// c'est parce que deja fermer, juste return et ne rien faire,
			// loguer l'Exception
		}
	}
}
