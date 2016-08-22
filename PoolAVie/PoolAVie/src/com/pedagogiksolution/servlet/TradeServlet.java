package com.pedagogiksolution.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pedagogiksolution.datastorebeans.Pool;
import com.pedagogiksolution.model.TradeModel;

public class TradeServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 54388717965389157L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		TradeModel mModelTrade;
		Pool mBean = (Pool) req.getSession().getAttribute("Pool");
		int cycleAnnuel = mBean.getCycleAnnuel();

		int fromId;
		String fromID = req.getParameter("from");
		if (fromID != null) {
			fromId = Integer.parseInt(fromID);
		} else {
			fromId = 1;
		}
		
		switch (fromId) {
		case 1:
				// selon la période du pool, on recupere ou pas les offres de trades
				switch(cycleAnnuel){
				
				case 1: 
					// pas de trade possible, on envoie sur trade center sans récupérer les offres
					req.setAttribute("messageTrade", "Il n'y a pas de trade possible à cet période du pool");
					req.getRequestDispatcher("jsp/trade/trade.jsp").forward(req, resp);
					break;
				case 2: 
					// pas de trade possible, on envoie sur trade center sans récupérer les offres
					req.setAttribute("messageTrade", "Il n'y a pas de trade possible à cet période du pool");
					req.getRequestDispatcher("jsp/trade/trade.jsp").forward(req, resp);
					break;				
				case 3: 
					// on recupere les trade recu du datastore et place dans bean pour affichage
					mModelTrade = new TradeModel(req,mBean);
					mModelTrade.getTradeOffer();
					req.getRequestDispatcher("jsp/trade/trade.jsp").forward(req, resp);
					break;
				case 4: 
					// pas de trade possible, on envoie sur trade center sans récupérer les offres
					req.getRequestDispatcher("jsp/trade/trade.jsp").forward(req, resp);
					break;
				case 5:
					// Si tradeType==1, on recupere les trade recu du datastore et place dans bean pour affichage
					mModelTrade = new TradeModel(req,mBean);
					int tradeType = mBean.getTradeType();
					if(tradeType==1){
						
						mModelTrade.getTradeOffer();
						req.getRequestDispatcher("jsp/trade/trade.jsp").forward(req, resp);
					} else {
						// check si date de trade en vigueur
						Boolean checkIfTradeOpen = mModelTrade.checkIfTradeOpen();
						if(checkIfTradeOpen){
							mModelTrade.getTradeOffer();
							req.getRequestDispatcher("jsp/trade/trade.jsp").forward(req, resp);
						} else {
							// on envoie un bean requestScope avec les dates setup du trade
							// TODO recuper les date de trade et les placer dans un bean
							req.getRequestDispatcher("jsp/trade/trade.jsp").forward(req, resp);
						}
						
					}
					
				
				}
			
			
			break;
		case 2:
			break;
		case 3:
			break;
		case 4:
			break;

		}

		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}

}
