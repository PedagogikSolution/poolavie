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
			switch (cycleAnnuel) {

			case 1:
				// pas de trade possible, on envoie sur trade center sans
				// récupérer les offres
				req.setAttribute("messageTrade", "Il n'y a pas de trade possible à cet période du pool");
				req.getRequestDispatcher("jsp/trade/trade_center.jsp").forward(req, resp);
				break;
			case 2:
				// pas de trade possible, on envoie sur trade center sans
				// récupérer les offres
				req.setAttribute("messageTrade", "Il n'y a pas de trade possible à cet période du pool");
				req.getRequestDispatcher("jsp/trade/trade_center.jsp").forward(req, resp);
				break;
			case 3:

				// on recupere les trade recu du datastore et place dans bean
				// pour affichage
				mModelTrade = new TradeModel(req, mBean);
				mModelTrade.getTradeOfferReceived();
				mModelTrade.getTradeOfferMade();
				req.setAttribute("tradeOpen", 1);
				req.getRequestDispatcher("jsp/trade/trade_center.jsp").forward(req, resp);
				break;
			case 4:
				// pas de trade possible, on envoie sur trade center sans
				// récupérer les offres
				req.getRequestDispatcher("jsp/trade/trade_center.jsp").forward(req, resp);
				break;
			case 5:
				// Si tradeType==1, on recupere les trade recu du datastore et
				// place dans bean pour affichage
				mModelTrade = new TradeModel(req, mBean);
				int tradeType = mBean.getTradeType();
				if (tradeType == 1) {
					req.setAttribute("tradeOpen", 1);
					mModelTrade.getTradeOfferReceived();
					mModelTrade.getTradeOfferMade();
					req.getRequestDispatcher("jsp/trade/trade_center.jsp").forward(req, resp);
				} else {
					// check si date de trade en vigueur
					Boolean checkIfTradeOpen = mModelTrade.checkIfTradeOpen();
					if (checkIfTradeOpen) {
						req.setAttribute("tradeOpen", 1);
						mModelTrade.getTradeOfferReceived();
						mModelTrade.getTradeOfferMade();
						req.getRequestDispatcher("jsp/trade/trade_center.jsp").forward(req, resp);
					} else {
						// on envoie un bean requestScope avec les dates setup
						// du trade
						// TODO recuper les date de trade et les placer dans un
						// bean
						req.getRequestDispatcher("jsp/trade/trade_center.jsp").forward(req, resp);
					}

				}

			}

			break;

		case 2:
			switch (cycleAnnuel) {

			case 1:
				// pas de trade possible, on envoie sur trade center sans
				// récupérer les offres
				req.setAttribute("messageTrade", "Il n'y a pas de trade possible à cet période du pool");
				req.getRequestDispatcher("jsp/trade/trade_center.jsp").forward(req, resp);
				break;
			case 2:
				// pas de trade possible, on envoie sur trade center sans
				// récupérer les offres
				req.setAttribute("messageTrade", "Il n'y a pas de trade possible à cet période du pool");
				req.getRequestDispatcher("jsp/trade/trade_center.jsp").forward(req, resp);
				break;
			case 3:

				// on recupere les trade recu du datastore et place dans bean
				// pour affichage
				mModelTrade = new TradeModel(req, mBean);
				mModelTrade.getMyTrade();
				req.getRequestDispatcher("jsp/trade/trade_center.jsp").forward(req, resp);
				break;
			case 4:
				// pas de trade possible, on envoie sur trade center sans
				// récupérer les offres
				req.getRequestDispatcher("jsp/trade/trade_center.jsp").forward(req, resp);
				break;
			case 5:
				// on recupere les trade recu du datastore et place dans bean
				// pour affichage
				mModelTrade = new TradeModel(req, mBean);
				mModelTrade.getMyTrade();
				req.getRequestDispatcher("jsp/trade/trade_center.jsp").forward(req, resp);
				break;
			}

			break;
			
		case 3:
			switch (cycleAnnuel) {

			case 1:
				// pas de trade possible, on envoie sur trade center sans
				// récupérer les offres
				req.setAttribute("messageTrade", "Il n'y a pas de trade possible à cet période du pool");
				req.getRequestDispatcher("jsp/trade/trade_center.jsp").forward(req, resp);
				break;
			case 2:
				// pas de trade possible, on envoie sur trade center sans
				// récupérer les offres
				req.setAttribute("messageTrade", "Il n'y a pas de trade possible à cet période du pool");
				req.getRequestDispatcher("jsp/trade/trade_center.jsp").forward(req, resp);
				break;
			case 3:

				// on recupere les trade recu du datastore et place dans bean
				// pour affichage
				mModelTrade = new TradeModel(req, mBean);
				mModelTrade.getAllTrade();
				req.getRequestDispatcher("jsp/trade/trade_center.jsp").forward(req, resp);
				break;
			case 4:
				// pas de trade possible, on envoie sur trade center sans
				// récupérer les offres
				req.getRequestDispatcher("jsp/trade/trade_center.jsp").forward(req, resp);
				break;
			case 5:
				// Si tradeType==1, on recupere les trade recu du datastore et
				// place dans bean pour affichage
				mModelTrade = new TradeModel(req, mBean);
				mModelTrade.getAllTrade();
				req.getRequestDispatcher("jsp/trade/trade_center.jsp").forward(req, resp);

			}
			break;

		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String tradeTag = req.getParameter("tradeTag");
		int tradeTagId = Integer.parseInt(tradeTag);
		
		switch(tradeTagId){
		// Un joueur veut faire une offre à un autre équipe
		case 1:
			break;
		// Un joueur veut valider si son offre est possible et recevoir un message de confirmation a cliquer pour envoyer l'offre
		case 2:
			break;
		// Un joueur veut confirmer son offre de trade et l'envoyer
		case 3:
			break;
		// Un joueur veut annuler une offre qu'il a faite
		case 4:
			break;
		// Un joueur veut modifier une offre qu'il a faite
		case 5:
			break;
		// Un joueur veut voir les details d'une offre qu'il a faite
		case 6:
			break;
		// Un jouer veut voir les detai d'une offre qu'il a recu
		case 7:
			break;
		// Un joueur veut accepter une offre qu'il a recu (verification de la valider de l'offre et message de confirmation de l'offre
		case 8:
			break;
		// Un joueur veut confirmer l'Echange pour persister celle-ci
		case 9:
			break;
		// Un joueur veut annuler une offre recu
		case 10:
			break;
		// Un joueur veut faire une contre-offre a une offre recu
			
		}

	}

}
