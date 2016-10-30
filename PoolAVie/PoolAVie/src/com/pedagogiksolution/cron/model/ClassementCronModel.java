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
import com.pedagogiksolution.dao.ClassementDao;
import com.pedagogiksolution.dao.PlayersDao;
import com.pedagogiksolution.datastorebeans.Classement;
import com.pedagogiksolution.utils.EMF;

public class ClassementCronModel {

    private ClassementDao classementDao;
    private PlayersDao playersDao;
    
    

    public ClassementCronModel(ClassementDao classementDao, PlayersDao playersDao) {
	this.classementDao = classementDao;
	this.playersDao = playersDao;
    }

    public void putDatabaseInDatastore(int poolId) {

	Classement mBeanClassement = classementDao.cronJobGetClassementbyPoolId(poolId);

	EntityManagerFactory emf = EMF.get();
	EntityManager em = null;
	try {
	    em = emf.createEntityManager();
	    // on persiste dans le datastore via notre EntityManager
	    em.persist(mBeanClassement);

	} finally {
	    // on ferme le manager pour libérer la mémoire
	    if (em != null)
		em.close();
	}

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

	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	Date date = new Date();
	String derniereMAJ = dateFormat.format(date);

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

    public void updateClassement(int poolId, Long numberTeam) {
	
	for(int teamId=1;teamId<(numberTeam+1);teamId++){
	    int pj = playersDao.getPj(teamId,poolId);   
	    int but = playersDao.getBut(teamId,poolId);
	    int passe = playersDao.getPasse(teamId,poolId);
	    int pts = playersDao.getPts(teamId,poolId);
	    classementDao.updateStat(poolId,teamId,pj,but,passe,pts);
	    	    
	}
	
	classementDao.updateDifference(poolId);
	
	
	
    }
}
