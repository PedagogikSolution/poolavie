package com.pedagogiksolution.poolavie.trade;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AnnulationOffreTradeServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String trade_id_2=req.getParameter("trade_id");
		int trade_id = Integer.parseInt(trade_id_2);
		RecuperationMyOffer recupOffer = new RecuperationMyOffer();
		recupOffer.cancelMyOffer(trade_id);
		
		
		
		req.getRequestDispatcher("/make_offer").forward(req, resp);
	}

}
