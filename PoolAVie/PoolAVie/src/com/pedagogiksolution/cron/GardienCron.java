package com.pedagogiksolution.cron;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pedagogiksolution.cron.model.PlayersCronModel;
import com.pedagogiksolution.dao.DAOFactory;
import com.pedagogiksolution.dao.PlayersDao;

public class GardienCron extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 945679182244107130L;
    public static final String CONF_DAO_FACTORY = "daofactory";
    private PlayersDao playersDao;

    public void init() throws ServletException {
	/* R�cup�ration d'une instance de notre DAO Utilisateur */
	this.playersDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getPlayersDao();

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	// initilisation du m�tier
	PlayersCronModel mModel = new PlayersCronModel(playersDao);
	
	String position="gardien";
	int recrue = 0;
	int numberOfPool = mModel.getNumberOfPool();

	if (numberOfPool == 0) {
	    //TODO  log de l'erreur
	} else {

	    for (int i = 1; i < (numberOfPool + 1); i++) {
		
		int numberOfTeam = mModel.getNumberOfTeamByPool(i);
		
		if(numberOfTeam==0){
		    //TODO persister un erreur et un lanceur d'Alerte
		} else {
		    mModel.putDatabaseInDatastore(i,numberOfTeam,position,recrue);
		}
		
		
	    }

	}

    }
}