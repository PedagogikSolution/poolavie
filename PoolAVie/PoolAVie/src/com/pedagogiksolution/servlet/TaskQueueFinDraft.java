package com.pedagogiksolution.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pedagogiksolution.dao.DAOFactory;
import com.pedagogiksolution.dao.DraftDao;
import com.pedagogiksolution.dao.PlayersDao;
import com.pedagogiksolution.model.TaskQueueModel;

public class TaskQueueFinDraft extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = -4856982923052270731L;
	/**
     * 
     */
    
    
    public static final String CONF_DAO_FACTORY = "daofactory";
    private DraftDao draftDao;

    @Override
    public void init() throws ServletException {
	/* Recuperation d'une instance de notre DAO Utilisateur */
	this.draftDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getDraftDao();

    }

   
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    	String currentPick = req.getParameter("currentPick");
    	String poolID =  req.getParameter("poolID");
    	String teamID =  req.getParameter("teamID");
    	draftDao.deleteDraftPickWhenFinishPicking(poolID,teamID,currentPick);
    	
    	
    	
	
    }
    
   

}
