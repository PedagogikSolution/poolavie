 package com.pedagogiksolution.model;

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
	String isRookie = req.getParameter("isRookie");
	int isRookieId = Integer.parseInt(isRookie);
	
	if(isRookieId==0){
	playersDao.persistPlayerPick(playersId, salaireId, poolId, teamId, clubEcoleId, acquireYearsId);
	} else {
	playersDao.persistPlayerPickRookie(playersId, salaireId, poolId, teamId, clubEcoleId, acquireYearsId);   
	}

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
	
	String teamId = req.getParameter("teamId");
	String max_salaire_begin = req.getParameter("max_salaire_begin");
	String moy_sal_restant_draft = req.getParameter("moy_sal_restant_draft");
	String nb_attaquant = req.getParameter("nb_attaquant");
	String nb_defenseur = req.getParameter("nb_defenseur");
	String nb_gardien = req.getParameter("nb_gardien");
	String nb_rookie = req.getParameter("nb_rookie");
	String nb_contrat = req.getParameter("nb_contrat");
	String nb_equipe = req.getParameter("nb_equipe");
	String manquant_att = req.getParameter("manquant_att");
	String manquant_def = req.getParameter("manquant_def");
	String manquant_gardien = req.getParameter("manquant_gardien");
	String manquant_recrue = req.getParameter("manquant_recrue");
	String manquant_equipe = req.getParameter("manquant_equipe");
	String argent_recu = req.getParameter("argent_recu");
	
	String meilleur_classement = req.getParameter("meilleur_classement");
	
	String bonus_5m = req.getParameter("bonus_5m");
	String bonus_penalite = req.getParameter("bonus_penalite");
	String classement_last_year = req.getParameter("classement_last_year");
	String num_annee = req.getParameter("num_annee");
	String num_champion = req.getParameter("num_champion");
	String total_salaire_now = req.getParameter("total_salaire_now");
	
	
	
	
	String budget_restant = req.getParameter("budget_restant");
	String jspSessionName = poolID + "_" + counter;
	
	
	    Equipe mBean = new Equipe();
	    
	    mBean.setPoolTeamId(jspSessionName);
	    mBean.setBudget_restant(Integer.parseInt(budget_restant));    
	    mBean.setPoolId(Integer.parseInt(poolID));
	    mBean.setTeamId(Integer.parseInt(teamId));
	    mBean.setMax_salaire_begin(Integer.parseInt(max_salaire_begin));
	    mBean.setMoy_sal_restant_draft(Integer.parseInt(moy_sal_restant_draft));
	    mBean.setNb_attaquant(Integer.parseInt(nb_attaquant));
	    mBean.setNb_contrat(Integer.parseInt(nb_contrat));
	    mBean.setNb_defenseur(Integer.parseInt(nb_defenseur));
	    mBean.setNb_equipe(Integer.parseInt(nb_equipe));
	    mBean.setNb_gardien(Integer.parseInt(nb_gardien));
	    mBean.setNb_rookie(Integer.parseInt(nb_rookie));
	    mBean.setNum_annee(Integer.parseInt(num_annee));
	    mBean.setNum_champion(Integer.parseInt(num_champion));
	    mBean.setManquant_att(Integer.parseInt(manquant_att));
	    mBean.setManquant_def(Integer.parseInt(manquant_def));
	    mBean.setManquant_gardien(Integer.parseInt(manquant_gardien));
	    mBean.setManquant_equipe(Integer.parseInt(manquant_equipe));
	    mBean.setManquant_recrue(Integer.parseInt(manquant_recrue));
	    mBean.setMeilleur_classement(Integer.parseInt(meilleur_classement));
	    mBean.setArgent_recu(Integer.parseInt(argent_recu));
	    mBean.setClassement_last_year(Integer.parseInt(classement_last_year));
	    mBean.setBonus_5m(Integer.parseInt(bonus_5m));
	    mBean.setBonus_penalite(Integer.parseInt(bonus_penalite));
	    mBean.setTotal_salaire_now(Integer.parseInt(total_salaire_now));
	    
	    
	    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		Entity mEntity = mBean.mapBeanToEntityForDatastore(mBean,mBean.getPoolTeamId());
		
		datastore.put(mEntity);
	

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
	String blanchissage = req.getParameter("blanchissage");
	String pts = req.getParameter("pts");
	String projection = req.getParameter("projection");
	String position = req.getParameter("position");
	String can_be_rookie = req.getParameter("can_be_rookie");
	String salaire_draft = req.getParameter("salaire_draft");
	String contrat = req.getParameter("contrat");
	String club_ecole = req.getParameter("club_ecole");
	
	String poolID = req.getParameter("poolID");

	String playersTableName = "Players_" + poolID;
	Key datastoreKey = KeyFactory.createKey(playersTableName, players_id);

	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

	Entity playersEntity = new Entity(datastoreKey);
	playersEntity.setProperty("players_id", Integer.parseInt(players_id));
	playersEntity.setProperty("nom", nom);
	playersEntity.setProperty("teamOfPlayer", teamOfPlayer);
	playersEntity.setProperty("pj",Integer.parseInt(pj));
	playersEntity.setProperty("but_victoire", Integer.parseInt(but_victoire));
	playersEntity.setProperty("aide_overtime", Integer.parseInt(aide_overtime));
	playersEntity.setProperty("blanchissage", Integer.parseInt(blanchissage));
	playersEntity.setProperty("pts", Integer.parseInt(pts));
	playersEntity.setProperty("projection", Integer.parseInt(projection));
	playersEntity.setProperty("position", position);
	playersEntity.setProperty("can_be_rookie", Integer.parseInt(can_be_rookie));
	playersEntity.setProperty("salaire_draft", Integer.parseInt(salaire_draft));
	playersEntity.setProperty("contrat", Integer.parseInt(contrat));
	playersEntity.setProperty("club_ecole", Integer.parseInt(club_ecole));

	datastore.put(playersEntity);
	
	
    }

}
