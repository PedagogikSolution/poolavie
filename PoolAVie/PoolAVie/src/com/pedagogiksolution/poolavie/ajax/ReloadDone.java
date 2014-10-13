package com.pedagogiksolution.poolavie.ajax;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pedagogiksolution.poolavie.utils.DatabaseConnector;

public class ReloadDone extends HttpServlet {
	String result_of_test;
	Connection conn;
	DatabaseConnector dbHelper;
	ResultSet rs;
	String statement,statement2,statement3,mData;
	int mIntData;

	/**
	 * 
	 */
	private static final long serialVersionUID = -2732125333743563410L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		mData = req.getParameter("data");
		mIntData = Integer.parseInt(mData);
		
		dbHelper = new DatabaseConnector();
		conn = dbHelper.open();

		if (conn != null) {

			try {
				
			
				statement2 = "SELECT counter FROM reload_counter WHERE _id=1";
				statement = "UPDATE reload_counter SET counter = counter+1 WHERE _id=1";
				statement3 = "UPDATE reload_counter SET counter = 0 AND reload=0 WHERE _id=1";
				try {
					rs = conn.createStatement().executeQuery(statement2);
					
					int mCounter = rs.getInt("counter");
					
					if(mCounter<10){
					
					conn.createStatement().executeQuery(statement);	
					} else {
						conn.createStatement().executeQuery(statement3);	
						
					}
					req.getRequestDispatcher("/draft")
					.forward(req, resp);

				} catch (SQLException e) {
					e.printStackTrace();
				}

			} finally {
				dbHelper.close(conn);
			}

		} else {
			// une erreur de connexion s'est produite, gérer ce problème pour
			// être transparent pour l'utilisateur
			req.getRequestDispatcher("/jsp/no_connexion.jsp")
					.forward(req, resp);

		}
		

		
	}

}
