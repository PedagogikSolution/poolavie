package com.pedagogiksolution.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilter;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Transaction;
import com.google.appengine.api.datastore.TransactionOptions;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.pedagogiksolution.beans.MessageErreurBeans;
import com.pedagogiksolution.beans.NonSessionPlayers;
import com.pedagogiksolution.datastorebeans.Attaquant;
import com.pedagogiksolution.datastorebeans.Defenseur;
import com.pedagogiksolution.datastorebeans.DraftProcess;
import com.pedagogiksolution.datastorebeans.DraftRound;
import com.pedagogiksolution.datastorebeans.Equipe;
import com.pedagogiksolution.datastorebeans.Gardien;
import com.pedagogiksolution.datastorebeans.Players;
import com.pedagogiksolution.datastorebeans.Pool;
import com.pedagogiksolution.utils.EMF;

public class DraftPlayersModel {
    int ascDescOrder;
    String segment, sort;
    HttpServletRequest req;
    HttpServletResponse resp;
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    public DraftPlayersModel(HttpServletRequest req, String segment, String sort) {
	this.segment = segment;
	this.sort = sort;
	this.req = req;
    }

    public DraftPlayersModel() {
	// TODO Auto-generated constructor stub
    }

    public DraftPlayersModel(HttpServletRequest req2, HttpServletResponse resp2) {
	this.req = req2;
	this.resp = resp2;
    }

    public void showPlayersSortByParameter() {
	NonSessionPlayers mBean = new NonSessionPlayers();
	mBean = getDraftPlayersFromDatastore(sort, segment, mBean);

	req.setAttribute("SegmentSort", segment);
	req.setAttribute("NonSessionPlayers", mBean);

    }

