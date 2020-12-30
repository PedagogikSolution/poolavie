package com.pedagogiksolution.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.pedagogiksolution.dao.ClassementDao;
import com.pedagogiksolution.dao.DAOFactory;
import com.pedagogiksolution.dao.DraftDao;
import com.pedagogiksolution.dao.DraftPickDao;
import com.pedagogiksolution.dao.PlayersDao;
import com.pedagogiksolution.dao.SalaireDao;
import com.pedagogiksolution.dao.TradeMadeDao;
import com.pedagogiksolution.dao.TradeOfferDao;
import com.pedagogiksolution.datastorebeans.Equipe;
import com.pedagogiksolution.datastorebeans.Pool;
import com.pedagogiksolution.datastorebeans.Utilisateur;
import com.pedagogiksolution.model.DraftPlayersModel;
import com.pedagogiksolution.model.EquipeModel;
import com.pedagogiksolution.model.LoginModel;

public class AdminTeamServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 831617662010238450L;
	/**
	 * 
	 */
	

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
		/* Recuperation d'une instance de notre nos DAO */
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
		LoginModel mModel2 = new LoginModel(req);
		mModel2.createSessionEquipeBean();
		mModel2.createSessionAttaquantBean();
		mModel2.createSessionDefenseurBean();
		mModel2.createSessionGardienBean();
		mModel2.createSessionRecrueBean();
		mModel2.createSessionDraftPickBean();
		mModel2.createSessionDraftRoundBean();
		mModel2.createSessionPoolBean();
		
		
		Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");
		int cycleAnnuel = mBeanPool.getCycleAnnuel();
		if (cycleAnnuel == 3) {

			DraftPlayersModel mModelDraft = new DraftPlayersModel();
			mModelDraft.putDatastoreIntoBean(mBeanPool, req);
		}

		
		EquipeModel mModelTeam = new EquipeModel();
		mModelTeam.getBeanByTeam(req);
		String teamId = req.getParameter("team");
	    req.setAttribute("team", teamId);
		req.getRequestDispatcher("jsp/admin/team_update.jsp").forward(req, resp);

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String teamUpdate = req.getParameter("teamUpdate");
		int teamUpateRequestNumber = Integer.parseInt(teamUpdate);
		EquipeModel mModelEquipe = new EquipeModel();
		String teamId;
		
		
		switch (teamUpateRequestNumber) {
		
		case 1:
			mModelEquipe.updateEquipeBudget(req);
			teamId = req.getParameter("team");
		    req.setAttribute("team", teamId);
			resp.sendRedirect("/AdminTeam?teamId="+teamId);
			break;
		case 2 :
			mModelEquipe.updateEquipeStats(req);
			teamId = req.getParameter("team");
		    req.setAttribute("team", teamId);
			resp.sendRedirect("/AdminTeam?teamId="+teamId);
			break;
		}
		
		
	
		
		

	}

}
