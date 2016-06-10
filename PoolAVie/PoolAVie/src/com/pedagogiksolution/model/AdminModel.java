package com.pedagogiksolution.model;

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

	DraftRound mBeanDraft = draftDao.setDraftRoundOrder(poolId);
	
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

	

    }
}