    private NonSessionPlayers getDraftPlayersFromDatastore(String sort2, String segment2, NonSessionPlayers mBean) {

	List<Long> players_id = new ArrayList<Long>();
	List<String> nom = new ArrayList<String>();
	List<String> teamOfPlayer = new ArrayList<String>();
	List<Long> pj = new ArrayList<Long>();
	List<Long> but_victoire = new ArrayList<Long>();
	List<Long> aide_overtime = new ArrayList<Long>();
	List<Long> blanchissage = new ArrayList<Long>();
	List<Long> pts = new ArrayList<Long>();
	List<Long> projection = new ArrayList<Long>();
	List<String> position = new ArrayList<String>();
	List<Long> can_be_rookie = new ArrayList<Long>();
	List<Long> salaire_draft = new ArrayList<Long>();

	Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");
	String poolID = mBeanPool.getPoolID();

	String datastoreID = "Players_" + poolID;
	Query q = null;
	Filter noContrat;
	Filter byPosition;
	CompositeFilter mFiltre;

	if (req.getSession().getAttribute("ascDescOrder") == null) {
	    ascDescOrder = 0;
	} else {
	    ascDescOrder = (int) req.getSession().getAttribute("ascDescOrder");
	}
	Boolean fromMenu = false;
	String menu = req.getParameter("from");
	if (menu != null) {
	    fromMenu = true;
	}

	switch (segment2) {
	case "all":

	    Filter allPlayers = new FilterPredicate("contrat", FilterOperator.EQUAL, 0);

	    if (ascDescOrder == 0 || fromMenu) {
		q = new Query(datastoreID).addSort(sort2, Query.SortDirection.DESCENDING).setFilter(allPlayers);
		req.getSession().setAttribute("ascDescOrder", 1);
	    } else {
		q = new Query(datastoreID).addSort(sort2, Query.SortDirection.ASCENDING).setFilter(allPlayers);
		req.getSession().setAttribute("ascDescOrder", 0);
	    }

	    break;
	case "foward":
	    noContrat = new FilterPredicate("contrat", FilterOperator.EQUAL, 0);
	    byPosition = new FilterPredicate("position", FilterOperator.EQUAL, "attaquant");
	    mFiltre = CompositeFilterOperator.and(noContrat, byPosition);
	    if (ascDescOrder == 0 || fromMenu) {
		q = new Query(datastoreID).addSort(sort2, Query.SortDirection.DESCENDING).setFilter(mFiltre);
		req.getSession().setAttribute("ascDescOrder", 1);
	    } else {
		q = new Query(datastoreID).addSort(sort2, Query.SortDirection.ASCENDING).setFilter(mFiltre);
		req.getSession().setAttribute("ascDescOrder", 0);
	    }
	    break;
	case "defenseur":
	    noContrat = new FilterPredicate("contrat", FilterOperator.EQUAL, 0);
	    byPosition = new FilterPredicate("position", FilterOperator.EQUAL, "defenseur");
	    mFiltre = CompositeFilterOperator.and(noContrat, byPosition);
	    if (ascDescOrder == 0 || fromMenu) {
		q = new Query(datastoreID).addSort(sort2, Query.SortDirection.DESCENDING).setFilter(mFiltre);
		req.getSession().setAttribute("ascDescOrder", 1);
	    } else {
		q = new Query(datastoreID).addSort(sort2, Query.SortDirection.ASCENDING).setFilter(mFiltre);
		req.getSession().setAttribute("ascDescOrder", 0);
	    }
	    break;
	case "goaler":
	    noContrat = new FilterPredicate("contrat", FilterOperator.EQUAL, 0);
	    byPosition = new FilterPredicate("position", FilterOperator.EQUAL, "gardien");
	    mFiltre = CompositeFilterOperator.and(noContrat, byPosition);
	    if (ascDescOrder == 0 || fromMenu) {
		q = new Query(datastoreID).addSort(sort2, Query.SortDirection.DESCENDING).setFilter(mFiltre);
		req.getSession().setAttribute("ascDescOrder", 1);
	    } else {
		q = new Query(datastoreID).addSort(sort2, Query.SortDirection.ASCENDING).setFilter(mFiltre);
		req.getSession().setAttribute("ascDescOrder", 0);
	    }
	    break;
	case "rookie":
	    noContrat = new FilterPredicate("contrat", FilterOperator.EQUAL, 0);
	    byPosition = new FilterPredicate("can_be_rookie", FilterOperator.EQUAL, 1);
	    mFiltre = CompositeFilterOperator.and(noContrat, byPosition);
	    if (ascDescOrder == 0 || fromMenu) {
		q = new Query(datastoreID).addSort(sort2, Query.SortDirection.DESCENDING).setFilter(mFiltre);
		req.getSession().setAttribute("ascDescOrder", 1);
	    } else {
		q = new Query(datastoreID).addSort(sort2, Query.SortDirection.ASCENDING).setFilter(mFiltre);
		req.getSession().setAttribute("ascDescOrder", 0);
	    }
	    break;

	}

	// Use PreparedQuery interface to retrieve results
	List<Entity> pq = datastore.prepare(q).asList(FetchOptions.Builder.withChunkSize(200));

	for (Entity result : pq) {

	    Long m_players_id = (Long) result.getProperty("players_id");
	    players_id.add(m_players_id);
	    mBean.setPlayers_id(players_id);

	    String m_nom = (String) result.getProperty("nom");
	    nom.add(m_nom);
	    mBean.setNom(nom);

	    String m_teamOfPlayer = (String) result.getProperty("teamOfPlayer");
	    teamOfPlayer.add(m_teamOfPlayer);
	    mBean.setTeamOfPlayer(teamOfPlayer);

	    Long m_pj = (Long) result.getProperty("pj");
	    pj.add(m_pj);
	    mBean.setPj(pj);

	    Long m_but_victoire = (Long) result.getProperty("but_victoire");
	    but_victoire.add(m_but_victoire);
	    mBean.setBut_victoire(but_victoire);

	    Long m_aide_overtime = (Long) result.getProperty("aide_overtime");
	    aide_overtime.add(m_aide_overtime);
	    mBean.setAide_overtime(aide_overtime);

	    Long m_blanchissage = (Long) result.getProperty("blanchissage");
	    blanchissage.add(m_blanchissage);
	    mBean.setBlanchissage(blanchissage);

	    Long m_pts = (Long) result.getProperty("pts");
	    pts.add(m_pts);
	    mBean.setPts(pts);

	    Long m_projection = (Long) result.getProperty("projection");
	    projection.add(m_projection);
	    mBean.setProjection(projection);

	    String m_position = (String) result.getProperty("position");
	    position.add(m_position);
	    mBean.setPosition(position);

	    Long m_can_be_rookie = (Long) result.getProperty("can_be_rookie");
	    can_be_rookie.add(m_can_be_rookie);
	    mBean.setCan_be_rookie(can_be_rookie);

	    Long m_salaire_draft = (Long) result.getProperty("salaire_draft");
	    salaire_draft.add(m_salaire_draft);
	    mBean.setSalaire_draft(salaire_draft);
	}

	return mBean;

    }

