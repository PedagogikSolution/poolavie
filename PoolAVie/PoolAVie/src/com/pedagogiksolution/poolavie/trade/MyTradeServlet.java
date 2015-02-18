package com.pedagogiksolution.poolavie.trade;

import java.io.IOException;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyTradeServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 763180796186293473L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		ResultSet rs;
		/* r�cup�rer les trades offers du DG connect� */
		
		
		String team_that_trade_string = (String) req.getSession().getAttribute("mTeamId");
		int team_that_trade = Integer.parseInt(team_that_trade_string);
		RecuperationMyOffer recupOffer = new RecuperationMyOffer();
		rs = recupOffer.recuperationMyTrade(team_that_trade);
		req.setAttribute("my_offer", rs);
		
		
		req.getRequestDispatcher("/jsp/my_trade.jsp").forward(req, resp);
	}

	
	
	

}
