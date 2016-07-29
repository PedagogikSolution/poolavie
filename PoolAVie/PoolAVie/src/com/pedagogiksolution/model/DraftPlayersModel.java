package com.pedagogiksolution.model;

import java.util.ArrayList;
import java.util.List;

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
import com.pedagogiksolution.beans.NonSessionPlayers;
import com.pedagogiksolution.datastorebeans.DraftProcess;
import com.pedagogiksolution.datastorebeans.Pool;

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
	if(menu!=null){
	   fromMenu=true; 
	}

	switch (segment2) {
	case "all":

	    Filter allPlayers = new FilterPredicate("contrat", FilterOperator.EQUAL, 0);

	    if (ascDescOrder == 0||fromMenu) {
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
	    if (ascDescOrder == 0||fromMenu) {
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
	    if (ascDescOrder == 0||fromMenu) {
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
	    if (ascDescOrder == 0||fromMenu) {
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
	    if (ascDescOrder == 0||fromMenu) {
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
	Key mKey = KeyFactory.createKey("DraftProcess",poolID);
	
	Entity mEntity = new Entity(mKey);
	mEntity.setProperty("currentPick", 1);
	datastore.put(mEntity);
    }

    public Boolean checkIfDatastoreCreate(Pool mBean) {
	
	String poolID = mBean.getPoolID();
	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	Key mKey = KeyFactory.createKey("DraftProcess",poolID);
	
	try {
	    datastore.get(mKey);
	    return true;
	} catch (EntityNotFoundException e) {
	   return false;
	}
    }

    public void putDatastoreIntoBean(Pool mBean, HttpServletRequest req2) {
	
	int currentPick2=0;
	DraftProcess mBeanDraft = new DraftProcess();
	String poolID = mBean.getPoolID();
	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	Key mKey = KeyFactory.createKey("DraftProcess",poolID);
	
	try {
	    Entity mEntity = datastore.get(mKey);
	    Long currentPick =  (Long) mEntity.getProperty("currentPick");
	    currentPick2 =  currentPick.intValue();
	    
	    
	    mBeanDraft.setCurrentPick(currentPick2);
	    
	    
	} catch (EntityNotFoundException e) {
	   
	}
	
	
	DatastoreService datastore2 = DatastoreServiceFactory.getDatastoreService();
	Key mKey2 = KeyFactory.createKey("DraftRound",poolID);
	
	try {
	    Entity mEntity = datastore2.get(mKey2);

	    @SuppressWarnings("unchecked")
	    List<Long> currentPickerArray =  (List<Long>) mEntity.getProperty("team_id");
	    Long currentPicker =  (Long) currentPickerArray.get(currentPick2-1);
	    int currentPicker2 = currentPicker.intValue();
	    mBeanDraft.setCurrentPicker(currentPicker2);
	    
	    
	} catch (EntityNotFoundException e) {
	   
	}
	
	req2.setAttribute("DraftBean", mBeanDraft);
	
    }
}