    public void createDraftDatastoreForThatPool(Pool mBean) {

	String poolID = mBean.getPoolID();
	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	Key mKey = KeyFactory.createKey("DraftProcess", poolID);

	Entity mEntity = new Entity(mKey);
	mEntity.setProperty("currentPick", 1);
	datastore.put(mEntity);
    }

    public Boolean checkIfDatastoreCreate(Pool mBean) {

	String poolID = mBean.getPoolID();
	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	Key mKey = KeyFactory.createKey("DraftProcess", poolID);

	try {
	    datastore.get(mKey);
	    return true;
	} catch (EntityNotFoundException e) {
	    return false;
	}
    }

    public void putDatastoreIntoBean(Pool mBean, HttpServletRequest req2) {

	int currentPick2 = 0;
	DraftProcess mBeanDraft = new DraftProcess();
	String poolID = mBean.getPoolID();
	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	Key mKey = KeyFactory.createKey("DraftProcess", poolID);

	try {
	    Entity mEntity = datastore.get(mKey);
	    Long currentPick = (Long) mEntity.getProperty("currentPick");
	    currentPick2 = currentPick.intValue();

	    mBeanDraft.setCurrentPick(currentPick2);

	} catch (EntityNotFoundException e) {

	}

	DatastoreService datastore2 = DatastoreServiceFactory.getDatastoreService();
	Key mKey2 = KeyFactory.createKey("DraftRound", poolID);

	try {
	    Entity mEntity = datastore2.get(mKey2);

	    @SuppressWarnings("unchecked")
	    List<Long> currentPickerArray = (List<Long>) mEntity.getProperty("team_id");
	    Long currentPicker = (Long) currentPickerArray.get(currentPick2 - 1);
	    int currentPicker2 = currentPicker.intValue();
	    mBeanDraft.setCurrentPicker(currentPicker2);

	} catch (EntityNotFoundException e) {

	}

	req2.setAttribute("DraftBean", mBeanDraft);

    }

