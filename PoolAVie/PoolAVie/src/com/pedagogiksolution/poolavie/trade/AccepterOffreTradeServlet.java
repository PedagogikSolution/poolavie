package com.pedagogiksolution.poolavie.trade;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AccepterOffreTradeServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 763180796186293473L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		
		String trade_id2 = req.getParameter("trade_id");
		int trade_id = Integer.parseInt(trade_id2);
		
		// copier le trade dans table trade made
		
		PersistenceTrade persist = new PersistenceTrade();
		persist.persitenceTrade(trade_id);
		
		RecuperationMyOffer recupOffer = new RecuperationMyOffer();
		recupOffer.cancelMyOffer(trade_id);
		
		
		
		
		
		
		
		
		
		
		req.getRequestDispatcher("/jsp/all_trade.jsp").forward(req, resp);
	}

	
	
	

}