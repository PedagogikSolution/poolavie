package com.pedagogiksolution.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.pedagogiksolution.dao.DraftDao;
import com.pedagogiksolution.datastorebeans.DraftRound;
import com.pedagogiksolution.datastorebeans.Pool;
import com.pedagogiksolution.utils.EMF;

public class AdminModel {

    private DraftDao draftDao;

    public AdminModel() {

    }

    public AdminModel(DraftDao draftDao) {
	this.draftDao = draftDao;
    }

    public void setDraftTime(String dateDraft, String heureDraft, HttpServletRequest req) {

	// on met dans bon format de persistence, on change valeur de cycle a 2, on persist dans POOL la date et l'heure

	StringBuilder sb = new StringBuilder(dateDraft);
	sb.append(" ").append(heureDraft);

	Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");

	mBeanPool.setDraftDate(sb.toString());
	mBeanPool.setCycleAnnuel(2);

	req.getSession().setAttribute("Pool", mBeanPool);

	String poolID = mBeanPool.getPoolID();
	int poolId = Integer.parseInt(poolID);

	MemcacheService memcache = MemcacheServiceFactory.getMemcacheService();
	Key clefMemCache = KeyFactory.createKey("Pool", poolId);
	memcache.put(clefMemCache, mBeanPool);

	EntityManagerFactory emf = EMF.get();
	EntityManager em = null;

	try {
	    em = emf.createEntityManager();
	    mBeanPool = em.merge(mBeanPool);
	} finally {
	    if (em != null)
		em.close();
	}

    }

    public void checkIfDraftDay(Pool mBeanPool, HttpServletRequest req) {
	// on verifie si c'est la date du draft, si oui, on met le cycle annuel a 3
	String dateDraft = mBeanPool.getDraftDate();

	DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
	DateTime dt = formatter.parseDateTime(dateDraft);

	if (dt.isBeforeNow()) {

	    mBeanPool.setCycleAnnuel(3);

	    req.getSession().setAttribute("Pool", mBeanPool);

	    String poolID = mBeanPool.getPoolID();
	    int poolId = Integer.parseInt(poolID);

	    MemcacheService memcache = MemcacheServiceFactory.getMemcacheService();
	    Key clefMemCache = KeyFactory.createKey("Pool", poolId);
	    memcache.put(clefMemCache, mBeanPool);

	    EntityManagerFactory emf = EMF.get();
	    EntityManager em = null;

	    try {
		em = emf.createEntityManager();
		mBeanPool = em.merge(mBeanPool);
	    } finally {
		if (em != null)
		    em.close();
	    }

	}

    }

    public void determineOrderOfDraft(HttpServletRequest req) {

	Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");
	String poolID = mBeanPool.getPoolID();
	int poolId = Integer.parseInt(poolID);

	int numberTeam = mBeanPool.getNumberTeam();
	List<Integer> permutation = null;
	switch (numberTeam) {
	case 8:
	    permutation = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
	    break;
	case 9:
	    permutation = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
	    break;
	case 10:
	    permutation = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
	    break;
	case 11:
	    permutation = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11);
	    break;
	case 12:
	    permutation = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
	    break;
	}

	Collections.shuffle(permutation);

	// On persist dans Database et dans Datastore et dans MemCache et dans Bean

	draftDao.populateFirstYearsDraft(poolId, permutation, mBeanPool);

	ResultSet rs = draftDao.getDraftRoundOrder(poolId);

	List<Integer> draft_pick_no = new ArrayList<Integer>();
	List<String> equipe = new ArrayList<String>();
	List<Integer> ronde = new ArrayList<Integer>();
	List<Integer> team_id = new ArrayList<Integer>();
	List<String> from_who = new ArrayList<String>();
	List<Integer> team_id_from = new ArrayList<Integer>();
	List<Integer> team_count = new ArrayList<Integer>();
	List<Integer> follow_up = new ArrayList<Integer>();
	List<String> player_drafted = new ArrayList<String>();
	List<Integer> year_of_draft = new ArrayList<Integer>();

	try {
	    while (rs.next()) {
		int draft_pick_no2 = rs.getInt("draft_pick_no");
		draft_pick_no.add(draft_pick_no2);
		String equipe2 = rs.getString("equipe");
		equipe.add(equipe2);
		int ronde2 = rs.getInt("ronde");
		ronde.add(ronde2);
		int team_id2 = rs.getInt("team_id");
		team_id.add(team_id2);
		String from_who2 = rs.getString("from_who");
		from_who.add(from_who2);
		int team_id_from2 = rs.getInt("team_id_from");
		team_id_from.add(team_id_from2);
		int team_count2 = rs.getInt("team_count");
		team_count.add(team_count2);
		int follow_up2 = rs.getInt("follow_up");
		follow_up.add(follow_up2);
		String player_drafted2 = rs.getString("player_drafted");
		player_drafted.add(player_drafted2);
		int year_of_draft2 = rs.getInt("year_of_draft");
		year_of_draft.add(year_of_draft2);
	    }

	    DraftRound mBeanDraft = new DraftRound();
	    mBeanDraft.setPoolId(String.valueOf(poolId));
	    mBeanDraft.setDraft_pick_no(draft_pick_no);
	    mBeanDraft.setEquipe(equipe);
	    mBeanDraft.setRonde(ronde);
	    mBeanDraft.setTeam_id(team_id);
	    mBeanDraft.setFrom_who(from_who);
	    mBeanDraft.setTeam_id_from(team_id_from);
	    mBeanDraft.setTeam_count(team_count);
	    mBeanDraft.setFollow_up(follow_up);
	    mBeanDraft.setPlayer_drafted(player_drafted);
	    mBeanDraft.setYear_of_draft(year_of_draft);

	    req.getSession().setAttribute("DraftRound", mBeanDraft);

	    MemcacheService memcache = MemcacheServiceFactory.getMemcacheService();
	    Key clefMemCache = KeyFactory.createKey("DrafRound", Integer.parseInt(poolID));
	    memcache.put(clefMemCache, mBeanDraft);

	    EntityManagerFactory emf = EMF.get();
	    EntityManager em = null;
	    // on persiste dans le datastore via notre EntityManager
	    try {
		em = emf.createEntityManager();
		em.persist(mBeanDraft);
	    } finally {
		if (em != null)
		    em.close();
	    }

	} catch (SQLException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

    }
}
