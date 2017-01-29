package com.pedagogiksolution.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pedagogiksolution.dao.DAOFactory;
import com.pedagogiksolution.dao.DraftPickDao;
import com.pedagogiksolution.dao.PlayersDao;
import com.pedagogiksolution.dao.TradeMadeDao;
import com.pedagogiksolution.dao.TradeOfferDao;
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
    public static final String CONF_DAO_FACTORY = "daofactory";
    private PlayersDao playersDao;
    private DraftPickDao draftPickDao;
    private TradeOfferDao tradeOfferDao;
    private TradeMadeDao tradeMadeDao;

    @Override
    public void init() throws ServletException {
	/* Récupération d'une instance de notre DAO Utilisateur */
	this.playersDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getPlayersDao();
	this.draftPickDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getDraftPickDao();
	this.tradeOfferDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getTradeOfferDao();
	this.tradeMadeDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getTradeMadeDao();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	TradeModel mModelTrade;
	Pool mBean = new Pool();
	mBean = (Pool) req.getSession().getAttribute("Pool");
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
		mModelTrade.getTradeOfferReceived(req, tradeOfferDao);
		mModelTrade.getTradeOfferMade(req, tradeOfferDao);
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
		    mModelTrade.getTradeOfferReceived(req, tradeOfferDao);
		    mModelTrade.getTradeOfferMade(req, tradeOfferDao);
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
		mModelTrade.getTradeOfferReceived(req, tradeOfferDao);
		mModelTrade.getTradeOfferMade(req, tradeOfferDao);
		req.getRequestDispatcher("jsp/trade/trade_center.jsp").forward(req, resp);

		break;
	    }

	    break;

	case 2:
	    mModelTrade = new TradeModel(req, mBean);
	    mModelTrade.getMyTrade();
	    req.getRequestDispatcher("jsp/trade/my_trade.jsp").forward(req, resp);

	    break;

	case 3:
	    mModelTrade = new TradeModel(req, mBean);
	    mModelTrade.getAllTrade();
	    req.getRequestDispatcher("jsp/trade/all_trade.jsp").forward(req, resp);

	}

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	TradeModel mModelTrade;
	LoginModel mModel;
	Boolean testErreur;
	Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");
	Utilisateur mBeanUser = (Utilisateur) req.getSession().getAttribute("Utilisateur");
	int cycleAnnuel;
	String tradeTag = req.getParameter("tradeTag");
	int tradeTagId = Integer.parseInt(tradeTag);

	switch (tradeTagId) {

	// Un joueur veut faire une offre à un autre équipe
	case 1:

	    // on met a jour les objet de session pour etre certain d'avoir la plus recente representation des données
	    mModel = new LoginModel(req);
	    mModel.createSessionDraftPickBean();
	    mModel.createSessionAttaquantBean();
	    mModel.createSessionDefenseurBean();
	    mModel.createSessionGardienBean();
	    mModel.createSessionRecrueBean();
	    mModel.createSessionDraftRoundBean();

	    // on place les beans de session dans des beans temporaires pour les deux equipes
	    mModelTrade = new TradeModel(mBeanUser, mBeanPool, req);
	    testErreur = mModelTrade.getDataOnTeamThatTrade();

	    // on met un token pour indiquer que la section est accessible a ce moment du cycle (empeche quelqu'un de
// connecter d'aller directemetn a URL .jsp si pas bon cycle)
	    req.setAttribute("tradeOpen", 1);

	    // on test si recuperation des données des deux equipes a jour fait , si oui on retourne message erreur a trade_center,
	    // si pas d'erreur on va a page d'Affichage pour faire offre
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
	    Boolean offerGood = mModelTrade.checkIfTradeIsValid(playersDao, draftPickDao);
	    if (!offerGood) {

		req.getRequestDispatcher("jsp/trade/trade_center.jsp").forward(req, resp);

	    } else {
		req.getRequestDispatcher("jsp/trade/trade_offer_confirmation_sheet.jsp").forward(req, resp);
	    }
	    break;
	// Un joueur veut confirmer son offre de trade et l'envoyer
	case 3:

	    String confirmation = req.getParameter("confirmation");

	    if (confirmation.equalsIgnoreCase("non")) {
		req.getSession().removeAttribute("tradeOfferBean");
		resp.sendRedirect("/Trade");
	    } else {
		mModelTrade = new TradeModel(mBeanUser, mBeanPool, req);
		mModelTrade.persistTradeOffer(req, tradeOfferDao);
		cycleAnnuel = mBeanPool.getCycleAnnuel();
		if (cycleAnnuel == 3) {
		    mModelTrade.sendAlertViaChannel();
		} else {
		    mModelTrade.sendEmailForOffer(req);
		}
		req.getSession().removeAttribute("tradeOfferBean");
		resp.sendRedirect("/Trade");
	    }
	    break;
	// Un joueur veut accepter une offre qu'il a faite
	case 4:
	    
	    // on verifie si l'offre tiens toujours avec un boolean
	    mModelTrade = new TradeModel(mBeanUser, mBeanPool, req);
	    mModel = new LoginModel(req);
	    mModel.createSessionEquipeBean();
	    Boolean tradeStillPosible = mModelTrade.checkIfTradeIsStillPossible(playersDao,draftPickDao,tradeOfferDao);
	   
	    if (!tradeStillPosible) {
		// si pas bon on annule l'offre et renvoie avec un message erreur expliquant l'annulation sur la page trade center
		mModelTrade.annulerOffre(req,tradeOfferDao);
		
		
		req.getRequestDispatcher("jsp/trade/trade_center.jsp").forward(req, resp);

	    } else {
		// on effectue l'Echange et on persiste celle-ci dans trade_made
		
		
		mModelTrade.makeTrade(playersDao,draftPickDao,tradeOfferDao);
		mModelTrade.persistTrade(tradeMadeDao);
		
		mModelTrade.annulerOffre(req, tradeOfferDao);
		
		
		resp.sendRedirect("/Equipes");
	    }
	    
	    resp.sendRedirect("/Trade");
	   
	    
	    
	    break;
	// Un joueur veut annuler une offre qu'il a faite
	case 5:
	    
	    mModelTrade = new TradeModel(mBeanUser, mBeanPool, req);
	    mModelTrade.annulerOffre(req,tradeOfferDao);
	    resp.sendRedirect("/Trade");
	    
	    break;
	// Un joueur veut voir les details d'une offre qu'il a faite
	case 6:
	    
	    mModelTrade = new TradeModel(mBeanUser, mBeanPool, req);
	    mModelTrade.showOfferNumberX(req,1,tradeOfferDao, playersDao, draftPickDao);
	    req.setAttribute("whichShow", 6);
	    req.getRequestDispatcher("jsp/trade/showOfferDetail.jsp").forward(req, resp);

	    break;
	// Un jouer veut voir les details d'une offre qu'il a recu
	case 7:

	    mModelTrade = new TradeModel(mBeanUser, mBeanPool, req);
	    mModelTrade.showOfferNumberX(req,2,tradeOfferDao, playersDao, draftPickDao);
	    req.setAttribute("whichShow", 7);
	    req.getRequestDispatcher("jsp/trade/showOfferDetail.jsp").forward(req, resp);
	    
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
