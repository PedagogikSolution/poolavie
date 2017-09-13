package com.pedagogiksolution.servlet;

import static com.pedagogiksolution.constants.MessageErreurConstants.CREATE_POOL_PAS_FINI;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.pedagogiksolution.beans.MessageErreurBeans;
import com.pedagogiksolution.dao.ClassementDao;
import com.pedagogiksolution.dao.DAOFactory;
import com.pedagogiksolution.dao.DraftDao;
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
	private ClassementDao classementDao;
	private DraftDao draftDao;

	@Override
	public void init() throws ServletException {

		this.playersDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getPlayersDao();
		this.draftPickDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getDraftPickDao();
		this.tradeOfferDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getTradeOfferDao();
		this.tradeMadeDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getTradeMadeDao();
		this.classementDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getClassementDao();
		this.draftDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getDraftDao();

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		TradeModel mModelTrade;
		// on recupere le poolID et le teamId du Session Bean Utilisateur
		Utilisateur mBean = (Utilisateur) req.getSession().getAttribute("Utilisateur");

		// int teamId = mBean.getTeamId();
		int poolId = mBean.getPoolId();

		Pool mBeanPool = new Pool();

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Key clefDatastore = KeyFactory.createKey("Pool", Integer.toString(poolId));
		try {
			// si existe, aucun EntityNotFoundException, donc on recup�re
			// l'info pour tester password
			Entity mEntity = datastore.get(clefDatastore);

			// on met dans SessionBean
			HttpSession session = req.getSession();
			mBeanPool = mBeanPool.mapPoolFromDatastore(mEntity, mBeanPool);
			synchronized (session) {
				session.setAttribute("Pool", mBeanPool);
			}

		} catch (EntityNotFoundException e) {
			MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
			mBeanMessageErreur.setErreurFormulaireLogin(CREATE_POOL_PAS_FINI);
			req.setAttribute("MessageErreurBeans", mBeanMessageErreur);

		}

		int cycleAnnuel = mBeanPool.getCycleAnnuel();

		if (cycleAnnuel == 3) {

			DraftPlayersModel mModelDraft = new DraftPlayersModel();
			mModelDraft.putDatastoreIntoBean(mBeanPool, req);
		}

		LoginModel mModel = new LoginModel(req);
		mModel.createSessionEquipeBean();
		mModel.createSessionAttaquantBean();
		mModel.createSessionDefenseurBean();
		mModel.createSessionGardienBean();
		mModel.createSessionRecrueBean();
		mModel.createSessionDraftPickBean();

		int fromId;
		String fromID = req.getParameter("from");
		if (fromID != null) {
			fromId = Integer.parseInt(fromID);
		} else {
			fromId = 1;
		}

		switch (fromId) {

		case 1:
			// selon la p�riode du pool, on recupere ou pas les offres de trades
			switch (cycleAnnuel) {

			case 0:
				// pas de trade possible, on envoie sur trade center sans
				req.setAttribute("messageTrade", "Il n'y a pas de trade possible à cet période du pool");
				req.getRequestDispatcher("jsp/trade/trade_center.jsp").forward(req, resp);
				break;

			case 1:
				// pas de trade possible, on envoie sur trade center sans
				req.setAttribute("messageTrade", "Il n'y a pas de trade possible à cet période du pool");
				req.getRequestDispatcher("jsp/trade/trade_center.jsp").forward(req, resp);
				break;
			case 2:
				// pas de trade possible, on envoie sur trade center sans
				req.setAttribute("messageTrade", "Il n'y a pas de trade possible à cet période du pool");
				req.getRequestDispatcher("jsp/trade/trade_center.jsp").forward(req, resp);
				break;
			case 3:

				// on recupere les trade recu du datastore et place dans bean
				// pour affichage
				mModelTrade = new TradeModel(req, mBeanPool);
				mModelTrade.getTradeOfferReceived(req, tradeOfferDao);
				mModelTrade.getTradeOfferMade(req, tradeOfferDao);
				req.setAttribute("tradeOpen", 1);
				req.getRequestDispatcher("jsp/trade/trade_center.jsp").forward(req, resp);
				break;
			case 4:
				// pas de trade possible, on envoie sur trade center sans
				// r�cup�rer les offres
				req.getRequestDispatcher("jsp/trade/trade_center.jsp").forward(req, resp);
				break;
			case 5:
				// Si tradeType==1, on recupere les trade recu du datastore et
				// place dans bean pour affichage
				mModelTrade = new TradeModel(req, mBeanPool);
				int tradeType = mBeanPool.getTradeType();
				if (tradeType == 2) {
					req.setAttribute("tradeOpen", 1);
					mModelTrade.getTradeOfferReceived(req, tradeOfferDao);
					mModelTrade.getTradeOfferMade(req, tradeOfferDao);
					req.getRequestDispatcher("jsp/trade/trade_center.jsp").forward(req, resp);
				} else {
					req.setAttribute("tradeOpen", 0);
					req.setAttribute("messageTrade", "Il n'y a pas de trade possible � cet p�riode du pool");
					req.getRequestDispatcher("jsp/trade/trade_center.jsp").forward(req, resp);

				}
				break;

			case 6:

				mModelTrade = new TradeModel(req, mBeanPool);
				req.setAttribute("tradeOpen", 1);
				mModelTrade.getTradeOfferReceived(req, tradeOfferDao);
				mModelTrade.getTradeOfferMade(req, tradeOfferDao);
				req.getRequestDispatcher("jsp/trade/trade_center.jsp").forward(req, resp);

				break;

			case 7:

				// pas de trade possible, on envoie sur trade center sans
				req.setAttribute("messageTrade", "Il n'y a pas de trade possible à cet période du pool");
				req.getRequestDispatcher("jsp/trade/trade_center.jsp").forward(req, resp);

				break;

			case 8:

				// pas de trade possible, on envoie sur trade center sans
				req.setAttribute("messageTrade", "Il n'y a pas de trade possible à cet période du pool");
				req.getRequestDispatcher("jsp/trade/trade_center.jsp").forward(req, resp);

				break;

			case 9:

				// pas de trade possible, on envoie sur trade center sans
				req.setAttribute("messageTrade", "Il n'y a pas de trade possible à cet période du pool");
				req.getRequestDispatcher("jsp/trade/trade_center.jsp").forward(req, resp);

			case 10:
				// pas de trade possible, on envoie sur trade center sans
				req.setAttribute("messageTrade", "Il n'y a pas de trade possible à cet période du pool");
				req.getRequestDispatcher("jsp/trade/trade_center.jsp").forward(req, resp);

				break;
			case 11:

				mModelTrade = new TradeModel(req, mBeanPool);
				req.setAttribute("tradeOpen", 1);
				mModelTrade.getTradeOfferReceived(req, tradeOfferDao);
				mModelTrade.getTradeOfferMade(req, tradeOfferDao);
				req.getRequestDispatcher("jsp/trade/trade_center.jsp").forward(req, resp);

				break;
			case 12:

				// pas de trade possible, on envoie sur trade center sans
				req.setAttribute("messageTrade", "Il n'y a pas de trade possible à cet période du pool");
				req.getRequestDispatcher("jsp/trade/trade_center.jsp").forward(req, resp);

				break;
			case 13:

				// pas de trade possible, on envoie sur trade center sans
				req.setAttribute("messageTrade", "Il n'y a pas de trade possible à cet période du pool");
				req.getRequestDispatcher("jsp/trade/trade_center.jsp").forward(req, resp);

				break;
			}

			break;

		case 2:
			mModelTrade = new TradeModel(req, mBeanPool);
			mModelTrade.getMyTrade(req, tradeMadeDao);
			req.getRequestDispatcher("jsp/trade/my_trade.jsp").forward(req, resp);

			break;

		case 3:
			mModelTrade = new TradeModel(req, mBeanPool);
			mModelTrade.getAllTrade(req, tradeMadeDao);
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

		// Un joueur veut faire une offre � un autre �quipe
		case 1:

			// on met a jour les objet de session pour etre certain d'avoir la plus recente
			// representation des donn�es
			mModel = new LoginModel(req);
			mModel.createSessionDraftPickBean();
			mModel.createSessionAttaquantBean();
			mModel.createSessionDefenseurBean();
			mModel.createSessionGardienBean();
			mModel.createSessionRecrueBean();
			mModel.createSessionDraftRoundBean();

			// on place les beans de session dans des beans temporaires pour les deux
			// equipes
			mModelTrade = new TradeModel(mBeanUser, mBeanPool, req);
			testErreur = mModelTrade.getDataOnTeamThatTrade();

			// on met un token pour indiquer que la section est accessible a ce moment du
			// cycle (empeche quelqu'un de
			// connecter d'aller directemetn a URL .jsp si pas bon cycle)
			req.setAttribute("tradeOpen", 1);

			// on test si recuperation des donn�es des deux equipes a jour fait , si oui on
			// retourne message erreur a
			// trade_center,
			// si pas d'erreur on va a page d'Affichage pour faire offre
			if (testErreur) {
				req.getRequestDispatcher("jsp/trade/trade_center.jsp").forward(req, resp);
			} else {
				req.getRequestDispatcher("jsp/trade/trade_offer_sheet.jsp").forward(req, resp);
			}

			break;
		// Un joueur veut valider si son offre est possible et recevoir un message de
		// confirmation a cliquer pour
		// envoyer l'offre
		case 2:
			mModelTrade = new TradeModel(mBeanUser, mBeanPool, req);
			Boolean offerGood = mModelTrade.checkIfTradeIsValid(playersDao, draftPickDao, draftDao);
			if (!offerGood) {
				req.setAttribute("tradeOpen", 1);
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
			mModel.createSessionDraftRoundBean();
			Boolean tradeStillPosible = mModelTrade.checkIfTradeIsStillPossible(playersDao, draftPickDao, tradeOfferDao,
					draftDao);

			if (!tradeStillPosible) {
				// si pas bon on annule l'offre et renvoie avec un message erreur expliquant
				// l'annulation sur la page
				// trade center
				mModelTrade.annulerOffre(req, tradeOfferDao);
				req.setAttribute("tradeOpen", 1);
				req.getRequestDispatcher("jsp/trade/trade_center.jsp").forward(req, resp);

			} else {
				// on effectue l'Echange et on persiste celle-ci dans trade_made

				mModelTrade.makeTrade(playersDao, draftPickDao, tradeOfferDao, draftDao);
				mModelTrade.persistTrade(tradeMadeDao);

				mModelTrade.annulerOffre(req, tradeOfferDao);

				// on roule les cron jobs des deux equipes du pool

				mModelTrade.putDatabaseInDatastore(classementDao, playersDao, draftPickDao, req);

				// on force re-ecriture dans la session dans un filtre

				resp.sendRedirect("/Equipes");
			}

			break;
		// Un joueur veut annuler une offre qu'il a faite
		case 5:

			mModelTrade = new TradeModel(mBeanUser, mBeanPool, req);
			mModelTrade.annulerOffre(req, tradeOfferDao);
			resp.sendRedirect("/Trade");

			break;
		// Un joueur veut voir les details d'une offre qu'il a faite
		case 6:

			mModelTrade = new TradeModel(mBeanUser, mBeanPool, req);
			mModelTrade.showOfferNumberX(1, tradeOfferDao, tradeMadeDao, playersDao, draftPickDao, draftDao);
			req.setAttribute("whichShow", 6);
			req.getRequestDispatcher("jsp/trade/showOfferDetail.jsp").forward(req, resp);

			break;
		// Un jouer veut voir les details d'une offre qu'il a recu
		case 7:

			mModelTrade = new TradeModel(mBeanUser, mBeanPool, req);
			mModelTrade.showOfferNumberX(1, tradeOfferDao, tradeMadeDao, playersDao, draftPickDao, draftDao);
			req.setAttribute("whichShow", 7);
			req.getRequestDispatcher("jsp/trade/showOfferDetail.jsp").forward(req, resp);

			break;
		// Un joueur veut voir les d�tails d'un trade qu'il a fait
		case 8:
			mModelTrade = new TradeModel(mBeanUser, mBeanPool, req);
			mModelTrade.showOfferNumberX(2, tradeOfferDao, tradeMadeDao, playersDao, draftPickDao, draftDao);

			req.getRequestDispatcher("jsp/trade/showOfferDetailTradeMade.jsp").forward(req, resp);
			break;
		// Un joueur veut voir les d�tails d'un trade fait par n'importe quelle �quipe
		case 9:
			mModelTrade = new TradeModel(mBeanUser, mBeanPool, req);
			mModelTrade.showOfferNumberX(2, tradeOfferDao, tradeMadeDao, playersDao, draftPickDao, draftDao);

			req.getRequestDispatcher("jsp/trade/showOfferDetailTradeMade.jsp").forward(req, resp);
			break;

		}

	}

}
