package com.pedagogiksolution.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pedagogiksolution.datastorebeans.Pool;
import com.pedagogiksolution.model.DraftPlayersModel;
import com.pedagogiksolution.model.EquipeModel;
import com.pedagogiksolution.model.LoginModel;

public class EquipesServlet extends HttpServlet {
   
    private static final long serialVersionUID = 54388717965389157L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");
	int cycleAnnuel = mBeanPool.getCycleAnnuel();
	if (cycleAnnuel == 3) {
	  
	    
	    DraftPlayersModel mModelDraft = new DraftPlayersModel();	    	   
	    mModelDraft.putDatastoreIntoBean(mBeanPool,req);
	}
	
	
	    LoginModel mModel = new LoginModel(req);
	    mModel.createSessionEquipeBean();
	    mModel.createSessionAttaquantBean();
	    mModel.createSessionDefenseurBean();
	    mModel.createSessionGardienBean();
	    mModel.createSessionRecrueBean();
	    mModel.createSessionDraftPickBean();
	    
	
	EquipeModel mModelTeam = new EquipeModel();
	mModelTeam.getBeanByTeam(req);
	
	
        
	req.getRequestDispatcher("jsp/team/team.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	req.getRequestDispatcher("jsp/team/team.jsp").forward(req, resp);

    }

}
