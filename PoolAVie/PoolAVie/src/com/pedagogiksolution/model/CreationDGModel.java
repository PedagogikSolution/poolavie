package com.pedagogiksolution.model;

import static com.pedagogiksolution.constants.MessageErreurConstants.CREATE_NEW_USER_NO_GOOD;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpServletRequest;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.pedagogiksolution.beans.MessageErreurBeans;
import com.pedagogiksolution.dao.ClassementDao;
import com.pedagogiksolution.datastorebeans.Pool;
import com.pedagogiksolution.datastorebeans.Utilisateur;
import com.pedagogiksolution.utils.EMF;

public class CreationDGModel {

    private ClassementDao classementDao;

    public CreationDGModel(ClassementDao classementDao) {
	this.classementDao = classementDao;

    }

    public CreationDGModel() {
	// TODO Auto-generated constructor stub
    }

    public Boolean validationNewDG(String poolID, String teamID, String codeValidationPool, HttpServletRequest req) {
	// on check d'abord si poolID existe, si oui si le code concorde et finalement on verifie si ce team existe deja
// ou si il est possible.
	// si pas plante a un moment x, on place un message dans le Bean d'erreur et retourne false

	Boolean checkIfPoolIdExist = checkIfPoolIdExist(poolID, req);

	if (!checkIfPoolIdExist) {
	    return false;
	} else {

	    Boolean checkIfCodeValidate = checkIfCodeValidate(poolID, codeValidationPool, req);

	    if (!checkIfCodeValidate) {
		return false;
	    } else {
		Boolean checkIfTeamExist = checkIfTeamExist(poolID, teamID, req);

		if (!checkIfTeamExist) {
		    return false;
		} else {
		    return true;
		}

	    }

	}

    }

    private Boolean checkIfPoolIdExist(String poolID, HttpServletRequest req) {

	MemcacheService memcache = MemcacheServiceFactory.getMemcacheService();
	Key clefMemCache = KeyFactory.createKey("Pool", poolID);
	Pool mBean = (Pool) memcache.get(clefMemCache);

	if (mBean == null) {
	    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    Key clefDatastore = KeyFactory.createKey("Pool", poolID);
	    try {
		Entity mEntity = datastore.get(clefDatastore);
		if (mEntity != null) {
		    return true;
		} else {
		    MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
		    mBeanMessageErreur.setErreurCreateNewTeam(CREATE_NEW_USER_NO_GOOD);
		    req.setAttribute("MessageErreurBeans", mBeanMessageErreur);

		    return false;
		}

	    } catch (EntityNotFoundException e) {
		MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
		mBeanMessageErreur.setErreurCreateNewTeam(CREATE_NEW_USER_NO_GOOD);
		req.setAttribute("MessageErreurBeans", mBeanMessageErreur);
		return false;
	    }
	} else {
	    return true;
	}
    }

    private Boolean checkIfCodeValidate(String poolID, String codeValidationPool, HttpServletRequest req) {

	MemcacheService memcache = MemcacheServiceFactory.getMemcacheService();
	Key clefMemCache = KeyFactory.createKey("Pool", poolID);
	Pool mBean = (Pool) memcache.get(clefMemCache);

	if (mBean == null) {
	    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    Key clefDatastore = KeyFactory.createKey("Pool", poolID);
	    try {
		Entity mEntity = datastore.get(clefDatastore);
		String codeValidationPool2 = (String) mEntity.getProperty("codeValidationPool");

		if (codeValidationPool.equalsIgnoreCase(codeValidationPool2)) {
		    return true;
		} else {

		    MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
		    mBeanMessageErreur.setErreurCreateNewTeam(CREATE_NEW_USER_NO_GOOD);
		    req.setAttribute("MessageErreurBeans", mBeanMessageErreur);
		    return false;
		}

	    } catch (EntityNotFoundException e) {
		MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
		mBeanMessageErreur.setErreurCreateNewTeam(CREATE_NEW_USER_NO_GOOD);
		req.setAttribute("MessageErreurBeans", mBeanMessageErreur);
		return false;
	    }
	} else {

	    if (codeValidationPool.equalsIgnoreCase(mBean.getCodeValidationPool())) {
		return true;
	    } else {

		MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
		mBeanMessageErreur.setErreurCreateNewTeam(CREATE_NEW_USER_NO_GOOD);
		req.setAttribute("MessageErreurBeans", mBeanMessageErreur);
		return false;
	    }

	}

    }

