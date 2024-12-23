package com.pedagogiksolution.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pedagogiksolution.datastorebeans.Pool;
import com.pedagogiksolution.model.DraftPlayersModel;
import com.pedagogiksolution.model.LoginModel;

public class ReglementServlet extends HttpServlet {
    /**
     * 
     */
    private static final long serialVersionUID = 54388717965389157L;

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
	    mModelDraft.putDatastoreIntoBean(mBeanPool,req);
	}
	
	req.getRequestDispatcher("jsp/reglement/reglements.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
    }
    
    
    

}
