package com.pedagogiksolution.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pedagogiksolution.dao.DAOFactory;
import com.pedagogiksolution.dao.DraftPickDao;
import com.pedagogiksolution.dao.PlayersDao;
import com.pedagogiksolution.model.TaskQueueModel;

public class TaskQueueCreationPool extends HttpServlet {

    public static final String CONF_DAO_FACTORY = "daofactory";
    private DraftPickDao draftPickDao;
    private PlayersDao playerDao;

    @Override
    public void init() throws ServletException {
	/* Récupération d'une instance de notre DAO Utilisateur */
	this.draftPickDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getDraftPickDao();
	this.playerDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getPlayersDao();

    }

    /**
     * 
     */
    private static final long serialVersionUID = 5153659828527340877L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	TaskQueueModel mModel;
	String fromTag = req.getParameter("fromTag");
	int fromTagId = Integer.parseInt(fromTag);

	switch (fromTagId) {
	case 1:
	    mModel = new TaskQueueModel(req);
	    mModel.createDatastoreEquipe();
	    break;
	case 2:
	    mModel = new TaskQueueModel(req, playerDao);
	    mModel.createDatastorePlayers();
	    break;
	case 3:
	    mModel = new TaskQueueModel(req, playerDao);
	    mModel.createDatastoreAttaquant();
	    break;
	case 4:
	    mModel = new TaskQueueModel(req, playerDao);
	    mModel.createDatastoreDefenseur();
	    break;
	case 5:
	    mModel = new TaskQueueModel(req, playerDao);
	    mModel.createDatastoreGardien();
	    break;
	case 6:
	    mModel = new TaskQueueModel(req, playerDao);
	    mModel.createDatastoreRecrue();
	    break;

	case 7:
	    mModel = new TaskQueueModel(req, draftPickDao);
	    mModel.createDatastoreDraftPick();
	    break;

	}

    }

}