    private Boolean checkIfTeamExist(String poolID, String teamID, HttpServletRequest req) {

	int teamId = Integer.parseInt(teamID);

	MemcacheService memcache = MemcacheServiceFactory.getMemcacheService();
	Key clefMemCache = KeyFactory.createKey("Pool", poolID);
	Pool mBean = (Pool) memcache.get(clefMemCache);

	if (mBean == null) {
	    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    Key clefDatastore = KeyFactory.createKey("Pool", poolID);
	    try {
		Entity mEntity = datastore.get(clefDatastore);
		long numberTeam = (long) mEntity.getProperty("numberTeam");

		String teamName = null;
		if (numberTeam >= teamId) {
		    switch (teamId) {
		    case 1:
			teamName = (String) mEntity.getProperty("nomTeam1");
			break;
		    case 2:
			teamName = (String) mEntity.getProperty("nomTeam2");
			break;
		    case 3:
			teamName = (String) mEntity.getProperty("nomTeam3");
			break;
		    case 4:
			teamName = (String) mEntity.getProperty("nomTeam4");
			break;
		    case 5:
			teamName = (String) mEntity.getProperty("nomTeam5");
			break;
		    case 6:
			teamName = (String) mEntity.getProperty("nomTeam6");
			break;
		    case 7:
			teamName = (String) mEntity.getProperty("nomTeam7");
			break;
		    case 8:
			teamName = (String) mEntity.getProperty("nomTeam8");
			break;
		    case 9:
			teamName = (String) mEntity.getProperty("nomTeam9");
			break;
		    case 10:
			teamName = (String) mEntity.getProperty("nomTeam10");
			break;
		    case 11:
			teamName = (String) mEntity.getProperty("nomTeam11");
			break;
		    case 12:
			teamName = (String) mEntity.getProperty("nomTeam12");
			break;
		    }

		    if (teamName == null || teamName == "") {
			return true;

		    } else {
			MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
			mBeanMessageErreur.setErreurCreateNewTeam(CREATE_NEW_USER_NO_GOOD);
			req.setAttribute("MessageErreurBeans", mBeanMessageErreur);
			return false;
		    }

		} else {

		    MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
		    mBeanMessageErreur.setErreurCreateNewTeam(CREATE_NEW_USER_NO_GOOD);
		    req.setAttribute("MessageErreurBeans", mBeanMessageErreur);
		    return false;
		}

	    } catch (EntityNotFoundException e) {
		MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
		mBeanMessageErreur.setErreurCreateNewTeam(CREATE_NEW_USER_NO_GOOD);
		req.setAttribute("MessageErreurBeans", mBeanMessageErreur);
		return false;
	    }
	} else {
	    int numberTeam = mBean.getNumberTeam();
	    String teamName = null;
	    if (numberTeam >= teamId) {
		switch (teamId) {
		case 1:
		    teamName = mBean.getNomTeam1();
		    break;
		case 2:
		    teamName = mBean.getNomTeam2();
		    break;
		case 3:
		    teamName = mBean.getNomTeam3();
		    break;
		case 4:
		    teamName = mBean.getNomTeam4();
		    break;
		case 5:
		    teamName = mBean.getNomTeam5();
		    break;
		case 6:
		    teamName = mBean.getNomTeam6();
		    break;
		case 7:
		    teamName = mBean.getNomTeam7();
		    break;
		case 8:
		    teamName = mBean.getNomTeam8();
		    break;
		case 9:
		    teamName = mBean.getNomTeam9();
		    break;
		case 10:
		    teamName = mBean.getNomTeam10();
		    break;
		case 11:
		    teamName = mBean.getNomTeam11();
		    break;
		case 12:
		    teamName = mBean.getNomTeam12();
		    break;
		}

		if (teamName == null || teamName == "") {
		    return true;

		} else {
		    MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
		    mBeanMessageErreur.setErreurCreateNewTeam(CREATE_NEW_USER_NO_GOOD);
		    req.setAttribute("MessageErreurBeans", mBeanMessageErreur);
		    return false;
		}

	    } else {

		MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
		mBeanMessageErreur.setErreurCreateNewTeam(CREATE_NEW_USER_NO_GOOD);
		req.setAttribute("MessageErreurBeans", mBeanMessageErreur);
		return false;
	    }

	}
    }

