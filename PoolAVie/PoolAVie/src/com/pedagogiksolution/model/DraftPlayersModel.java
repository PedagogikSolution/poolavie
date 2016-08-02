package com.pedagogiksolution.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpServletRequest;

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
import com.pedagogiksolution.beans.MessageErreurBeans;
import com.pedagogiksolution.beans.NonSessionPlayers;
import com.pedagogiksolution.datastorebeans.Attaquant;
import com.pedagogiksolution.datastorebeans.DraftProcess;
import com.pedagogiksolution.datastorebeans.Players;
import com.pedagogiksolution.datastorebeans.Pool;
import com.pedagogiksolution.utils.EMF;

public class DraftPlayersModel {
    int ascDescOrder;
    String segment, sort;
    HttpServletRequest req;
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    public DraftPlayersModel(HttpServletRequest req, String segment, String sort) {
	this.segment = segment;
	this.sort = sort;
	this.req = req;
    }

    public DraftPlayersModel() {
	// TODO Auto-generated constructor stub
    }

    public DraftPlayersModel(HttpServletRequest req2) {
	this.req = req2;
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

    public void persistenceDraftPickRegulier() {
	// on recupere tous les variables utiles
	EntityManagerFactory emf = EMF.get();
	EntityManager em = null;
	Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");
	String poolID = mBeanPool.getPoolID();
	int poolYear = mBeanPool.getPoolYear();
	int poolId = Integer.parseInt(poolID);
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
	    List<Integer> team_id = new ArrayList<Integer>();
	    List<String> nom2 = new ArrayList<String>();
	    List<String> teamOfPlayer = new ArrayList<String>();
	    List<Integer> pj = new ArrayList<Integer>();
	    List<Integer> but_victoire = new ArrayList<Integer>();
	    List<Integer> aide_overtime = new ArrayList<Integer>();
	    List<Integer> blanchissage = new ArrayList<Integer>();
	    List<Integer> pts = new ArrayList<Integer>();
	    List<Integer> projection = new ArrayList<Integer>();
	    List<String> position2 = new ArrayList<String>();
	    List<Date> birthday = new ArrayList<Date>();
	    List<Integer> can_be_rookie = new ArrayList<Integer>();
	    List<Integer> take_proj = new ArrayList<Integer>();
	    List<Integer> salaire_draft = new ArrayList<Integer>();
	    List<Integer> contrat = new ArrayList<Integer>();
	    List<Integer> acquire_years = new ArrayList<Integer>();
	    List<Integer> salaire_contrat = new ArrayList<Integer>();
	    List<Integer> contrat_cours = new ArrayList<Integer>();
	    List<Integer> contrat_max_years = new ArrayList<Integer>();
	    List<Integer> type_contrat = new ArrayList<Integer>();
	    List<Integer> club_ecole = new ArrayList<Integer>();
	    List<Date> date_calcul = new ArrayList<Date>();
	    List<String> years_1 = new ArrayList<String>();
	    List<String> years_2 = new ArrayList<String>();
	    List<String> years_3 = new ArrayList<String>();
	    List<String> years_4 = new ArrayList<String>();
	    List<String> years_5 = new ArrayList<String>();
	    List<Integer> team_was_update = new ArrayList<Integer>();
	    List<Integer> age = new ArrayList<Integer>();
	    List<Integer> hier = new ArrayList<Integer>();
	    List<Integer> semaine = new ArrayList<Integer>();
	    List<Integer> mois = new ArrayList<Integer>();
	try {
	    Entity mEntity = datastore.get(mKey);
	    Long currentPick2 = (Long) mEntity.getProperty("currentPick");
	    currentPick = currentPick2.intValue();

	} catch (EntityNotFoundException e) {

	}
	String playersEntityName = "Players_" + poolID;
	String attaquantDatastoreID = poolID + "_" + teamID;
	// on commence une transaction pour mettre a jour les datastores Players, Attaquant, Defenseur, Gardien, Equipe,
// draftRound, DraftPick,DraftProcess
	TransactionOptions options = TransactionOptions.Builder.withXG(true);
	Transaction txn = datastore.beginTransaction();
	try {

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
	    
	    
	    Key attaquantKey = KeyFactory.createKey("Attaquant", attaquantDatastoreID);
	    Attaquant mBeanAttaquant = new Attaquant();
	    Entity attaquant = null;
	    try {
	        em = emf.createEntityManager();
	        mBeanAttaquant = em.find(Attaquant.class, attaquantKey);
	        
	        players_id.addAll(mBeanAttaquant.get_id());
	        players_id.add((Integer) players.getProperty("player_id"));
	        mBeanAttaquant.set_id(players_id);
	        
	        team_id.addAll(mBeanAttaquant.getTeam_id());
	        team_id.add((Integer) players.getProperty("team_id"));
	        mBeanAttaquant.setTeam_id(team_id);
	        
	        
	        
	        em.merge(mBeanAttaquant);
	        
	        try {
		    attaquant = datastore.get(attaquantKey);
		} catch (EntityNotFoundException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
	        
	        datastore.put(txn, attaquant);
	        
	    } finally {
	        if (em != null)
	    	em.close();
	    }

	    
	    
	    txn.commit();
	} finally {
	    if (txn.isActive()) {
		txn.rollback();
	    }
	}

    }

    /* ******************************************* Methode privé ************************************************ */

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
