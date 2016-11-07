package com.pedagogiksolution.model;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.pedagogiksolution.dao.PlayersDao;
import com.pedagogiksolution.datastorebeans.Attaquant;
import com.pedagogiksolution.datastorebeans.Defenseur;
import com.pedagogiksolution.datastorebeans.Equipe;
import com.pedagogiksolution.datastorebeans.Gardien;
import com.pedagogiksolution.datastorebeans.Utilisateur;

public class SignatureModel {
    List<Integer> players_id = new ArrayList<Integer>();
    List<String> nom2 = new ArrayList<String>();
    List<String> teamOfPlayer = new ArrayList<String>();
    List<Integer> pj = new ArrayList<Integer>();
    List<Integer> but_victoire = new ArrayList<Integer>();
    List<Integer> aide_overtime = new ArrayList<Integer>();
    List<Integer> blanchissage = new ArrayList<Integer>();
    List<Integer> pts = new ArrayList<Integer>();
    List<String> years_1 = new ArrayList<String>();
    List<String> years_2 = new ArrayList<String>();
    List<String> years_3 = new ArrayList<String>();
    List<String> years_4 = new ArrayList<String>();
    List<String> years_5 = new ArrayList<String>();
    List<Integer> salaire_contrat = new ArrayList<Integer>();
    private PlayersDao playersDao;

    public SignatureModel(PlayersDao playersDao) {
	this.playersDao = playersDao;
    }

    public void putPlayersThatCanBeSignInBean(HttpServletRequest req) {

	Utilisateur mBeanUser = (Utilisateur) req.getSession().getAttribute("Utilisateur");
	int teamId = mBeanUser.getTeamId();
	int poolId = mBeanUser.getPoolId();

	playersDao.getPlayersThatCanBeSign(teamId, poolId, req);

    }

