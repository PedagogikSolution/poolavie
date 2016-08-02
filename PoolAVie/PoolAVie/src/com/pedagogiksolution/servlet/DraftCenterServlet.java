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

public class DraftCenterServlet extends HttpServlet {
    /**
     * 
     */
    private static final long serialVersionUID = 54388717965389157L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
	
	Pool mBean = (Pool) req.getSession().getAttribute("Pool");
	int cycleAnnuel = mBean.getCycleAnnuel();
	
	if (cycleAnnuel == 3) {
	    LoginModel mModel = new LoginModel(req);
	    mModel.createSessionDraftRoundBean();
	    mModel.createSessionEquipeBean();
	    mModel.createSessionDraftPickBean();
	    mModel.createSessionAttaquantBean();
	    mModel.createSessionDefenseurBean();
	    mModel.createSessionGardienBean();
	    mModel.createSessionRecrueBean();
	}
	
	if(cycleAnnuel>=2){
	    EquipeModel mModel = new EquipeModel();
		mModel.getBeanByTeam(req);
	}
	
	if(cycleAnnuel==3){
	    
	    DraftPlayersModel mModelDraft = new DraftPlayersModel();
	    	   
	    mModelDraft.putDatastoreIntoBean(mBean,req);	    
 
	    
	}
	
	req.getRequestDispatcher("jsp/draft/draft_center.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
    }
    
    
    

}
