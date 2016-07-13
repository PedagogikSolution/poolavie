package com.pedagogiksolution.model;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilter;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.pedagogiksolution.beans.NonSessionPlayers;
import com.pedagogiksolution.datastorebeans.Pool;

public class DraftPlayersModel {

    String segment, sort;
    HttpServletRequest req;
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    public DraftPlayersModel(HttpServletRequest req, String segment, String sort) {
	this.segment = segment;
	this.sort = sort;
	this.req = req;
    }

    public void showPlayersSortByParameter() {
	NonSessionPlayers mBean = new NonSessionPlayers();
	mBean = getDraftPlayersFromDatastore(sort, segment, mBean);
	req.setAttribute("NonSessionPlayers", mBean);

    }

    private NonSessionPlayers getDraftPlayersFromDatastore(String sort2, String segment2, NonSessionPlayers mBean) {

	List<Integer> players_id = new ArrayList<Integer>();
	List<String> nom = new ArrayList<String>();
	List<String> teamOfPlayer = new ArrayList<String>();
	List<Integer> pj = new ArrayList<Integer>();
	List<Integer> but_victoire = new ArrayList<Integer>();
	List<Integer> aide_overtime = new ArrayList<Integer>();
	List<Integer> blanchissage = new ArrayList<Integer>();
	List<Integer> pts = new ArrayList<Integer>();
	List<Integer> projection = new ArrayList<Integer>();
	List<String> position = new ArrayList<String>();
	List<Integer> can_be_rookie = new ArrayList<Integer>();
	List<Integer> salaire_draft = new ArrayList<Integer>();

	Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");
	String poolID = mBeanPool.getPoolID();

	String datastoreID = "Players_" + poolID;
	Query q = null;
	Filter noContrat;
	Filter byPosition;
	CompositeFilter mFiltre;
	switch (segment2) {
	case "all":
	    Filter allPlayers = new FilterPredicate("contrat", FilterOperator.EQUAL, 0);
	    q = new Query(datastoreID).addSort(sort2, Query.SortDirection.DESCENDING).setFilter(allPlayers);
	    break;
	case "foward":
	    noContrat = new FilterPredicate("contrat", FilterOperator.EQUAL, 0);
	    byPosition = new FilterPredicate("position", FilterOperator.EQUAL, "attaquant");
	    mFiltre = CompositeFilterOperator.and(noContrat, byPosition);
	    q = new Query(datastoreID).addSort(sort2, Query.SortDirection.DESCENDING).setFilter(mFiltre);
	    break;
	case "defense":
	    noContrat = new FilterPredicate("contrat", FilterOperator.EQUAL, 0);
	    byPosition = new FilterPredicate("position", FilterOperator.EQUAL, "defense");
	    mFiltre = CompositeFilterOperator.and(noContrat, byPosition);
	    q = new Query(datastoreID).addSort(sort2, Query.SortDirection.DESCENDING).setFilter(mFiltre);
	    break;
	case "goaler":
	    noContrat = new FilterPredicate("contrat", FilterOperator.EQUAL, 0);
	    byPosition = new FilterPredicate("position", FilterOperator.EQUAL, "gardien");
	    mFiltre = CompositeFilterOperator.and(noContrat, byPosition);
	    q = new Query(datastoreID).addSort(sort2, Query.SortDirection.DESCENDING).setFilter(mFiltre);
	    break;
	case "rookie":
	    noContrat = new FilterPredicate("contrat", FilterOperator.EQUAL, 0);
	    byPosition = new FilterPredicate("can_be_rookie", FilterOperator.EQUAL,1);
	    mFiltre = CompositeFilterOperator.and(noContrat, byPosition);
	    q = new Query(datastoreID).addSort(sort2, Query.SortDirection.DESCENDING).setFilter(mFiltre);
	    break;

	}

	// Use PreparedQuery interface to retrieve results
	List<Entity> pq = datastore.prepare(q).asList(FetchOptions.Builder.withDefaults());

	for (Entity result : pq) {

	    int m_players_id = (int) result.getProperty("players_id");
	    players_id.add(m_players_id);
	    mBean.set_id(players_id);

	    String m_nom = (String) result.getProperty("nom");
	    nom.add(m_nom);
	    mBean.setNom(nom);

	    String m_teamOfPlayer = (String) result.getProperty("teamOfPlayer");
	    teamOfPlayer.add(m_teamOfPlayer);
	    mBean.setTeamOfPlayer(teamOfPlayer);

	    int m_pj = (int) result.getProperty("pj");
	    pj.add(m_pj);
	    mBean.setPj(pj);

	    int m_but_victoire = (int) result.getProperty("but_victoire");
	    but_victoire.add(m_but_victoire);
	    mBean.setBut_victoire(but_victoire);

	    int m_aide_overtime = (int) result.getProperty("aide_overtime");
	    aide_overtime.add(m_aide_overtime);
	    mBean.setAide_overtime(aide_overtime);

	    int m_blanchissage = (int) result.getProperty("blanchissage");
	    blanchissage.add(m_blanchissage);
	    mBean.setBlanchissage(blanchissage);

	    int m_pts = (int) result.getProperty("pts");
	    pts.add(m_pts);
	    mBean.setPts(pts);

	    int m_projection = (int) result.getProperty("projection");
	    projection.add(m_projection);
	    mBean.setProjection(projection);

	    String m_position = (String) result.getProperty("position");
	    position.add(m_position);
	    mBean.setPosition(position);

	    int m_can_be_rookie = (int) result.getProperty("can_be_rookie");
	    can_be_rookie.add(m_can_be_rookie);
	    mBean.setCan_be_rookie(can_be_rookie);

	    int m_salaire_draft = (int) result.getProperty("salaire_draft");
	    salaire_draft.add(m_salaire_draft);
	    mBean.setSalaire_draft(salaire_draft);
	}

	return mBean;

    }
}
