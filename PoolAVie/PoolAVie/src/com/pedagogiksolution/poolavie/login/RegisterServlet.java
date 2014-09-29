package com.pedagogiksolution.poolavie.login;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2718138746239425792L;
	String username;
	String password;
	String team;
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		username = req.getParameter("user_reg");
		password = req.getParameter("password_reg");
		team = req.getParameter("team_reg");
	}
	
	

}
