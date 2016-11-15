package com.pedagogiksolution.cron.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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

	for (int teamId = 1; teamId < (numberTeam + 1); teamId++) {
	    int pj = playersDao.getPj(teamId, poolId);
	    int but = playersDao.getBut(teamId, poolId);
	    int passe = playersDao.getPasse(teamId, poolId);
	    int pts = playersDao.getPts(teamId, poolId);
	    HashMap<String, Integer> progressionHashMap = putTodayInDatastoreAndGetProgression(teamId, poolId, pts);
	    
	    int hier = progressionHashMap.get("hier");
	    int semaine = progressionHashMap.get("semaine");
	    int mois = progressionHashMap.get("mois");

	    classementDao.updateStat(poolId, pj, but, passe, pts, teamId,hier,semaine,mois);

	}
	
	List<Long> mouvementArray = getMouvementArray(poolId);

	for (int teamId = 1; teamId < (numberTeam + 1); teamId++) {
	    classementDao.updateDifference(poolId, teamId,mouvementArray);
	}

    }

    @SuppressWarnings("unchecked")
    private List<Long> getMouvementArray(int poolId) {
	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    
	    Key mKeyClassement = KeyFactory.createKey("Classement", String.valueOf(poolId));
	    
	    try {
		Entity entityClassement = datastore.get(mKeyClassement);
		List<Long> mouvementArray = new ArrayList<Long>();
		mouvementArray = (List<Long>) entityClassement.getProperty("mouvement");
		
		return mouvementArray;
	    } catch (EntityNotFoundException e) {
		return null;
	    }
		
    }

    private HashMap<String, Integer> putTodayInDatastoreAndGetProgression(int teamId, int poolId, int pts) {

	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	String datastoreID = poolId + "_" + teamId;
	Key mKey = KeyFactory.createKey("Classement_Progression", datastoreID);
	HashMap<String, Integer> hmap = new HashMap<String, Integer>();
	try {
	    Entity entity = datastore.get(mKey);
	    Long compteur = (Long) entity.getProperty("compteur");
	    Long compteurAjuster = compteur + 1;
	    String nomNextCompteur = String.valueOf(compteurAjuster);
	    entity.setProperty(nomNextCompteur, pts);
	    
	    
	    

	    String compteurHier = compteur.toString();
	    Long ptsHier = (Long) entity.getProperty(compteurHier);
	    int ptsHierId = ptsHier.intValue();
	    int hier = pts - ptsHierId;
	    
	    hmap.put("hier", hier);

	    String compteurSemaine = String.valueOf(compteur - 6);
	    Long ptsSemaine = (Long) entity.getProperty(compteurSemaine);

	    if (ptsSemaine == null) {
		hmap.put("semaine", 0);
	    } else {
		int ptsSemaineId = ptsSemaine.intValue();
		int semaine = pts - ptsSemaineId;
		hmap.put("semaine", semaine);
	    }

	    String compteurMois = String.valueOf(compteur - 6);
	    Long ptsmois = (Long) entity.getProperty(compteurMois);

	    if (ptsmois == null) {
		hmap.put("mois", 0);
	    } else {
		int ptsmoisId = ptsmois.intValue();
		int mois = pts - ptsmoisId;
		hmap.put("mois", mois);
	    }
	    
	    entity.setProperty("compteur", compteurAjuster);
	    
	    datastore.put(entity);

	    return hmap;

	} catch (EntityNotFoundException e) {

	    Entity entity = new Entity("Classement_Progression", datastoreID);
	    entity.setProperty("1", pts);
	    
	    entity.setProperty("position", teamId);
	    datastore.put(entity);
	    
	    hmap.put("hier", 0);
	    hmap.put("semaine", 0);
	    hmap.put("mois", 0);
	    
	    return hmap;

	}
	

    }
}
