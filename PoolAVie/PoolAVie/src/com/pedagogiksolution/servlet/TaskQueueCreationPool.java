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

public class TaskQueueCreationPool extends HttpServlet {
  
      
    public static final String CONF_DAO_FACTORY = "daofactory";
    private DraftDao draftDao;
    private PlayersDao playerDao;

    @Override
    public void init() throws ServletException {
	/* Récupération d'une instance de notre DAO Utilisateur */
	this.draftDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getDraftDao();
	this.playerDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getPlayersDao();

    }
   
    /**
     * 
     */
    private static final long serialVersionUID = 5153659828527340877L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        
	
	String fromTag = req.getParameter("fromTag");
	int fromTagId = Integer.parseInt(fromTag);
	
	
    	
    	TaskQueueModel mModel = new TaskQueueModel(req);
    	
    	switch(fromTagId){
    	case 1:
    	mModel.createDatastoreEquipe();
    	    break;
    	case 2:
    	mModel.createDatastorePlayers(playerDao);
    	    break;
    	case 3:
        	mModel.createDatastoreAttaquant(playerDao);
        	    break;
    	case 4:
        	mModel.createDatastoreDefenseur(playerDao);
        	    break;
    	case 5:
        	mModel.createDatastoreGardien(playerDao);
        	    break;
    	case 6:
        	mModel.createDatastoreRecrue(playerDao);
        	    break;
    	    
    	}
    	
	
    }
    
   

}
