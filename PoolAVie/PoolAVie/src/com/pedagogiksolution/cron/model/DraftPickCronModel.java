package com.pedagogiksolution.cron.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.pedagogiksolution.dao.DraftPickDao;
import com.pedagogiksolution.datastorebeans.DraftPick;
import com.pedagogiksolution.datastorebeans.Pool;
import com.pedagogiksolution.utils.EMF;

public class DraftPickCronModel {

    private DraftPickDao draftPickDao;

    public DraftPickCronModel(DraftPickDao draftPickDao) {
	this.draftPickDao = draftPickDao;
    }

    public void putDatabaseInDatastore(int poolId, int numberOfTeam) {

	DraftPick mBeanDraftPick = draftPickDao.cronJobGetDraftPickbyPoolId(poolId,numberOfTeam);
	
	EntityManagerFactory emf = EMF.get();
	EntityManager em = null;
	try {
	    em = emf.createEntityManager();
	    // on persiste dans le datastore via notre EntityManager
	    em.persist(mBeanDraftPick);

	} finally {
	    // on ferme le manager pour libérer la mémoire
	    if (em != null)
		em.close();
	}

	MemcacheService memcache = MemcacheServiceFactory.getMemcacheService();
	Key clefMemCachePool = KeyFactory.createKey("DraftPick", poolId);
	memcache.put(clefMemCachePool, mBeanDraftPick);
	
	

    }

    public int getNumberOfPool() {

	int poolId = 0;
	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

	Filter firstConnexionFinish = new FilterPredicate("firstConnexionFinish", FilterOperator.EQUAL, 1);
	// Use class Query to assemble a query
	Query q = new Query("Utilisateur").setFilter(firstConnexionFinish).addSort("poolId", SortDirection.DESCENDING);

	// Use PreparedQuery interface to retrieve results
	List<Entity> pq = datastore.prepare(q).asList(FetchOptions.Builder.withLimit(1));

	for (Entity result : pq) {
	    Long poolIdTemp = (Long) result.getProperty("poolId");
	    poolId = poolIdTemp.intValue();

	}
	return poolId;

    }

    public void setDerniereMAJ(int i) {
	
	String poolID = String.valueOf(i);

	MemcacheService memcache = MemcacheServiceFactory.getMemcacheService();
	Key clefMemCache = KeyFactory.createKey("Pool", i);
	Pool mBean = (Pool) memcache.get(clefMemCache);

	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	Date date = new Date();
	String derniereMAJ = dateFormat.format(date);

	if(mBean!=null){
	mBean.setDerniereMAJ(derniereMAJ);
	memcache.put(clefMemCache, mBean);
	}
	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	Key clefDatastore = KeyFactory.createKey("Pool", poolID);
	Entity mEntity;
	try {
	    mEntity = datastore.get(clefDatastore);
	    mEntity.setProperty("derniereMAJ", derniereMAJ);
	    datastore.put(mEntity);
	} catch (EntityNotFoundException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

    }

    public int getNumberOfTeamByPool(int i) {
	
	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	Key clefDatastore = KeyFactory.createKey("Pool", String.valueOf(i));	
	Entity mEntity;
	try {
	    mEntity = datastore.get(clefDatastore);
	    Long numberTeam = (Long) mEntity.getProperty("numberTeam");
	    int mTeamNumber = numberTeam.intValue(); 
	    return mTeamNumber;
	} catch (EntityNotFoundException e) {
	    // TODO Auto-generated catch block
	   
	    e.printStackTrace();
	    return 0;
	}
	
	
	

	
    }

}
