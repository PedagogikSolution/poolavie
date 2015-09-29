package com.pedagogiksolution.poolavie.trade;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pedagogiksolution.poolavie.beans.TradeBeans;

public class AccepterOffreTradeServlet extends HttpServlet {
    TradeBeans mBean = new TradeBeans();
    /**
	 * 
	 */
    private static final long serialVersionUID = 763180796186293473L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	String confirmation = req.getParameter("confirmation");

	switch (confirmation) {

	case "oui":

	    TradeModel mClass = new TradeModel();
    
	    Boolean checkIfTradeIsStillPossible = mClass.checkIfTradeIsStillPossible(req);
	    
	    if(checkIfTradeIsStillPossible){
		mClass.makeTrade(req);
		mClass.persistTrade(req);
		mClass.annulerOffre(req);
		resp.sendRedirect("/trade_received");
		
		
	    } else {
		mBean.setCodeErreurOffreTrade(11);
		req.setAttribute("messageErreur", mBean);
		req.getRequestDispatcher("/jsp/received_offer.jsp").forward(req, resp);
	    }
	    
	    
	    
	    
	    
	    break;
	    
	    
	    
	case "non":
	    resp.sendRedirect("/trade_received");
	    break;

	}

    }

}