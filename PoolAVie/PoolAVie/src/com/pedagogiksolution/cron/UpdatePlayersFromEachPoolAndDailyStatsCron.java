package com.pedagogiksolution.cron;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.pedagogiksolution.cron.model.PlayersCronModel;
import com.pedagogiksolution.dao.DAOFactory;
import com.pedagogiksolution.dao.PlayersDao;

public class UpdatePlayersFromEachPoolAndDailyStatsCron extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 711576446718535213L;
	
	public static final String CONF_DAO_FACTORY = "daofactory";
    private PlayersDao playersDao;

    @Override
    public void init() throws ServletException {
	/* Récupération d'une instance de notre DAO Utilisateur */
	this.playersDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getPlayersDao();

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	// initilisation du métier
	PlayersCronModel mModel = new PlayersCronModel(playersDao);
	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	

	int numberOfPool = mModel.getNumberOfPool();

	if (numberOfPool == 0) {
	    //TODO  log de l'erreur
	} else {

	    for (int i = 1; i < (numberOfPool + 1); i++) {
	    	Key key = KeyFactory.createKey("Pool", String.valueOf(i));
			Entity entity;
			try {
			    entity = datastore.get(key);
			    Long cycleAnnuel = (Long) entity.getProperty("cycleAnnuel");
			    if(cycleAnnuel==5||cycleAnnuel==6){
			    	playersDao.updateTeam(i);
					playersDao.deletePlayersWithNoTeam(i);
					
					playersDao.addPlayersNotThere(i);
					
					playersDao.updateStats(i);
				}
			} catch (EntityNotFoundException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			}   
	    	
			
	    }

	}

    }
}
