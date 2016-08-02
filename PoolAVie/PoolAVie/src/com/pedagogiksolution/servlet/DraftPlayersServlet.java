package com.pedagogiksolution.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pedagogiksolution.datastorebeans.Pool;
import com.pedagogiksolution.model.DraftPlayersModel;

public class DraftPlayersServlet extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = -2767768156351834050L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	String segment = req.getParameter("seg");
	String sort = req.getParameter("sort");
	DraftPlayersModel mModel = new DraftPlayersModel(req, segment, sort);

	mModel.showPlayersSortByParameter();

	Pool mBean = (Pool) req.getSession().getAttribute("Pool");
	int cycleAnnuel = mBean.getCycleAnnuel();

	if (cycleAnnuel == 3) {

	    DraftPlayersModel mModelDraft = new DraftPlayersModel();

	    mModelDraft.putDatastoreIntoBean(mBean, req);

	}

	req.getRequestDispatcher("jsp/draft/draft_pick_zone.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	DraftPlayersModel mModel;	
	String draftStep = req.getParameter("draftStep");
	
	
	switch(Integer.parseInt(draftStep)){
	
	case 1: // provient du choix fait a la page draft_pick_zone
	    mModel=new DraftPlayersModel(req);
	    mModel.checkIfDraftIsPossible();
	    req.getRequestDispatcher("jsp/draft/draft_pick_confirmation.jsp").forward(req, resp);
	    break;
	case 2: // persistence d'un pick dans club regulier
	    mModel=new DraftPlayersModel(req);
	    mModel.persistenceDraftPickRegulier();
	    resp.sendRedirect("/DraftCenter");
	    break;
	case 3: // persitence d'un pick dans club recrue
	    break;
	case 4:
	    break;
	
	}
	
	
	
    }

}
