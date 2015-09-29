package com.pedagogiksolution.poolavie.trade;

import java.io.IOException;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pedagogiksolution.poolavie.beans.TradeBeans;

public class ReceivedTradeOfferServlet extends HttpServlet {
    	TradeBeans mBean = new TradeBeans();
	/**
	 * 
	 */
	private static final long serialVersionUID = 763180796186293473L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	    
	    	TradeModel mClass = new TradeModel();
	    	boolean isThereAOfferForMe = mClass.isThereAOfferForMe(req);
	    	
	    	if(isThereAOfferForMe){
	    	mBean.setIsThereAOfferForMe(1);
	    	req.setAttribute("beanTrade", mBean);
	    	mClass.getTheOfferThatIReceived(req);
	    	req.getRequestDispatcher("/jsp/received_offer.jsp").forward(req, resp); 
	    	} else {
	    	mBean.setIsThereAOfferForMe(0);
	    	req.setAttribute("beanTrade", mBean);
	    	req.getRequestDispatcher("/jsp/received_offer.jsp").forward(req, resp);   
	    	}
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    
	    TradeModel mClass = new TradeModel();
		String etape = req.getParameter("etape");
		
		switch(etape){
		
		    
		case "showOffer" :
		    
		    mClass.showOfferNumberX(req);
		    req.getRequestDispatcher("/jsp/show_offer_received.jsp").forward(req, resp);
		    
		    break;
		
		case "cancelOffer":
		    
		    mClass.annulerOffre(req);
		    resp.sendRedirect("/trade_received");
		    
		    break;
		    
		case "acceptOffer":
		    
		    mClass.showOfferNumberX(req);
		    req.getRequestDispatcher("/jsp/confirmation_accepter_trade.jsp").forward(req, resp);
		    
		    break;
		    
		
		}

	}
	
	

	
	

}
