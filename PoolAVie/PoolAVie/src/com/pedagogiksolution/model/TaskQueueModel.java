package com.pedagogiksolution.model;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpServletRequest;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.pedagogiksolution.dao.DraftDao;
import com.pedagogiksolution.dao.DraftPickDao;
import com.pedagogiksolution.dao.PlayersDao;
import com.pedagogiksolution.datastorebeans.Equipe;
import com.pedagogiksolution.utils.EMF;

public class TaskQueueModel {

    PlayersDao playersDao;
    DraftPickDao draftPickDao;
    HttpServletRequest req;
    DraftDao draftDao;

    public TaskQueueModel(DraftDao draftDao, PlayersDao playerDao, HttpServletRequest req) {

	this.playersDao = playerDao;
	this.draftDao = draftDao;
	this.req = req;
    }

    public TaskQueueModel(HttpServletRequest req2, PlayersDao playerDao) {
	this.req = req2;
	this.playersDao = playerDao;
    }

    public TaskQueueModel(HttpServletRequest req2) {
	this.req = req2;
    }

    public TaskQueueModel(HttpServletRequest req2, DraftPickDao draftPickDao2) {
	this.req = req2;
	this.draftPickDao = draftPickDao2;
    }

    public void persistPlayer() {
	String poolID = req.getParameter("poolID");
	int poolId = Integer.parseInt(poolID);
	String playersID = req.getParameter("playersID");
	int playersId = Integer.parseInt(playersID);
	String salaireID = req.getParameter("salaireID");
	int salaireId = Integer.parseInt(salaireID);
	String teamID = req.getParameter("teamID");
	int teamId = Integer.parseInt(teamID);
	String clubEcole = req.getParameter("club_ecole");
	int clubEcoleId = Integer.parseInt(clubEcole);
	String acquireYearsID = req.getParameter("acquireYearsID");
	int acquireYearsId = Integer.parseInt(acquireYearsID);

	playersDao.persistPlayerPick(playersId, salaireId, poolId, teamId, clubEcoleId, acquireYearsId);

    }

    public void persistDraftRound() {
	String poolID = req.getParameter("poolID");
	int poolId = Integer.parseInt(poolID);
	String nom = req.getParameter("nom");
	String currentPick = req.getParameter("currentPick");
	int currentPickId = Integer.parseInt(currentPick);
	int currentPickReel = currentPickId - 1;

	draftDao.persistPlayerPick(nom, currentPickReel, poolId);

    }

    public void createDatastoreEquipe() {

	String counter = req.getParameter("counter");
	String poolID = req.getParameter("poolID");
	String budget_restant = req.getParameter("budget_restant");
	String jspSessionName = poolID + "_" + counter;
	EntityManagerFactory emf = EMF.get();
	EntityManager em = null;
	try {

	    em = emf.createEntityManager();
	    Equipe mBean = new Equipe();
	    mBean.setPoolTeamId(jspSessionName);
	    mBean.setBudget_restant(Integer.parseInt(budget_restant));
	    em.persist(mBean);
	} finally {

	    // on ferme le manager pour libérer la mémoire
	    if (em != null) {
		em.close();

	    }
	}

    }

    public void createDatastorePlayers(String poolID) {

	playersDao.getPlayersForDatastoreFromPoolId(poolID);
	    
    }

    public void createDatastoreAttaquant() {
	String counter = req.getParameter("counter");
	String poolID = req.getParameter("poolID");

	playersDao.getPlayersForDatastoreFromPoolIdAndTeamNumber(poolID, counter, "attaquant", 0);

    }

    public void createDatastoreDefenseur() {
	String counter = req.getParameter("counter");
	String poolID = req.getParameter("poolID");

	playersDao.getPlayersForDatastoreFromPoolIdAndTeamNumber(poolID, counter, "defenseur", 0);

    }

    public void createDatastoreGardien() {
	String counter = req.getParameter("counter");
	String poolID = req.getParameter("poolID");

	playersDao.getPlayersForDatastoreFromPoolIdAndTeamNumber(poolID, counter, "gardien", 0);

    }

    public void createDatastoreRecrue() {
	String counter = req.getParameter("counter");
	String poolID = req.getParameter("poolID");

	playersDao.getPlayersForDatastoreFromPoolIdAndTeamNumber(poolID, counter, "recrue", 1);

    }

    public void createDatastoreDraftPick() {
	String counter = req.getParameter("counter");
	String poolID = req.getParameter("poolID");

	draftPickDao.getDraftPickForDatastoreFromPoolIdAndTeamNumber(poolID, counter);
    }

   
    public void createPlayersTable() {
	String players_id = req.getParameter("players_id");
	String nom = req.getParameter("nom");
	String teamOfPlayer = req.getParameter("teamOfPlayer");
	String pj = req.getParameter("pj");
	String but_victoire = req.getParameter("but_victoire");
	String aide_overtime = req.getParameter("aide_overtime");
	String blanchissage = req.getParameter("but_victoire");
	String pts = req.getParameter("aide_overtime");
	String projection = req.getParameter("but_victoire");
	String position = req.getParameter("aide_overtime");
	String can_be_rookie = req.getParameter("but_victoire");
	String salaire_draft = req.getParameter("aide_overtime");
	String contrat = req.getParameter("contrat");
	String club_ecole = req.getParameter("club_ecole");
	
	String poolID = req.getParameter("poolID");

	String playersTableName = "Players_" + poolID;
	Key datastoreKey = KeyFactory.createKey(playersTableName, players_id);

	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

	Entity playersEntity = new Entity(datastoreKey);
	playersEntity.setProperty("players_id", players_id);
	playersEntity.setProperty("nom", nom);
	playersEntity.setProperty("teamOfPlayer", teamOfPlayer);
	playersEntity.setProperty("pj", pj);
	playersEntity.setProperty("but_victoire", but_victoire);
	playersEntity.setProperty("aide_overtime", aide_overtime);
	playersEntity.setProperty("blanchissage", blanchissage);
	playersEntity.setProperty("pts", pts);
	playersEntity.setProperty("projection", projection);
	playersEntity.setProperty("position", position);
	playersEntity.setProperty("can_be_rookie", can_be_rookie);
	playersEntity.setProperty("salaire_draft", salaire_draft);
	playersEntity.setProperty("contrat", contrat);
	playersEntity.setProperty("club_ecole", club_ecole);

	datastore.put(playersEntity);
	
	
    }

}
