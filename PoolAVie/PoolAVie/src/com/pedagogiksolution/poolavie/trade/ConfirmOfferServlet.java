package com.pedagogiksolution.poolavie.trade;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ConfirmOfferServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 763180796186293473L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	    
	    String confirmation = req.getParameter("confirmation");
	    
	    switch(confirmation){
	    
	    case "oui": 
		
		
		
		TradeModel mClass = new TradeModel();
		mClass.persistOffer(req);
		resp.sendRedirect("/make_offer");
		break;
	    case "non":
		resp.sendRedirect("/make_offer");
		break;
	    
	    }
	    
	    
	    
	}

}