    public void checkIfDraftIsPossible() {

	String teamID = req.getParameter("team_id");
	int teamId = Integer.parseInt(teamID);
	// 0- son tour a drafter2
	Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");
	int currentPick2 = 0;
	int currentPicker2 = 0;
	String poolID = mBeanPool.getPoolID();
	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	Key mKey = KeyFactory.createKey("DraftProcess", poolID);

	try {
	    Entity mEntity = datastore.get(mKey);
	    Long currentPick = (Long) mEntity.getProperty("currentPick");
	    currentPick2 = currentPick.intValue();

	} catch (EntityNotFoundException e) {

	}

	DatastoreService datastore2 = DatastoreServiceFactory.getDatastoreService();
	Key mKey2 = KeyFactory.createKey("DraftRound", poolID);

	try {
	    Entity mEntity = datastore2.get(mKey2);

	    @SuppressWarnings("unchecked")
	    List<Long> currentPickerArray = (List<Long>) mEntity.getProperty("team_id");
	    Long currentPicker = (Long) currentPickerArray.get(currentPick2 - 1);
	    currentPicker2 = currentPicker.intValue();

	} catch (EntityNotFoundException e) {

	}

	if (teamId == currentPicker2) {

	} else {
	    MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
	    mBeanMessageErreur.setErreurDraft("Ce n'est pas votre tour de draft. Vous avez peut-être tenté de ré-envoyer un formulaire de draft précédent en cliquant sur refresh. Nous travaillons à rsoudre ce problème. Merci de notifier cette erreur dans le formulaire de rapport d'erreur  de la BETA TEST.");
	    req.setAttribute("messageErreur", mBeanMessageErreur);

	    return;
	}
	// 1- joueur encore dispo
	String playersID = req.getParameter("draft_player_id");
	String datastoreName = "Players_" + poolID;

	boolean playersStillAvailable = checkIfPlayersStillAvailable(playersID, datastoreName);

	if (playersStillAvailable) {

	} else {
	    MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
	    mBeanMessageErreur.setErreurDraft("Ce joueur n'est plaus disponible. Désolé de cette erreur. Merci de bien vouloir remplir le formualire de la BETA test pour nous le signaler");
	    req.setAttribute("messageErreur", mBeanMessageErreur);
	    return;
	}

	// 2- assez d'argent

	String salaire_draft = req.getParameter("salaire");
	Entity mEntity = getEntityEquipe(poolID, teamID);
	boolean checkIfCashIsOk = checkIfCashStillAvailable(mEntity, salaire_draft);

	if (checkIfCashIsOk) {

	} else {
	    MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
	    mBeanMessageErreur.setErreurDraft("Vous n'avez pas l'argent pour repêcher ce joueur sans éventuellement dépasser votre budget disponible au draft");
	    req.setAttribute("messageErreur", mBeanMessageErreur);
	    return;
	}

	// 3- manque position
	String position = req.getParameter("position");
	String positionJoueur = checkIfPositionWillBeOk(mEntity, position);

	if (positionJoueur == null) {

	} else {
	    MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
	    mBeanMessageErreur.setErreurDraft("Vous ne pouvez prendre ce joueur car ceci aurait pour influence de vous empêcher de repêcher un " + positionJoueur + ". Ainsi vous ne respecterez pas les règlements concernant les minimum de joueurs par position");
	    req.setAttribute("messageErreur", mBeanMessageErreur);
	    return;
	}

	// 4- manque recrue

	String can_be_rookie = req.getParameter("can_be_rookie");

	if (can_be_rookie.equals("0")) {
	    Boolean checkIfWillMissRookie = checkIfWillMissRookie(mEntity);
	    if (!checkIfWillMissRookie) {

	    } else {
		MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
		mBeanMessageErreur.setErreurDraft("Vous devez choisir un joueur recrue. Il ne vous reste de la place que pour les recrues dans votre équipe");
		req.setAttribute("messageErreur", mBeanMessageErreur);
		return;
	    }

	}

	// C'est bon, on met les info dans le bean et on envoie pour validation
	String nom = req.getParameter("nom");
	String salaire = req.getParameter("salaire");
	String team = req.getParameter("team");

	Players mBeanPlayer = new Players();
	mBeanPlayer.setPlayers_id(Integer.parseInt(playersID));
	mBeanPlayer.setNom(nom);
	mBeanPlayer.setPosition(position);
	mBeanPlayer.setTeamOfPlayer(team);
	mBeanPlayer.setSalaire_draft(Integer.parseInt(salaire));
	mBeanPlayer.setCan_be_rookie(Integer.parseInt(can_be_rookie));

	req.setAttribute("confirmationPick", mBeanPlayer);

    }

