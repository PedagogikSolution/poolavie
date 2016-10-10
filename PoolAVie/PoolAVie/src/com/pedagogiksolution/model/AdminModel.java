package com.pedagogiksolution.model;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
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
	int poolYearId = mBeanPool.getPoolYear();

	DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
	DateTime dt = formatter.parseDateTime(dateDraft);

	if (dt.isBeforeNow()) {

	    mBeanPool.setCycleAnnuel(3);
	    mBeanPool.setPoolYear((poolYearId + 1));

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

    public void annulationStartDraft(HttpServletRequest req) {

	Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");

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

    public void envoieCourrielDateEtOrdreDeDraft(HttpServletRequest req, String dateDraft, String heureDraft) {

	Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");
	String poolID = mBeanPool.getPoolID();
	int poolId = Integer.parseInt(poolID);

	// on appel le service
	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

	// Use class Query to assemble a query
	Query q = new Query("Utilisateur");
	q.setFilter(new Query.FilterPredicate("poolId", Query.FilterOperator.EQUAL, poolId));

	PreparedQuery pq = datastore.prepare(q);
	List<Entity> entity = pq.asList(FetchOptions.Builder.withDefaults());

	for (Entity results : entity) {

	   // Long typeUser = (Long) results.getProperty("typeUtilisateur");

	    
		String courriel = (String) results.getProperty("courriel");
		String nomTeam = (String) results.getProperty("teamName");
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);

		try {
		    MimeMessage msg = new MimeMessage(session);
		    msg.setFrom(new InternetAddress("pedagogiksolution@gmail.com", "Poolavie.ca"));
		    msg.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(courriel));
		    msg.setSubject("Date et Ordre de Draft", "utf-8");
		    msg.setContent("Bonjour " + nomTeam + ", votre administrateur de pool à déterminer une date pour votre draft. \n\n" + "Celui-ci aura lieu le " + dateDraft + " à " + heureDraft + ". Vous pourrez alors vous connecter au serveur de draft" + " via l'onglet Draft ou en cliquant sur l'alerte en rouge lorsque vous vous connecterez"
			    + " sur le site et que vous arriverez à la page des nouvelles. \n\n Vous trouverez imédiatement aussi dans la section Draft Center l'ordre de draft des 20 premiers picks.\n\n Votre administrateur", "text/html");
		    Transport.send(msg);
		} catch (AddressException e) {
		    // ...
		} catch (MessagingException e) {
		    // ...
		} catch (UnsupportedEncodingException e) {
		    // ...
		}
	    }

	

    }

    public void resetCycleAnnuelTo2(HttpServletRequest req) {
	
	
    }
}
