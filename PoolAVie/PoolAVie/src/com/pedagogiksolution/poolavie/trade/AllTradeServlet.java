package com.pedagogiksolution.poolavie.trade;

import java.io.IOException;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AllTradeServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 763180796186293473L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		ResultSet rs;
		/* récupérer les trades offers du DG connecté */
		
		
		
		RecuperationMyOffer recupOffer = new RecuperationMyOffer();
		rs = recupOffer.recuperationAllTrade();
		req.setAttribute("all_offer", rs);
		
		
		req.getRequestDispatcher("/jsp/trade/all_trade.jsp").forward(req, resp);
	}

	
	
	

}
