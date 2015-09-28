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
	String[] player_trade_by,pick_trade_by,player_trade_for,pick_trade_for;
	int number_of_player_trade_by,number_of_pick_trade_by,number_of_player_trade_for,number_of_pick_trade_for;
	String t1j1, t1j2, t1j3, t1j4, t1j5, t1j6, t1j7, t2j1, t2j2, t2j3, t2j4,
			t2j5, t2j6, t2j7, t1p1, t1p2, t1p3,t2p1, t2p2, t2p3;
	
	String cash_by, cash_for;
	String team_id_1;
	String team_id_2;
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