    public void storePoolAndUserInfo(String nomDuTeam, int poolId2, HttpServletRequest req) {
	// TODO ajouter les info partout

	Utilisateur mBeanUser = (Utilisateur) req.getSession().getAttribute("Utilisateur");
	// on récupère le numero du Pool et de l'équipe

	int poolID = mBeanUser.getPoolId();
	int teamID = mBeanUser.getTeamId();
	String username = mBeanUser.getNomUtilisateur();
	// on trouve la date de l'année
	// TODO rendre dynamique
	int years = 2017;

	// on insere les info dans la table classement
	Boolean checkIfTeamAlreadyCreate = classementDao.checkIfTeamAlreadyCreate(teamID, poolID);
	if (checkIfTeamAlreadyCreate) {
	    classementDao.updateTeamInClassement(nomDuTeam, teamID, poolID);
	} else {
	    classementDao.insertTeamInClassement(nomDuTeam, teamID, poolID, years);
	}
	// on change la valeur de firstConnexionFinish dans Utilisateur et de numTeamCreate + nom d'equipe dans POOL

	// on recupere le bean POOL du memcache ou du datastore

	MemcacheService memcache = MemcacheServiceFactory.getMemcacheService();
	Key clefMemCachePool = KeyFactory.createKey("Pool", Integer.toString(mBeanUser.getPoolId()));

	Pool mBeanPool = (Pool) memcache.get(clefMemCachePool);

	if (mBeanPool == null) {

	    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

	    try {
		Entity mEntity = datastore.get(clefMemCachePool);

		mBeanPool = mapPoolFromDatastore(mEntity, mBeanPool);

	    } catch (EntityNotFoundException e) {
		// TODO gérer cette erreur
	    }

	}

	int tempNumTeam = mBeanPool.getNumTeamCreate();
	int numTeamCreate = tempNumTeam + 1;
	// on modifie les beans
	mBeanUser.setTeamName(nomDuTeam);
	mBeanUser.setFirstConnexionFinish(1);
	mBeanPool.setNumTeamCreate(numTeamCreate);
	switch (teamID) {

	case 1:
	    mBeanPool.setNomTeam1(nomDuTeam);
	    break;
	case 2:
	    mBeanPool.setNomTeam2(nomDuTeam);
	    break;
	case 3:
	    mBeanPool.setNomTeam3(nomDuTeam);
	    break;
	case 4:
	    mBeanPool.setNomTeam4(nomDuTeam);
	    break;
	case 5:
	    mBeanPool.setNomTeam5(nomDuTeam);
	    break;
	case 6:
	    mBeanPool.setNomTeam6(nomDuTeam);
	    break;
	case 7:
	    mBeanPool.setNomTeam7(nomDuTeam);
	    break;
	case 8:
	    mBeanPool.setNomTeam8(nomDuTeam);
	    break;
	case 9:
	    mBeanPool.setNomTeam9(nomDuTeam);
	    break;
	case 10:
	    mBeanPool.setNomTeam10(nomDuTeam);
	    break;
	case 11:
	    mBeanPool.setNomTeam11(nomDuTeam);
	    break;
	case 12:
	    mBeanPool.setNomTeam12(nomDuTeam);
	    break;

	}

	// on recréer les sessions
	req.getSession().setAttribute("Utilisateur", mBeanUser);
	req.getSession().setAttribute("Pool", mBeanPool);

	// on persiste dans la memcache
	Key clefMemCacheUser = KeyFactory.createKey("Utilisateur", username);
	memcache.put(clefMemCacheUser, mBeanUser);
	memcache.put(clefMemCachePool, mBeanPool);

	// on persiste dans Datastore

	EntityManagerFactory emf = EMF.get();
	EntityManager em = null;

	try {
	    em = emf.createEntityManager();
	    em.merge(mBeanUser);
	} finally {
	    if (em != null)
		em.close();
	}

	try {
	    em = emf.createEntityManager();
	    em.merge(mBeanPool);
	} finally {
	    if (em != null)
		em.close();
	}

    }

    private Pool mapPoolFromDatastore(Entity mEntity, Pool mBeanPool) {

	EntityManagerFactory emf = EMF.get();
	EntityManager em = null;

	try {
	    em = emf.createEntityManager();
	    mBeanPool = em.find(Pool.class, mEntity.getKey());
	} finally {
	    if (em != null)
		em.close();
	}

	return mBeanPool;
    }

}
