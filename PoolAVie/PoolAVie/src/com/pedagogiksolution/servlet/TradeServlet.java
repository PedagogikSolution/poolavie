package com.pedagogiksolution.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pedagogiksolution.datastorebeans.Pool;
import com.pedagogiksolution.datastorebeans.Utilisateur;
import com.pedagogiksolution.model.DraftPlayersModel;
import com.pedagogiksolution.model.LoginModel;
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

	if (cycleAnnuel == 3) {

	    DraftPlayersModel mModelDraft = new DraftPlayersModel();
	    mModelDraft.putDatastoreIntoBean(mBean, req);
	}

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

	    case 0:
		// pas de trade possible, on envoie sur trade center sans
		// récupérer les offres
		req.setAttribute("messageTrade", "Il n'y a pas de trade possible à cet période du pool");
		req.getRequestDispatcher("jsp/trade/trade_center.jsp").forward(req, resp);
		break;

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
		    req.setAttribute("tradeOpen", 0); 
		    req.setAttribute("messageTrade", "Il n'y a pas de trade possible à cet période du pool");
			req.getRequestDispatcher("jsp/trade/trade_center.jsp").forward(req, resp);
		   

		}
		break;

	    case 6:
		mModelTrade = new TradeModel(req, mBean);
		req.setAttribute("tradeOpen", 1);
		mModelTrade.getTradeOfferReceived();
		mModelTrade.getTradeOfferMade();
		req.getRequestDispatcher("jsp/trade/trade_center.jsp").forward(req, resp);

		break;
	    }

	    break;

	case 2:
	    mModelTrade = new TradeModel(req, mBean);
	    mModelTrade.getMyTrade();
	    req.getRequestDispatcher("jsp/trade/trade_center.jsp").forward(req, resp);

	    break;

	case 3:
	    mModelTrade = new TradeModel(req, mBean);
	    mModelTrade.getAllTrade();
	    req.getRequestDispatcher("jsp/trade/trade_center.jsp").forward(req, resp);

	}

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	TradeModel mModelTrade;
	Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");
	Utilisateur mBeanUser = (Utilisateur) req.getSession().getAttribute("Utilisateur");
	int cycleAnnuel;
	String tradeTag = req.getParameter("tradeTag");
	int tradeTagId = Integer.parseInt(tradeTag);

	switch (tradeTagId) {

	// Un joueur veut faire une offre à un autre équipe
	case 1:

	    // on met a jour les objet de session pour etre certain d'avoir la plus recente representation des données
	    LoginModel mModel = new LoginModel(req);
	    mModel.createSessionDraftPickBean();
	    mModel.createSessionAttaquantBean();
	    mModel.createSessionDefenseurBean();
	    mModel.createSessionGardienBean();
	    mModel.createSessionRecrueBean();
	    mModel.createSessionDraftRoundBean();

	    // on place les beans de session dans des beans temporaires pour les deux equipes
	    mModelTrade = new TradeModel(mBeanUser, mBeanPool, req);
	    Boolean testErreur = mModelTrade.getDataOnTeamThatTrade();

	    // on met un token pour indiquer que la section est accessible a ce moment du cycle (empeche quelqu'un de
// connecter d'aller directemetn a URL .jsp si pas bon cycle)
	    req.setAttribute("tradeOpen", 1);

	    if (testErreur) {
		req.getRequestDispatcher("jsp/trade/trade_center.jsp").forward(req, resp);
	    } else {
		req.getRequestDispatcher("jsp/trade/trade_offer_sheet.jsp").forward(req, resp);
	    }

	    break;
	// Un joueur veut valider si son offre est possible et recevoir un message de confirmation a cliquer pour
// envoyer l'offre
	case 2:
	    mModelTrade = new TradeModel(mBeanUser, mBeanPool, req);
	    Boolean offerGood = mModelTrade.checkIfTradeIsValidDuringDraft();
	    if (offerGood) {
		req.getRequestDispatcher("jsp/trade/trade_offer_sheet.jsp").forward(req, resp);
	    } else {
		req.getRequestDispatcher("jsp/trade/trade_offer__confirmation_sheet.jsp").forward(req, resp);
	    }
	    break;
	// Un joueur veut confirmer son offre de trade et l'envoyer
	case 3:
	    mModelTrade = new TradeModel(mBeanUser, mBeanPool, req);
	    mModelTrade.persistTradeOffer();
	    cycleAnnuel = mBeanPool.getCycleAnnuel();
	    if (cycleAnnuel == 3) {
		mModelTrade.sendAlertViaChannel();
	    } else {
		mModelTrade.sendEmailForOffer();
	    }
	    req.getRequestDispatcher("jsp/trade/trade_center.jsp").forward(req, resp);
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
	// Un jouer veut voir les details d'une offre qu'il a recu
	case 7:
	    break;
	// Un joueur veut accepter une offre qu'il a recu (verification de la valider de l'offre et message de
// confirmation de l'offre
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
