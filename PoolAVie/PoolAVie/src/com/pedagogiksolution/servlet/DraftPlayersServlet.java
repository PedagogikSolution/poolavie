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
import com.pedagogiksolution.dao.TradeMadeDao;
import com.pedagogiksolution.dao.TradeOfferDao;
import com.pedagogiksolution.datastorebeans.Pool;
import com.pedagogiksolution.model.DraftPlayersModel;
import com.pedagogiksolution.model.LoginModel;

public class DraftPlayersServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2767768156351834050L;
	public static final String CONF_DAO_FACTORY = "daofactory";
	
	private DraftDao draftDao;

	@Override
	public void init() throws ServletException {

		
		this.draftDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getDraftDao();

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
		String segment = req.getParameter("seg");
		String sort = req.getParameter("sort");
		if(segment==null){
		    
		    segment = (String) req.getSession().getAttribute("Segment");
		    sort = (String) req.getSession().getAttribute("Sort");
		    
		}
		
		DraftPlayersModel mModel = new DraftPlayersModel(req, segment, sort);

		mModel.showPlayersSortByParameter();

		Pool mBean = (Pool) req.getSession().getAttribute("Pool");
		int cycleAnnuel = mBean.getCycleAnnuel();

		if (cycleAnnuel == 3) {

			DraftPlayersModel mModelDraft = new DraftPlayersModel();

			mModelDraft.putDatastoreIntoBean(mBean, req);

		}
		
		/** mini bean pour affichage menu secondaire du draft en shadow ****/
		switch(segment){
		case "all":
		    req.setAttribute("trierPar",1);
		    break;
		case "foward":
		    req.setAttribute("trierPar",2);
		    break;
		case "defenseur":
		    req.setAttribute("trierPar",3);
		    break;
		case "goaler":
		    req.setAttribute("trierPar",4);
		    break;
		case "rookie":
		    req.setAttribute("trierPar",5);
		    break;
		}
		

		req.getRequestDispatcher("jsp/draft/draft_pick_zone.jsp").forward(req, resp);

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		DraftPlayersModel mModel;
		String draftStep = req.getParameter("draftStep");
		Boolean checkIfDraftFinish;

		switch (Integer.parseInt(draftStep)) {

		case 1: // provient du choix fait a la page draft_pick_zone
			mModel = new DraftPlayersModel(req, resp);
			mModel.checkIfDraftIsPossible();
			req.getRequestDispatcher("jsp/draft/draft_pick_confirmation.jsp").forward(req, resp);
			break;
		case 2: // persistence d'un pick dans club regulier
			mModel = new DraftPlayersModel(req, resp);
			mModel.persistenceDraftPickRegulier();
			mModel.channelMessage(1);
			checkIfDraftFinish = mModel.checkIfDraftFinish(draftDao);
			if (checkIfDraftFinish) {
				mModel.persistDraftFinishForUser();
				Boolean checkifDraftFinishForAll = mModel.checkifDraftFinishForAll();
				if (checkifDraftFinishForAll) {
					mModel.changeCycleAnnuelToSignature4();
					mModel.channelMessage(3);
				} else {
					req.getSession().setAttribute("DraftFinish", 1);
				}
			}
			resp.sendRedirect("/DraftCenter");
			break;
		case 3: // persitence d'un pick dans club recrue
			mModel = new DraftPlayersModel(req, resp);
			mModel.persistenceDraftPickRookie();
			
			
			mModel.channelMessage(2);
			checkIfDraftFinish = mModel.checkIfDraftFinish(draftDao);
			if (checkIfDraftFinish) {
				mModel.persistDraftFinishForUser();
				Boolean checkifDraftFinishForAll = mModel.checkifDraftFinishForAll();
				if (checkifDraftFinishForAll) {
					mModel.changeCycleAnnuelToSignature4();
					mModel.channelMessage(4);
				} else {
				    req.getSession().setAttribute("DraftFinish", 1);
				}
			} 
			resp.sendRedirect("/DraftCenter");
			break;
		case 4:
			break;

		}

	}

}
