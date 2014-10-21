package com.pedagogiksolution.poolavie.signature;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignatureServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 763180796186293473L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.getRequestDispatcher("/jsp/signature_after_draft.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.getRequestDispatcher("/jsp/signature_after_draft.jsp").forward(req, resp);
	}
	
	

}
