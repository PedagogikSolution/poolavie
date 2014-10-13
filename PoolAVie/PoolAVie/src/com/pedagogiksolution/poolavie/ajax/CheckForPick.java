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

public class CheckForPick extends HttpServlet {
	String result_of_test;
	Connection conn;
	DatabaseConnector dbHelper;
	ResultSet rs;
	String statement;

	/**
	 * 
	 */
	private static final long serialVersionUID = -2732125333743563410L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		dbHelper = new DatabaseConnector();
		conn = dbHelper.open();

		if (conn != null) {

			try {
				statement = "SELECT * FROM reload_counter WHERE _id=1";
				try {

					rs = conn.createStatement().executeQuery(statement);
					
					result_of_test = rs.getString("reload");
					
					

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
		
		resp.setContentType("text/plain");
		resp.setCharacterEncoding("UTF-8");
		resp.getWriter().write(result_of_test);
		
	}

}