    @SuppressWarnings("unchecked")
    public void persistenceDraftPickRegulier() throws ServletException, IOException {
	// on recupere tous les variables utiles
	MemcacheService memcache = MemcacheServiceFactory.getMemcacheService();
	Key entityKey = null;
	Key clefMemCache = null;
	Attaquant mBeanAttaquant = null;
	Defenseur mBeanDefenseur = null;
	Gardien mBeanGardien = null;
	Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");
	String poolID = mBeanPool.getPoolID();
	int poolId = Integer.parseInt(poolID);
	int poolYear = mBeanPool.getPoolYear();
	String salaire = req.getParameter("salaire");
	int salaireId = Integer.parseInt(salaire);
	String playersID = req.getParameter("draft_player_id");
	int playersId = Integer.parseInt(playersID);
	String teamID = req.getParameter("team_id");
	int teamId = Integer.parseInt(teamID);
	String position = req.getParameter("position");
	String nom = req.getParameter("nom");
	String team = req.getParameter("team");
	Key mKey = KeyFactory.createKey("DraftProcess", poolID);
	int currentPick = 0;
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

	try {
	    Entity mEntity = datastore.get(mKey);
	    Long currentPick2 = (Long) mEntity.getProperty("currentPick");
	    currentPick = currentPick2.intValue();

	} catch (EntityNotFoundException e) {

	}
	String playersEntityName = "Players_" + poolID;
	String datastoreID = poolID + "_" + teamID;

	// on commence une transaction pour mettre a jour les datastores Players, Attaquant, Defenseur, Gardien, Equipe,
// draftRound, DraftPick,DraftProcess
	TransactionOptions options = TransactionOptions.Builder.withXG(true);
	Transaction txn = datastore.beginTransaction(options);
	try {

	    // table players_X
	    Key playersKey = KeyFactory.createKey(playersEntityName, playersId);
	    Entity players = null;
	    try {
		players = datastore.get(playersKey);
		players.setProperty("team_id", teamId);
		players.setProperty("salaire_contrat", salaireId);
		players.setProperty("contrat", 1);
		players.setProperty("years_1", "A");
		players.setProperty("years_2", "A");
		players.setProperty("years_3", "A");
		players.setProperty("years_4", "A");
		players.setProperty("years_5", "A");
		players.setProperty("acquire_years", poolYear);

		datastore.put(txn, players);

	    } catch (EntityNotFoundException e) {

	    }

	    // table Attaquant, Defenseur ou Gardien selon le pick

	    switch (position) {
	    case "attaquant":
		clefMemCache = KeyFactory.createKey("Attaquant", datastoreID);
		mBeanAttaquant = new Attaquant();		
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

	    Entity entity = null;
	    try {

		entity = datastore.get(entityKey);
		players_id = (List<Integer>) entity.getProperty("players_id");
		nom2 = (List<String>) entity.getProperty("nom");
		teamOfPlayer = (List<String>) entity.getProperty("teamOfPlayer");
		pj = (List<Integer>) entity.getProperty("pj");
		but_victoire = (List<Integer>) entity.getProperty("but_victoire");
		aide_overtime = (List<Integer>) entity.getProperty("aide_overtime");
		if (position.equalsIgnoreCase("Gardien")) {
		    blanchissage = (List<Integer>) entity.getProperty("blanchissage");
		}
		pts = (List<Integer>) entity.getProperty("pts");
		salaire_contrat = (List<Integer>) entity.getProperty("salaire_contrat");
		years_1 = (List<String>) entity.getProperty("years_1");
		years_2 = (List<String>) entity.getProperty("years_2");
		years_3 = (List<String>) entity.getProperty("years_3");
		years_4 = (List<String>) entity.getProperty("years_4");
		years_5 = (List<String>) entity.getProperty("years_5");

		if (players_id == null) {
		    players_id = new ArrayList<Integer>();
		    nom2 = new ArrayList<String>();
		    teamOfPlayer = new ArrayList<String>();
		    pj = new ArrayList<Integer>();
		    but_victoire = new ArrayList<Integer>();
		    aide_overtime = new ArrayList<Integer>();
		    if (position.equalsIgnoreCase("Gardien")) {
			blanchissage = new ArrayList<Integer>();
		    }
		    pts = new ArrayList<Integer>();
		    salaire_contrat = new ArrayList<Integer>();
		    years_1 = new ArrayList<String>();
		    years_2 = new ArrayList<String>();
		    years_3 = new ArrayList<String>();
		    years_4 = new ArrayList<String>();
		    years_5 = new ArrayList<String>();

		}
		players_id.add(playersId);
		entity.setProperty("players_id", players_id);

		nom2.add(nom);
		entity.setProperty("nom", nom2);

		teamOfPlayer.add(team);
		entity.setProperty("teamOfPlayer", teamOfPlayer);

		Long longPj = (Long) players.getProperty("pj");
		int pjId = longPj.intValue();
		pj.add(pjId);
		entity.setProperty("pj", pj);

		Long longBut_victoire = (Long) players.getProperty("but_victoire");
		int but_victoireId = longBut_victoire.intValue();
		but_victoire.add(but_victoireId);
		entity.setProperty("but_victoire", but_victoire);

		Long longAide_overtime = (Long) players.getProperty("aide_overtime");
		int aide_overtimeId = longAide_overtime.intValue();
		aide_overtime.add(aide_overtimeId);
		entity.setProperty("aide_overtime", aide_overtime);

		if (position.equalsIgnoreCase("Gardien")) {
		    Long longBlanchissage = (Long) players.getProperty("blanchissage");
		    int blanchissageId = longBlanchissage.intValue();
		    blanchissage.add(blanchissageId);
		    entity.setProperty("blanchissage", blanchissage);
		}

		Long longPts = (Long) players.getProperty("pts");
		int ptsId = longPts.intValue();
		pts.add(ptsId);
		entity.setProperty("pts", pts);

		salaire_contrat.add(salaireId);
		entity.setProperty("salaire_contrat", salaire_contrat);

		years_1.add("A");
		entity.setProperty("years_1", years_1);

		years_2.add("A");
		entity.setProperty("years_2", years_2);

		years_3.add("A");
		entity.setProperty("years_3", years_3);

		years_4.add("A");
		entity.setProperty("years_4", years_4);

		years_5.add("A");
		entity.setProperty("years_5", years_5);

		switch(position){
		case "attaquant":
		    mBeanAttaquant = mapAttaquant(entity,mBeanAttaquant);
			memcache.put(clefMemCache, mBeanAttaquant);
			break;
		    case "defenseur":
			mBeanDefenseur = mapDefenseur(entity,mBeanDefenseur);
			memcache.put(clefMemCache, mBeanDefenseur);
			break;
		    case "gardien":
			mBeanGardien = mapGardien(entity,mBeanGardien);
			memcache.put(clefMemCache, mBeanGardien);
			break;
		}
		

		datastore.put(txn, entity);

	    } catch (EntityNotFoundException e) {

	    }

	    // Table Equipe
	    Key equipeKey = KeyFactory.createKey("Equipe", datastoreID);
	    Entity equipeEntity = null;

	    try {
		equipeEntity = datastore.get(equipeKey);

		Long temp_budget_restant = (Long) equipeEntity.getProperty("budget_restant");
		int budget_restant = temp_budget_restant.intValue();

		Long temp_manquant_att = (Long) equipeEntity.getProperty("manquant_att");
		int manquant_att = temp_manquant_att.intValue();

		Long temp_manquant_def = (Long) equipeEntity.getProperty("manquant_def");
		int manquant_def = temp_manquant_def.intValue();

		Long temp_manquant_equipe = (Long) equipeEntity.getProperty("manquant_equipe");
		int manquant_equipe = temp_manquant_equipe.intValue();

		Long temp_manquant_gardien = (Long) equipeEntity.getProperty("manquant_gardien");
		int manquant_gardien = temp_manquant_gardien.intValue();

		// Long temp_manquant_recrue = (Long)equipeEntity.getProperty("manquant_recrue");
		// int manquant_recrue = temp_manquant_recrue.intValue();

		Long temp_moy_sal_restant_draft = (Long) equipeEntity.getProperty("moy_sal_restant_draft");
		int moy_sal_restant_draft = temp_moy_sal_restant_draft.intValue();

		Long temp_nb_attaquant = (Long) equipeEntity.getProperty("nb_attaquant");
		int nb_attaquant = temp_nb_attaquant.intValue();

		Long temp_nb_defenseur = (Long) equipeEntity.getProperty("nb_defenseur");
		int nb_defenseur = temp_nb_defenseur.intValue();

		Long temp_nb_equipe = (Long) equipeEntity.getProperty("nb_equipe");
		int nb_equipe = temp_nb_equipe.intValue();

		Long temp_nb_gardien = (Long) equipeEntity.getProperty("nb_gardien");
		int nb_gardien = temp_nb_gardien.intValue();

		// Long temp_nb_rookie = (Long)equipeEntity.getProperty("nb_rookie");
		// int nb_rookie = temp_nb_rookie.intValue();

		Long temp_total_salaire_now = (Long) equipeEntity.getProperty("total_salaire_now");
		int total_salaire_now = temp_total_salaire_now.intValue();

		budget_restant = budget_restant - salaireId;
		manquant_equipe = manquant_equipe - 1;
		nb_equipe = nb_equipe + 1;
		switch (position) {
		case "attaquant":
		    manquant_att = manquant_att - 1;
		    nb_attaquant = nb_attaquant + 1;
		    equipeEntity.setProperty("manquant_att", manquant_att);
		    equipeEntity.setProperty("nb_attaquant", nb_attaquant);
		    break;
		case "defenseur":
		    manquant_def = manquant_def - 1;
		    nb_defenseur = nb_defenseur + 1;
		    equipeEntity.setProperty("manquant_def", manquant_def);
		    equipeEntity.setProperty("nb_defenseur", nb_defenseur);
		    break;
		case "gardien":
		    manquant_gardien = manquant_gardien - 1;
		    nb_gardien = nb_gardien + 1;
		    equipeEntity.setProperty("manquant_gardien", manquant_gardien);
		    equipeEntity.setProperty("nb_gardien", nb_gardien);
		    break;
		}
		total_salaire_now = total_salaire_now + salaireId;
		moy_sal_restant_draft = budget_restant / manquant_equipe;

		equipeEntity.setProperty("budget_restant", budget_restant);
		equipeEntity.setProperty("manquant_equipe", manquant_equipe);
		equipeEntity.setProperty("nb_equipe", nb_equipe);
		equipeEntity.setProperty("total_salaire_now", total_salaire_now);
		equipeEntity.setProperty("moy_sal_restant_draft", moy_sal_restant_draft);

		datastore.put(txn, equipeEntity);

		clefMemCache = KeyFactory.createKey("Equipe", datastoreID);
		Equipe mBeanEquipe = new Equipe();
		mBeanEquipe = mapEquipe(equipeEntity,mBeanEquipe);
		memcache.put(clefMemCache, mBeanEquipe);

	    } catch (EntityNotFoundException e) {

	    }

	    // Table Draft_round
	    Key draftRoundKey = KeyFactory.createKey("DraftRound", poolID);
	    Entity draftRoundEntity = null;

	    try {
		draftRoundEntity = datastore.get(draftRoundKey);

		List<String> player_drafted = (List<String>) draftRoundEntity.getProperty("player_drafted");
		player_drafted.set((currentPick-1), nom);
		draftRoundEntity.setProperty("player_drafted", player_drafted);

		datastore.put(txn, draftRoundEntity);

		clefMemCache = KeyFactory.createKey("DraftRound", poolId);
		DraftRound mBeanDraftRound = new DraftRound();
		mBeanDraftRound = mapDraftRound(draftRoundEntity,mBeanDraftRound);
		memcache.put(clefMemCache, mBeanDraftRound);

	    } catch (EntityNotFoundException e) {

	    }

	    // Table DraftProcess
	    Key draftProcessKey = KeyFactory.createKey("DraftProcess", poolID);
	    Entity draftProcessEntity = null;

	    try {
		draftProcessEntity = datastore.get(draftProcessKey);

		currentPick = currentPick + 1;
		draftProcessEntity.setProperty("currentPick", currentPick);

		datastore.put(txn, draftProcessEntity);

	    } catch (EntityNotFoundException e) {

	    }

	    // commit
	    txn.commit();
	} finally {
	    if (txn.isActive()) {
		txn.rollback();
		// persistenceDraftPickRegulier();

	    }
	}

    }

    /* ******************************************* Methode privé ************************************************ */

    private DraftRound mapDraftRound(Entity entity, DraftRound mBean) {
	EntityManagerFactory emf = EMF.get();
	EntityManager em = null;

	try {
	    em = emf.createEntityManager();
	    mBean = em.find(DraftRound.class, entity.getKey());
	} finally {
	    if (em != null)
		em.close();
	}

	return mBean;
    }
    
    private Equipe mapEquipe(Entity entity, Equipe mBean) {
	EntityManagerFactory emf = EMF.get();
	EntityManager em = null;

	try {
	    em = emf.createEntityManager();
	    mBean = em.find(Equipe.class, entity.getKey());
	} finally {
	    if (em != null)
		em.close();
	}

	return mBean;
    }
    
    private Attaquant mapAttaquant(Entity entity, Attaquant mBean) {
   	EntityManagerFactory emf = EMF.get();
   	EntityManager em = null;

   	try {
   	    em = emf.createEntityManager();
   	    mBean = em.find(Attaquant.class, entity.getKey());
   	} finally {
   	    if (em != null)
   		em.close();
   	}

   	return mBean;
       }
    
    private Defenseur mapDefenseur(Entity entity, Defenseur mBean) {
   	EntityManagerFactory emf = EMF.get();
   	EntityManager em = null;

   	try {
   	    em = emf.createEntityManager();
   	    mBean = em.find(Defenseur.class, entity.getKey());
   	} finally {
   	    if (em != null)
   		em.close();
   	}

   	return mBean;
       }
    
    private Gardien mapGardien(Entity entity, Gardien mBean) {
   	EntityManagerFactory emf = EMF.get();
   	EntityManager em = null;

   	try {
   	    em = emf.createEntityManager();
   	    mBean = em.find(Gardien.class, entity.getKey());
   	} finally {
   	    if (em != null)
   		em.close();
   	}

   	return mBean;
       }

    private Entity getEntityEquipe(String poolID, String teamID) {
	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	String datastoreNameEquipeTable = poolID + "_" + teamID;
	Key mKey = KeyFactory.createKey("Equipe", datastoreNameEquipeTable);
	try {
	    Entity mEntity = datastore.get(mKey);
	    return mEntity;
	} catch (EntityNotFoundException e) {
	    return null;
	}
    }

    private boolean checkIfPlayersStillAvailable(String playersID, String datastoreName) {

	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	int playersId = Integer.parseInt(playersID);
	Filter selectPlayerById = new FilterPredicate("players_id", FilterOperator.EQUAL, playersId);
	// Use class Query to assemble a query
	Query q = new Query(datastoreName).setFilter(selectPlayerById);

	// Use PreparedQuery interface to retrieve results
	List<Entity> pq = datastore.prepare(q).asList(FetchOptions.Builder.withDefaults());

	if (pq.isEmpty()) {
	    return false;
	} else {
	    return true;
	}

    }

    private boolean checkIfCashStillAvailable(Entity mEntity, String salaire_draft) {

	int salaireDraft = Integer.parseInt(salaire_draft);
	int budgetRestant = 0;
	int manquantEquipe = 0;

	Long budget_restant = (Long) mEntity.getProperty("budget_restant");
	budgetRestant = budget_restant.intValue();
	Long manquant_equipe = (Long) mEntity.getProperty("manquant_equipe");
	manquantEquipe = manquant_equipe.intValue();

	if (budgetRestant < salaireDraft || ((budgetRestant - salaireDraft) / (manquantEquipe - 1) <= 1000000)) {
	    return false;
	} else {
	    return true;
	}
    }

    private String checkIfPositionWillBeOk(Entity mEntity, String position) {

	int manquantAttaquant = 0;
	int manquantDefenseur = 0;
	int manquantGardien = 0;
	int manquantEquipe = 0;

	Long manquant_att = (Long) mEntity.getProperty("manquant_att");
	manquantAttaquant = manquant_att.intValue();
	Long manquant_def = (Long) mEntity.getProperty("manquant_def");
	manquantDefenseur = manquant_def.intValue();
	Long manquant_gardien = (Long) mEntity.getProperty("manquant_gardien");
	manquantGardien = manquant_gardien.intValue();
	Long manquant_equipe = (Long) mEntity.getProperty("manquant_equipe");
	manquantEquipe = manquant_equipe.intValue();

	switch (position) {
	case "attaquant":
	    if ((manquantEquipe - 1) < manquantDefenseur || (manquantEquipe - 1) < manquantGardien) {
		return position;
	    } else {
		return null;
	    }

	case "defenseur":
	    if ((manquantEquipe - 1) < manquantAttaquant || (manquantEquipe - 1) < manquantGardien) {
		return position;
	    } else {
		return null;
	    }
	case "gardien":
	    if ((manquantEquipe - 1) < manquantDefenseur || (manquantEquipe - 1) < manquantAttaquant) {
		return position;
	    } else {
		return null;
	    }
	}
	return null;

    }

    private Boolean checkIfWillMissRookie(Entity mEntity) {
	Long manquant_equipe = (Long) mEntity.getProperty("manquant_equipe");
	int manquantEquipe = manquant_equipe.intValue();
	Long manquant_recrue = (Long) mEntity.getProperty("manquant_recrue");
	int manquantRecrue = manquant_recrue.intValue();

	if ((manquantEquipe - 1) < manquantRecrue) {
	    return true;
	} else {
	    return false;
	}

    }

}
