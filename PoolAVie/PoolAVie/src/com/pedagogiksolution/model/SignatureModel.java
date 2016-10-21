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

    
    public void signatureAfterDraft(HttpServletRequest req) {
	String nombreAnneeSignature = req.getParameter("nombreAnneeSignature");
	int numberOfYear = Integer.parseInt(nombreAnneeSignature);
	String draft_player_id = req.getParameter("draft_player_id");
	int playerId = Integer.parseInt(draft_player_id);
	String position = req.getParameter("position");
	String salaire = req.getParameter("salaire");
	Utilisateur mBeanUser = (Utilisateur) req.getSession().getAttribute("Utilisateur");
	int teamId = mBeanUser.getTeamId();
	int poolId = mBeanUser.getPoolId();
	Key entityKey = null;
	Key clefMemCache = null;
	Attaquant mBeanAttaquant = null;
	Defenseur mBeanDefenseur = null;
	Gardien mBeanGardien = null;
	String datastoreID = poolId + "_" + teamId;
	Entity entity = null, equipeEntity = null; 
	Key equipeKey;
	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

	// A: on change la valeur des years_x dans la bdd players_x
	playersDao.signPlayerAfterDraft(teamId, poolId, draft_player_id, salaire, numberOfYear);

	// B: on modifie les datatstore attaquant/def/goal/

	/*switch (position) {
	case "attaquant":
	    clefMemCache = KeyFactory.createKey("Attaquant", datastoreID);

	    entityKey = KeyFactory.createKey("Attaquant", datastoreID);
	    break;
	case "defenseur":
	    clefMemCache = KeyFactory.createKey("Defenseur", datastoreID);
	    mBeanDefenseur = new Defenseur();
	    entityKey = KeyFactory.createKey("Defenseur", datastoreID);
	    break;
	case "gardien":
	    clefMemCache = KeyFactory.createKey("Gardien", datastoreID);
	    mBeanGardien = new Gardien();
	    entityKey = KeyFactory.createKey("Gardien", datastoreID);
	    break;
	}

	try {

	    entity = datastore.get(entityKey);

	    mapDatastoreInTempArray(entity, position);
	    
	    int indexOfPlayersToSign = 1;//players_id.indexOf(playerId);

	    

	    switch (numberOfYear) {
	    case 1:
		years_3.set(indexOfPlayersToSign, "X");
		years_4.set(indexOfPlayersToSign, "X");
		years_5.set(indexOfPlayersToSign, "X");

		break;
	    case 2:
		years_2.set(indexOfPlayersToSign, salaire);
		years_3.set(indexOfPlayersToSign, "X");
		years_4.set(indexOfPlayersToSign, "X");
		years_5.set(indexOfPlayersToSign, "X");
		break;
	    case 3:
		years_2.set(indexOfPlayersToSign, salaire);
		years_3.set(indexOfPlayersToSign, salaire);
		years_4.set(indexOfPlayersToSign, "X");
		years_5.set(indexOfPlayersToSign, "X");
		break;
	    case 4:
		years_2.set(indexOfPlayersToSign, salaire);
		years_3.set(indexOfPlayersToSign, salaire);
		years_4.set(indexOfPlayersToSign, salaire);
		years_5.set(indexOfPlayersToSign, "X");
		break;

	    case 5:
		years_2.set(indexOfPlayersToSign, salaire);
		years_3.set(indexOfPlayersToSign, salaire);
		years_4.set(indexOfPlayersToSign, salaire);
		years_5.set(indexOfPlayersToSign, salaire);
		break;

	    }
	    datastore.put(entity);

	    String jspName;
	    switch (position) {
	    case "attaquant":
		jspName = "Attaquant" + teamId;
		mBeanAttaquant = (Attaquant) req.getSession().getAttribute(jspName);
		mBeanAttaquant.setAide_overtime(aide_overtime);
		mBeanAttaquant.setBlanchissage(blanchissage);
		mBeanAttaquant.setBut_victoire(but_victoire);
		mBeanAttaquant.setNom(nom2);
		mBeanAttaquant.setPj(pj);
		mBeanAttaquant.setPts(pts);
		mBeanAttaquant.setTeamOfPlayer(teamOfPlayer);
		mBeanAttaquant.setYears_1(years_1);
		mBeanAttaquant.setYears_2(years_2);
		mBeanAttaquant.setYears_3(years_3);
		mBeanAttaquant.setYears_4(years_4);
		mBeanAttaquant.setYears_5(years_5);

		memcache.put(clefMemCache, mBeanAttaquant);
		break;
	    case "defenseur":
		jspName = "Defenseur" + teamId;
		mBeanDefenseur = (Defenseur) req.getSession().getAttribute(jspName);
		mBeanDefenseur.setAide_overtime(aide_overtime);
		mBeanDefenseur.setBlanchissage(blanchissage);
		mBeanDefenseur.setBut_victoire(but_victoire);
		mBeanDefenseur.setNom(nom2);
		mBeanDefenseur.setPj(pj);
		mBeanDefenseur.setPts(pts);
		mBeanDefenseur.setTeamOfPlayer(teamOfPlayer);
		mBeanDefenseur.setYears_1(years_1);
		mBeanDefenseur.setYears_2(years_2);
		mBeanDefenseur.setYears_3(years_3);
		mBeanDefenseur.setYears_4(years_4);
		mBeanDefenseur.setYears_5(years_5);
		memcache.put(clefMemCache, mBeanDefenseur);
		break;
	    case "gardien":
		jspName = "Gardien" + teamId;
		mBeanGardien = (Gardien) req.getSession().getAttribute(jspName);
		mBeanGardien.setAide_overtime(aide_overtime);
		mBeanGardien.setBlanchissage(blanchissage);
		mBeanGardien.setBut_victoire(but_victoire);
		mBeanGardien.setNom(nom2);
		mBeanGardien.setPj(pj);
		mBeanGardien.setPts(pts);
		mBeanGardien.setTeamOfPlayer(teamOfPlayer);
		mBeanGardien.setYears_1(years_1);
		mBeanGardien.setYears_2(years_2);
		mBeanGardien.setYears_3(years_3);
		mBeanGardien.setYears_4(years_4);
		mBeanGardien.setYears_5(years_5);
		memcache.put(clefMemCache, mBeanGardien);
		break;
	    }

	} catch (EntityNotFoundException e) {

	}
	*/

	// C: on modifie le datastore equipe
	
	equipeKey = KeyFactory.createKey("Equipe", datastoreID);

	    try {
		equipeEntity = datastore.get(equipeKey);

		

		Long temp_nb_contrat = (Long) equipeEntity.getProperty("nb_contrat");
		int nb_contrat =  temp_nb_contrat.intValue();
		nb_contrat = nb_contrat + 1;
		equipeEntity.setProperty("nb_contrat", nb_contrat);
		datastore.put(equipeEntity);

		String jspName;
		jspName = "Equipe" + teamId;
		clefMemCache = KeyFactory.createKey("Equipe", datastoreID);
		Equipe mBeanEquipe = new Equipe();
		mBeanEquipe = (Equipe) req.getSession().getAttribute(jspName);

		mBeanEquipe.setNb_contrat(nb_contrat);
		
		
		req.getSession().setAttribute(jspName, mBeanEquipe);

	    } catch (EntityNotFoundException e) {

	    }

    }

    @SuppressWarnings("unchecked")
    private void mapDatastoreInTempArray(Entity entity, String position) {

	List<Integer> testIfEmpty = (List<Integer>) entity.getProperty("players_id");

	if (testIfEmpty != null) {

	    players_id.addAll((List<Integer>) entity.getProperty("players_id"));
	    nom2.addAll((List<String>) entity.getProperty("nom"));
	    teamOfPlayer.addAll((List<String>) entity.getProperty("teamOfPlayer"));
	    pj.addAll((List<Integer>) entity.getProperty("pj"));
	    but_victoire.addAll((List<Integer>) entity.getProperty("but_victoire"));
	    aide_overtime.addAll((List<Integer>) entity.getProperty("aide_overtime"));
	    if (position.equalsIgnoreCase("Gardien")) {
		blanchissage.addAll((List<Integer>) entity.getProperty("blanchissage"));
	    }
	    pts.addAll((List<Integer>) entity.getProperty("pts"));
	    years_1.addAll((List<String>) entity.getProperty("years_1"));
	    years_2.addAll((List<String>) entity.getProperty("years_2"));
	    years_3.addAll((List<String>) entity.getProperty("years_3"));
	    years_4.addAll((List<String>) entity.getProperty("years_4"));
	    years_5.addAll((List<String>) entity.getProperty("years_5"));
	    salaire_contrat.addAll((List<Integer>) entity.getProperty("salaire_contrat"));
	}
    }

}
