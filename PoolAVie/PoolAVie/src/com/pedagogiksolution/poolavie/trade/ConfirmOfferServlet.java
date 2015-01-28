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
		
		
		
		
	String team_id_1 = req.getParameter("team_id_1");
	String team_id_2 = req.getParameter("team_id_2");
	int team1 = Integer.parseInt(team_id_1);
	int team2 = Integer.parseInt(team_id_2);
	
	String[] player_trade_by = req.getParameterValues("player_trade_by");
	String cash_by = req.getParameter("cash_trade_by");
	int cash_trade_by = Integer.parseInt(cash_by);
	String[] pick_trade_by = req.getParameterValues("pick_trade_by");
	
	
	
	String[] player_trade_for = req.getParameterValues("player_trade_for");	
	String cash_for = req.getParameter("cash_trade_for");
	int cash_trade_for = Integer.parseInt(cash_for);
	String[] pick_trade_for = req.getParameterValues("pick_trade_for");
//	String[] rookie_trade_by = req.getParameterValues("rookie_trade_by");
//	String[] rookie_trade_for = req.getParameterValues("rookie_trade_by");
	
	EvaluationPreliminaireTrade evaluatePreTrade = new EvaluationPreliminaireTrade();
	
	boolean evaluationResult = evaluatePreTrade.evaluationResult(team1,team2,player_trade_by,cash_trade_by,pick_trade_by,player_trade_for,cash_trade_for,pick_trade_for);
	
	 if(evaluationResult==false){
		
	//	 PersitenceTradeOffer p = new PersitenceTradeOffer();
	//	 p.persistOffer(team1, team2, player_trade_by, cash_trade_by, pick_trade_by, player_trade_for, cash_trade_for, pick_trade_for);
		
		
		 resp.sendRedirect("/trade");
		
	} else {
		
		
		
		req.getRequestDispatcher("/jsp/trade_offer_reject.jsp").forward(req, resp);
		
	}
	
		
		
	}
	
	

}
