package com.pedagogiksolution.poolavie.draft;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PickConfirmerServlet extends HttpServlet {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -7979330643530437866L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		
		
		req.getRequestDispatcher("/draft").forward(req,
				resp);
		
		
		
		
		
		
		
		
	}

}
