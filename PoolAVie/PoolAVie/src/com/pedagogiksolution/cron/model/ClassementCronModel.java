package com.pedagogiksolution.cron.model;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.pedagogiksolution.dao.ClassementDao;
import com.pedagogiksolution.datastorebeans.Classement;
import com.pedagogiksolution.utils.EMF;

public class ClassementCronModel {

    private ClassementDao classementDao;

    public ClassementCronModel(ClassementDao classementDao) {
	this.classementDao = classementDao;
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

	Filter firstConnexionFinish  = new FilterPredicate("firstConnexionFinish", FilterOperator.EQUAL, 1);
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

}
