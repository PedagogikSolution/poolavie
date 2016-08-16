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

public class TaskQueueDraftPlayer extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = -4856982923052270731L;
	/**
     * 
     */
    
    
    public static final String CONF_DAO_FACTORY = "daofactory";
    private DraftDao draftDao;
    private PlayersDao playerDao;

    @Override
    public void init() throws ServletException {
	/* Récupération d'une instance de notre DAO Utilisateur */
	this.draftDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getDraftDao();
	this.playerDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getPlayersDao();

    }

   
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    
    	
    	TaskQueueModel mModel = new TaskQueueModel(draftDao,playerDao,req);
    	
    	
    	mModel.persistPlayer();
    	mModel.persistDraftRound();
	
    }
    
   

}
