package com.pedagogiksolution.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pedagogiksolution.datastorebeans.Pool;
import com.pedagogiksolution.model.AdminModel;
import com.pedagogiksolution.model.DraftPlayersModel;

public class MonCompteServlet extends HttpServlet {

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
	
	    req.getRequestDispatcher("jsp/main/user.jsp").forward(req, resp);
	

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
	String username = req.getParameter("username");
	String password = req.getParameter("password");
	String email = req.getParameter("email");
	String teamName = req.getParameter("teamName");
	String logoTeam = req.getParameter("logoTeam");
	
	AdminModel mModel = new AdminModel();
	Boolean changeCredential = mModel.changeCredential(username,password,email,teamName,logoTeam,req);
	
	if(changeCredential){
	mModel.sendConfirmationEmailAfterChange(email,req);
	}
	
	req.getRequestDispatcher("jsp/main/user.jsp").forward(req, resp);

    }

}
