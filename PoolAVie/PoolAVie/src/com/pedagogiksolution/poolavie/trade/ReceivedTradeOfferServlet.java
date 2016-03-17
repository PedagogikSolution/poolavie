package com.pedagogiksolution.poolavie.trade;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ReceivedTradeOfferServlet extends HttpServlet {
  
	/**
	 * 
	 */
	private static final long serialVersionUID = 763180796186293473L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
// initialisation du metier pour les trades qui initialise aussi le TradeBean	    
	    	TradeModel mClass = new TradeModel();
// method qui verifie si une offre existe pour le client connecter et retourne true si une offre existe	    	
	    	boolean isThereAOfferForMe = mClass.isThereAOfferForMe(req);
//	    	
	    	if(isThereAOfferForMe){
	    	req.getRequestDispatcher("/jsp/trade/received_offer.jsp").forward(req, resp); 
	    	} else {
	    	req.getRequestDispatcher("/jsp/trade/received_offer.jsp").forward(req, resp);   
	    	}
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    
	    TradeModel mClass = new TradeModel();
		String etape = req.getParameter("etape");
		
		switch(etape){
		
		    
		case "showOffer" :
		    
		    mClass.showOfferNumberX(req);
		    req.getRequestDispatcher("/jsp/trade/show_offer_received.jsp").forward(req, resp);
		    
		    break;
		
		case "cancelOffer":
		    
		    mClass.annulerOffre(req);
		    resp.sendRedirect("/trade_received");
		    
		    break;
		    
		case "acceptOffer":
		    
		    mClass.showOfferNumberX(req);
		    req.getRequestDispatcher("/jsp/trade/confirmation_accepter_trade.jsp").forward(req, resp);
		    
		    break;
		    
		
		}

	}
	
	

	
	

}