    @SuppressWarnings("unchecked")
    public void signatureAfterDraft(HttpServletRequest req) {
	String nombreAnneeSignature = req.getParameter("nombreAnneeSignature");
	int numberOfYear = Integer.parseInt(nombreAnneeSignature);
	String draft_player_id = req.getParameter("draft_player_id");
	// int player_id = Integer.parseInt(draft_player_id);
	String salaire = req.getParameter("salaire");
	Utilisateur mBeanUser = (Utilisateur) req.getSession().getAttribute("Utilisateur");
	int teamId = mBeanUser.getTeamId();
	int poolId = mBeanUser.getPoolId();
	String datastoreID = poolId + "_" + teamId;

	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

	// A: on change la valeur des years_x dans la bdd players_x
	playersDao.signPlayerAfterDraft(teamId, poolId, draft_player_id, salaire, numberOfYear);

	// B: on modifie les datatstore attaquant/def/goal/
	String position = req.getParameter("position");

	/*switch (position) {

	case "attaquant":
	    String jspSessionName = "Attaquant" + teamId;
	    String datastoreId = poolId + "_" + teamId;

	    Attaquant mBeanAttaquant = new Attaquant();

	    datastore = DatastoreServiceFactory.getDatastoreService();
	    Key clefDatastore = KeyFactory.createKey("Attaquant", datastoreId);
	    try {
		// si existe, aucun EntityNotFoundException, donc on
		// recupère l'info pour tester password
		Entity mEntity = datastore.get(clefDatastore);
		players_id = (ArrayList<Integer>) mEntity.getProperty("players_id");
		int playersIdPosition = players_id.indexOf(draft_player_id);

		years_1 = (ArrayList<String>) mEntity.getProperty("years_1");
		years_2 = (ArrayList<String>) mEntity.getProperty("years_2");
		years_3 = (ArrayList<String>) mEntity.getProperty("years_3");
		years_4 = (ArrayList<String>) mEntity.getProperty("years_4");
		years_5 = (ArrayList<String>) mEntity.getProperty("years_5");

		switch (numberOfYear) {
		case 1: 
		    years_1.set(playersIdPosition, salaire);
		    years_2.set(playersIdPosition, "JA");
		    years_3.set(playersIdPosition, "X");
		    years_4.set(playersIdPosition, "X");
		    years_5.set(playersIdPosition, "X");
		    
		    break;
		case 2:
		    years_1.set(playersIdPosition, salaire);
		    years_2.set(playersIdPosition, salaire);
		    years_3.set(playersIdPosition, "X");
		    years_4.set(playersIdPosition, "X");
		    years_5.set(playersIdPosition, "X");
		    
		    break;
		case 3:
		    years_1.set(playersIdPosition, salaire);
		    years_2.set(playersIdPosition, salaire);
		    years_3.set(playersIdPosition, salaire);
		    years_4.set(playersIdPosition, "X");
		    years_5.set(playersIdPosition, "X");
		    break;
		case 4:
		    years_1.set(playersIdPosition, salaire);
		    years_2.set(playersIdPosition, salaire);
		    years_3.set(playersIdPosition, salaire);
		    years_4.set(playersIdPosition, salaire);
		    years_5.set(playersIdPosition, "X");
		    
		    break;
		case 5:
		    years_1.set(playersIdPosition, salaire);
		    years_2.set(playersIdPosition, salaire);
		    years_3.set(playersIdPosition, salaire);
		    years_4.set(playersIdPosition, salaire);
		    years_5.set(playersIdPosition, salaire);

		    break;
		}
		
		mEntity.setProperty("years_1", years_1);
		mEntity.setProperty("years_2", years_2);
		mEntity.setProperty("years_3", years_3);
		mEntity.setProperty("years_4", years_4);
		mEntity.setProperty("years_5", years_5);
		
		datastore.put(mEntity);

		mBeanAttaquant = (Attaquant) req.getSession().getAttribute(jspSessionName);
		
		mBeanAttaquant.setYears_1(years_1);
		mBeanAttaquant.setYears_2(years_2);
		mBeanAttaquant.setYears_3(years_3);
		mBeanAttaquant.setYears_4(years_4);
		mBeanAttaquant.setYears_5(years_5);
		
		req.getSession().setAttribute(jspSessionName, mBeanAttaquant);

	    } catch (EntityNotFoundException e) {
		
	    }
	    break;

	case "defenseur":
	    String jspSessionName2 = "Defenseur" + teamId;
	    String datastoreId2 = poolId + "_" + teamId;

	    Defenseur mBeanDefenseur = new Defenseur();

	    
	    Key clefDatastore2 = KeyFactory.createKey("Defenseur", datastoreId2);
	    try {
		// si existe, aucun EntityNotFoundException, donc on
		// recupère l'info pour tester password
		Entity mEntity = datastore.get(clefDatastore2);
		players_id = (ArrayList<Integer>) mEntity.getProperty("players_id");
		int playersIdPosition = players_id.indexOf(draft_player_id);

		years_1 = (ArrayList<String>) mEntity.getProperty("years_1");
		years_2 = (ArrayList<String>) mEntity.getProperty("years_2");
		years_3 = (ArrayList<String>) mEntity.getProperty("years_3");
		years_4 = (ArrayList<String>) mEntity.getProperty("years_4");
		years_5 = (ArrayList<String>) mEntity.getProperty("years_5");

		switch (numberOfYear) {
		case 1: 
		    years_1.set(playersIdPosition, salaire);
		    years_2.set(playersIdPosition, "JA");
		    years_3.set(playersIdPosition, "X");
		    years_4.set(playersIdPosition, "X");
		    years_5.set(playersIdPosition, "X");
		    
		    break;
		case 2:
		    years_1.set(playersIdPosition, salaire);
		    years_2.set(playersIdPosition, salaire);
		    years_3.set(playersIdPosition, "X");
		    years_4.set(playersIdPosition, "X");
		    years_5.set(playersIdPosition, "X");
		    
		    break;
		case 3:
		    years_1.set(playersIdPosition, salaire);
		    years_2.set(playersIdPosition, salaire);
		    years_3.set(playersIdPosition, salaire);
		    years_4.set(playersIdPosition, "X");
		    years_5.set(playersIdPosition, "X");
		    break;
		case 4:
		    years_1.set(playersIdPosition, salaire);
		    years_2.set(playersIdPosition, salaire);
		    years_3.set(playersIdPosition, salaire);
		    years_4.set(playersIdPosition, salaire);
		    years_5.set(playersIdPosition, "X");
		    
		    break;
		case 5:
		    years_1.set(playersIdPosition, salaire);
		    years_2.set(playersIdPosition, salaire);
		    years_3.set(playersIdPosition, salaire);
		    years_4.set(playersIdPosition, salaire);
		    years_5.set(playersIdPosition, salaire);

		    break;
		}
		
		mEntity.setProperty("years_1", years_1);
		mEntity.setProperty("years_2", years_2);
		mEntity.setProperty("years_3", years_3);
		mEntity.setProperty("years_4", years_4);
		mEntity.setProperty("years_5", years_5);
		
		datastore.put(mEntity);

		mBeanDefenseur = (Defenseur) req.getSession().getAttribute(jspSessionName2);
		
		mBeanDefenseur.setYears_1(years_1);
		mBeanDefenseur.setYears_2(years_2);
		mBeanDefenseur.setYears_3(years_3);
		mBeanDefenseur.setYears_4(years_4);
		mBeanDefenseur.setYears_5(years_5);
		
		req.getSession().setAttribute(jspSessionName2, mBeanDefenseur);

	    } catch (EntityNotFoundException e) {
		
	    }
	    break;
	case "gardien":
	    String jspSessionName3 = "Gardien" + teamId;
	    String datastoreId3 = poolId + "_" + teamId;

	    Gardien mBeanGardien = new Gardien();
	    datastore = DatastoreServiceFactory.getDatastoreService();
	    Key clefDatastore3 = KeyFactory.createKey("Gardien", datastoreId3);
	    try {
		// si existe, aucun EntityNotFoundException, donc on
		// recupère l'info pour tester password
		Entity mEntity = datastore.get(clefDatastore3);
		players_id = (ArrayList<Integer>) mEntity.getProperty("players_id");
		int playersIdPosition = players_id.indexOf(draft_player_id);

		years_1 = (ArrayList<String>) mEntity.getProperty("years_1");
		years_2 = (ArrayList<String>) mEntity.getProperty("years_2");
		years_3 = (ArrayList<String>) mEntity.getProperty("years_3");
		years_4 = (ArrayList<String>) mEntity.getProperty("years_4");
		years_5 = (ArrayList<String>) mEntity.getProperty("years_5");

		switch (numberOfYear) {
		case 1: 
		    years_1.set(playersIdPosition, salaire);
		    years_2.set(playersIdPosition, "JA");
		    years_3.set(playersIdPosition, "X");
		    years_4.set(playersIdPosition, "X");
		    years_5.set(playersIdPosition, "X");
		    
		    break;
		case 2:
		    years_1.set(playersIdPosition, salaire);
		    years_2.set(playersIdPosition, salaire);
		    years_3.set(playersIdPosition, "X");
		    years_4.set(playersIdPosition, "X");
		    years_5.set(playersIdPosition, "X");
		    
		    break;
		case 3:
		    years_1.set(playersIdPosition, salaire);
		    years_2.set(playersIdPosition, salaire);
		    years_3.set(playersIdPosition, salaire);
		    years_4.set(playersIdPosition, "X");
		    years_5.set(playersIdPosition, "X");
		    break;
		case 4:
		    years_1.set(playersIdPosition, salaire);
		    years_2.set(playersIdPosition, salaire);
		    years_3.set(playersIdPosition, salaire);
		    years_4.set(playersIdPosition, salaire);
		    years_5.set(playersIdPosition, "X");
		    
		    break;
		case 5:
		    years_1.set(playersIdPosition, salaire);
		    years_2.set(playersIdPosition, salaire);
		    years_3.set(playersIdPosition, salaire);
		    years_4.set(playersIdPosition, salaire);
		    years_5.set(playersIdPosition, salaire);

		    break;
		}
		
		mEntity.setProperty("years_1", years_1);
		mEntity.setProperty("years_2", years_2);
		mEntity.setProperty("years_3", years_3);
		mEntity.setProperty("years_4", years_4);
		mEntity.setProperty("years_5", years_5);
		
		datastore.put(mEntity);

		mBeanGardien = (Gardien) req.getSession().getAttribute(jspSessionName3);
		
		mBeanGardien.setYears_1(years_1);
		mBeanGardien.setYears_2(years_2);
		mBeanGardien.setYears_3(years_3);
		mBeanGardien.setYears_4(years_4);
		mBeanGardien.setYears_5(years_5);
		
		req.getSession().setAttribute(jspSessionName3, mBeanGardien);

	    } catch (EntityNotFoundException e) {
		
	    }
	    break;

	}
*/
	// C: on modifie le datastore equipe

	Key equipeKey = KeyFactory.createKey("Equipe", datastoreID);

	try {
	    Entity equipeEntity = datastore.get(equipeKey);
	    Long temp_nb_contrat = (Long) equipeEntity.getProperty("nb_contrat");
	    int nb_contrat = temp_nb_contrat.intValue();
	    nb_contrat = nb_contrat + 1;
	    equipeEntity.setProperty("nb_contrat", nb_contrat);
	    datastore.put(equipeEntity);

	    String jspName;
	    jspName = "Equipe" + teamId;

	    Equipe mBeanEquipe = (Equipe) req.getSession().getAttribute(jspName);
	    mBeanEquipe.setNb_contrat(nb_contrat);
	    req.getSession().setAttribute(jspName, mBeanEquipe);

	} catch (EntityNotFoundException e) {

	}

    }

    public Boolean checkIfSignatureIsPossible(HttpServletRequest req) {

	Utilisateur mBeanUser = (Utilisateur) req.getSession().getAttribute("Utilisateur");
	int teamId = mBeanUser.getTeamId();
	String jspNameEquipe = "Equipe" + teamId;

	Equipe mBeanEquipe = (Equipe) req.getSession().getAttribute(jspNameEquipe);
	int nb_contrat = mBeanEquipe.getNb_contrat();

	if (nb_contrat >= 12) {
	    return false;
	} else {
	    return true;
	}

    }

}
