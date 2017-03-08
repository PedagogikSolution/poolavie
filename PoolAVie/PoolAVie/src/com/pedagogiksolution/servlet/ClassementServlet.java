package com.pedagogiksolution.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pedagogiksolution.datastorebeans.Pool;
import com.pedagogiksolution.model.DraftPlayersModel;
import com.pedagogiksolution.model.LoginModel;

public class ClassementServlet extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 7516956807248666211L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");
	int cycleAnnuel = mBeanPool.getCycleAnnuel();
	if (cycleAnnuel == 3) {
	   	    
	    DraftPlayersModel mModelDraft = new DraftPlayersModel();	    	   
	    mModelDraft.putDatastoreIntoBean(mBeanPool,req);
	}
	// permet un update instantanné si un joueur est connecté et qu'il refresh ou renavigue sur cette page avant la création de tout les équipes
	if (cycleAnnuel < 3) {
   	    
	   LoginModel mModel = new LoginModel(req);
	   mModel.createSessionClassementBean();
	}
	
	if (cycleAnnuel == 6||cycleAnnuel == 5) {  
	    LoginModel mModel = new LoginModel(req);
	    mModel.createSessionEquipeBean();
	    mModel.createSessionAttaquantBean();
	    mModel.createSessionDefenseurBean();
	    mModel.createSessionGardienBean();
	    mModel.createSessionRecrueBean();
	    mModel.createSessionDraftPickBean();
	    mModel.createSessionClassementBean();
	    
	}
	
	
	
	    req.getRequestDispatcher("jsp/classement/classement.jsp").forward(req, resp);
	

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

}
