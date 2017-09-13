package com.pedagogiksolution.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pedagogiksolution.cron.model.PlayersCronModel;
import com.pedagogiksolution.dao.ClassementDao;
import com.pedagogiksolution.dao.DAOFactory;
import com.pedagogiksolution.dao.DraftDao;
import com.pedagogiksolution.dao.DraftPickDao;
import com.pedagogiksolution.dao.PlayersDao;
import com.pedagogiksolution.dao.SalaireDao;
import com.pedagogiksolution.dao.TradeMadeDao;
import com.pedagogiksolution.dao.TradeOfferDao;
import com.pedagogiksolution.datastorebeans.Pool;
import com.pedagogiksolution.model.AdminModel;
import com.pedagogiksolution.model.DraftPlayersModel;

public class AdminPoolServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 54388717965389157L;

	public static final String CONF_DAO_FACTORY = "daofactory";
	private PlayersDao playersDao;
	private DraftDao draftDao;
	private TradeMadeDao tradeMadeDao;
	private TradeOfferDao tradeOfferDao;
	private ClassementDao classementDao;
	private SalaireDao salaireDao;
	private DraftPickDao draftPickDao;

	@Override
	public void init() throws ServletException {
		/* R�cup�ration d'une instance de notre nos DAO */
		this.playersDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getPlayersDao();
		this.draftDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getDraftDao();
		this.tradeMadeDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getTradeMadeDao();
		this.tradeOfferDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getTradeOfferDao();
		this.classementDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getClassementDao();
		this.salaireDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getSalaireDao();
		this.draftPickDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getDraftPickDao();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");
		int cycleAnnuel = mBeanPool.getCycleAnnuel();
		if (cycleAnnuel == 3) {

			DraftPlayersModel mModelDraft = new DraftPlayersModel();
			mModelDraft.putDatastoreIntoBean(mBeanPool, req);
		}

		switch (cycleAnnuel) {
		case 0:
			req.getRequestDispatcher("jsp/admin/adminPool.jsp").forward(req, resp);
			break;
		case 1:
			req.getRequestDispatcher("jsp/admin/adminPool.jsp").forward(req, resp);
			break;
		case 2:
			req.getRequestDispatcher("jsp/admin/adminPool.jsp").forward(req, resp);
			break;
		case 3:
			req.getRequestDispatcher("jsp/admin/adminPool.jsp").forward(req, resp);
			break;
		case 4:
			req.getRequestDispatcher("jsp/admin/adminPool.jsp").forward(req, resp);
			break;
		case 5:
			req.getRequestDispatcher("jsp/admin/adminPool.jsp").forward(req, resp);
			break;
		case 6:
			req.getRequestDispatcher("jsp/admin/adminPool.jsp").forward(req, resp);
			break;
		case 7:
			req.getRequestDispatcher("jsp/admin/adminPool.jsp").forward(req, resp);
			break;
		case 8:
			req.getRequestDispatcher("jsp/admin/adminPool.jsp").forward(req, resp);
			break;
		case 9:
			req.getRequestDispatcher("jsp/admin/adminPool.jsp").forward(req, resp);
			break;
		case 10:
			req.getRequestDispatcher("jsp/admin/adminPool.jsp").forward(req, resp);
			break;
		case 11:
			req.getRequestDispatcher("jsp/admin/adminPool.jsp").forward(req, resp);
			break;
		case 12:
			req.getRequestDispatcher("jsp/admin/adminPool.jsp").forward(req, resp);
			break;
		case 13:
			req.getRequestDispatcher("jsp/admin/adminPool.jsp").forward(req, resp);
			break;
		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		AdminModel mAdminModel;
		String adminButton = req.getParameter("adminButton");
		int adminButtonInt = Integer.parseInt(adminButton);
		Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");
		String poolID = mBeanPool.getPoolID();
		PlayersCronModel mModel = new PlayersCronModel(playersDao);
		int numberOfTeam = mModel.getNumberOfTeamByPool(Integer.parseInt(poolID));

		switch (adminButtonInt) {

		case 1: // fin de la saison reguliere, on envoie message courriel et ajoute news, on
				// change cycle du pool pour le 7, ouvrant ainsi la période de rachat

			// on change le cycle du pool a 7
			mAdminModel = new AdminModel();

			mAdminModel.updateAgeForRookie(req, playersDao);

			mAdminModel.archivageFinSaison(req, classementDao, playersDao, tradeMadeDao, draftDao);

			mAdminModel.changeCycleAnnuel(req, 7);

			// TODO mAdminModel.writeNewsAndEmailForWinner(req);

			req.getRequestDispatcher("jsp/admin/adminPool.jsp").forward(req, resp);
			break;
		case 2:

			// on change le cycle a 8
			mAdminModel = new AdminModel();

			mAdminModel.changeCycleAnnuel(req, 8);

			req.getRequestDispatcher("jsp/admin/adminPool.jsp").forward(req, resp);
			break;
		case 3:
			// on lance la série de modification pour le reset de l'année
			mAdminModel = new AdminModel();

			mAdminModel.changeCycleAnnuel(req, 9);
			mAdminModel.updateAgeForRookie(req, playersDao);
			mAdminModel.resetDatastorePoolEntity(req);
			mAdminModel.dropJoueurJAETX(req, playersDao);

			mAdminModel.resetFinAnneePlayers(req, playersDao, salaireDao);

			mAdminModel.resetDatastoreEquipeEntity(req, playersDao);

			mAdminModel.vidageEtResetTableBDD(req, classementDao, tradeMadeDao, draftDao, tradeOfferDao);

			mBeanPool = (Pool) req.getSession().getAttribute("Pool");
			poolID = mBeanPool.getPoolID();
			mModel = new PlayersCronModel(playersDao);
			numberOfTeam = mModel.getNumberOfTeamByPool(Integer.parseInt(poolID));

			mModel.putDatabaseInDatastore(Integer.parseInt(poolID), numberOfTeam, "attaquant", 0, "3");
			mModel.putDatabaseInDatastore(Integer.parseInt(poolID), numberOfTeam, "attaquant", 1, "6");

			mModel.putDatabaseInDatastore(Integer.parseInt(poolID), numberOfTeam, "defenseur", 0, "4");
			mModel.putDatabaseInDatastore(Integer.parseInt(poolID), numberOfTeam, "defenseur", 1, "6");

			mModel.putDatabaseInDatastore(Integer.parseInt(poolID), numberOfTeam, "gardien", 0, "5");
			mModel.putDatabaseInDatastore(Integer.parseInt(poolID), numberOfTeam, "gardien", 1, "6");

			req.getRequestDispatcher("jsp/admin/adminPool.jsp").forward(req, resp);
			break;
		case 4:
			// fin de rachat 2
			mAdminModel = new AdminModel();
			mAdminModel.changeCycleAnnuel(req, 10);
			req.getRequestDispatcher("jsp/admin/adminPool.jsp").forward(req, resp);
			break;
		case 5:
			// fin de signatureAB

			mAdminModel = new AdminModel();
			mAdminModel.dropAB(req, playersDao);
			mBeanPool = (Pool) req.getSession().getAttribute("Pool");
			poolID = mBeanPool.getPoolID();
			mModel = new PlayersCronModel(playersDao);
			numberOfTeam = mModel.getNumberOfTeamByPool(Integer.parseInt(poolID));

			mModel.putDatabaseInDatastore(Integer.parseInt(poolID), numberOfTeam, "attaquant", 0, "3");
			mModel.putDatabaseInDatastore(Integer.parseInt(poolID), numberOfTeam, "attaquant", 1, "6");

			mModel.putDatabaseInDatastore(Integer.parseInt(poolID), numberOfTeam, "defenseur", 0, "4");
			mModel.putDatabaseInDatastore(Integer.parseInt(poolID), numberOfTeam, "defenseur", 1, "6");

			mModel.putDatabaseInDatastore(Integer.parseInt(poolID), numberOfTeam, "gardien", 0, "5");
			mModel.putDatabaseInDatastore(Integer.parseInt(poolID), numberOfTeam, "gardien", 1, "6");
			mAdminModel.changeCycleAnnuel(req, 11);
			req.getRequestDispatcher("jsp/admin/adminPool.jsp").forward(req, resp);
			break;
		case 6:
			// fin de trade
			mAdminModel = new AdminModel();
			mAdminModel.changeCycleAnnuel(req, 12);
			req.getRequestDispatcher("jsp/admin/adminPool.jsp").forward(req, resp);
			break;
		case 7:
			// fin de rachat 3
			mAdminModel = new AdminModel();

			mAdminModel.changeCycleAnnuel(req, 13);
			req.getRequestDispatcher("jsp/admin/adminPool.jsp").forward(req, resp);
			break;
		case 8:
			// fin de drop et promo rookie
			mAdminModel = new AdminModel();

			Boolean goodToGo = mAdminModel.preparationNouveauDraft(draftDao, req, playersDao, classementDao,
					draftPickDao);

			if (goodToGo) {
				mAdminModel.changeCycleAnnuel(req, 1);
			}

			req.getRequestDispatcher("jsp/admin/adminPool.jsp").forward(req, resp);
			break;
		case 9:

			mAdminModel = new AdminModel();
			mAdminModel.addCashForYears(req);
			mAdminModel.resetPlayersStats(req, playersDao);
			mAdminModel.changeCycleAnnuel(req, 5);
			Pool mBeanPool2 = (Pool) req.getSession().getAttribute("Pool");
			String poolID2 = mBeanPool2.getPoolID();
			PlayersCronModel mModelPlayers = new PlayersCronModel(playersDao);
			int numberOfTeam2 = mModelPlayers.getNumberOfTeamByPool(Integer.parseInt(poolID2));

			mModelPlayers.putDatabaseInDatastore(Integer.parseInt(poolID2), numberOfTeam2, "attaquant", 0, "3");
			mModelPlayers.putDatabaseInDatastore(Integer.parseInt(poolID2), numberOfTeam2, "attaquant", 1, "6");

			mModelPlayers.putDatabaseInDatastore(Integer.parseInt(poolID2), numberOfTeam2, "defenseur", 0, "4");
			mModelPlayers.putDatabaseInDatastore(Integer.parseInt(poolID2), numberOfTeam2, "defenseur", 1, "6");

			mModelPlayers.putDatabaseInDatastore(Integer.parseInt(poolID2), numberOfTeam2, "gardien", 0, "5");
			mModelPlayers.putDatabaseInDatastore(Integer.parseInt(poolID2), numberOfTeam2, "gardien", 1, "6");
			req.getRequestDispatcher("jsp/admin/adminPool.jsp").forward(req, resp);
			break;

		}

	}

}
