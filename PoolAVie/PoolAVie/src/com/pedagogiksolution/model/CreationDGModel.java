package com.pedagogiksolution.model;

import static com.pedagogiksolution.constants.MessageErreurConstants.CREATE_NEW_USER_NO_GOOD;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.pedagogiksolution.beans.MessageErreurBeans;
import com.pedagogiksolution.dao.ClassementDao;
import com.pedagogiksolution.datastorebeans.Pool;
import com.pedagogiksolution.datastorebeans.Utilisateur;

public class CreationDGModel {

	private ClassementDao classementDao;

	public CreationDGModel(ClassementDao classementDao) {
		this.classementDao = classementDao;

	}

	public CreationDGModel() {
		// TODO Auto-generated constructor stub
	}

	public Boolean validationNewDG(String poolID, String teamID, String codeValidationPool, HttpServletRequest req) {
		// on check d'abord si poolID existe, si oui si le code concorde et finalement
		// on verifie si ce team existe deja
		// ou si il est possible.
		// si pas plante a un moment x, on place un message dans le Bean d'erreur et
		// retourne false

		Boolean checkIfPoolIdExist = checkIfPoolIdExist(poolID, req);

		if (!checkIfPoolIdExist) {
			MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
			mBeanMessageErreur.setErreurCreateNewTeam("1");
			req.setAttribute("MessageErreurBeans", mBeanMessageErreur);
			return false;
		} else {

			Boolean checkIfCodeValidate = checkIfCodeValidate(poolID, codeValidationPool, req);

			if (!checkIfCodeValidate) {
				MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
				mBeanMessageErreur.setErreurCreateNewTeam("2");
				req.setAttribute("MessageErreurBeans", mBeanMessageErreur);
				return false;
			} else {
				Boolean checkIfTeamExist = checkIfTeamExist(poolID, teamID, req);

				if (!checkIfTeamExist) {
					MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
					mBeanMessageErreur.setErreurCreateNewTeam("Cette �quipe a d�j� �t� cr��e");
					req.setAttribute("MessageErreurBeans", mBeanMessageErreur);
					return false;
				} else {
					return true;
				}

			}

		}

	}

	private Boolean checkIfPoolIdExist(String poolID, HttpServletRequest req) {

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Key clefDatastore = KeyFactory.createKey("Pool", poolID);
		try {
			Entity mEntity = datastore.get(clefDatastore);
			if (mEntity != null) {
				return true;
			} else {
				MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
				mBeanMessageErreur.setErreurCreateNewTeam("1");
				req.setAttribute("MessageErreurBeans", mBeanMessageErreur);

				return false;
			}

		} catch (EntityNotFoundException e) {
			MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
			mBeanMessageErreur.setErreurCreateNewTeam("2");
			req.setAttribute("MessageErreurBeans", mBeanMessageErreur);
			return false;
		}

	}

	private Boolean checkIfCodeValidate(String poolID, String codeValidationPool, HttpServletRequest req) {

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

	}

	private Boolean checkIfTeamExist(String poolID, String teamID, HttpServletRequest req) {

		int teamId = Integer.parseInt(teamID);

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

	}

	public void storePoolAndUserInfo(String nomDuTeam, int poolId2, String urlLogoTeam, HttpServletRequest req) {
		
		// on recupere les objets de sessions

		Utilisateur mBeanUser = (Utilisateur) req.getSession().getAttribute("Utilisateur");
		
		// on recupere le numero du Pool et de l'equipe
		

		int poolID = mBeanUser.getPoolId();
		int teamID = mBeanUser.getTeamId();
		// on trouve la date de l'annee
		DateTime dt = new DateTime();
		int year = dt.getYear();
		
		// on insere les info dans la table classement
		Boolean checkIfTeamAlreadyCreate = classementDao.checkIfTeamAlreadyCreate(teamID, poolID);
		if (checkIfTeamAlreadyCreate) {
			classementDao.updateTeamInClassement(nomDuTeam, teamID, poolID);
		} else {
			classementDao.insertTeamInClassement(nomDuTeam, teamID, poolID, year);
		}
		// on change la valeur de firstConnexionFinish dans Utilisateur et de
		// numTeamCreate + nom d'equipe dans POOL

		// on recupere le bean POOL du memcache ou du datastore

		

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Pool mBeanPool = new Pool();
		Key clefDatastorePool = KeyFactory.createKey("Pool", Integer.toString(mBeanUser.getPoolId()));
		try {
			Entity mEntity = datastore.get(clefDatastorePool);

			mBeanPool = mBeanPool.mapPoolFromDatastore(mEntity, mBeanPool);

		} catch (EntityNotFoundException e) {
			
		}

		int tempNumTeam = mBeanPool.getNumTeamCreate();
		int numTeamCreate = tempNumTeam + 1;
		int numberTeamForPool = mBeanPool.getNumberTeam();
		// on modifie les beans
		mBeanUser.setTeamName(nomDuTeam);
		mBeanUser.setFirstConnexionFinish(1);
		mBeanPool.setNumTeamCreate(numTeamCreate);
		mBeanUser.setUrlTeamLogo(urlLogoTeam);

		if (numTeamCreate == numberTeamForPool) {
			mBeanPool.setCycleAnnuel(1);
		}
		switch (teamID) {

		case 1:
			mBeanPool.setNomTeam1(nomDuTeam);
			mBeanPool.setLogoTeam1(urlLogoTeam);
			break;
		case 2:
			mBeanPool.setNomTeam2(nomDuTeam);
			mBeanPool.setLogoTeam2(urlLogoTeam);
			break;
		case 3:
			mBeanPool.setNomTeam3(nomDuTeam);
			mBeanPool.setLogoTeam3(urlLogoTeam);
			break;
		case 4:
			mBeanPool.setNomTeam4(nomDuTeam);
			mBeanPool.setLogoTeam4(urlLogoTeam);
			break;
		case 5:
			mBeanPool.setNomTeam5(nomDuTeam);
			mBeanPool.setLogoTeam5(urlLogoTeam);
			break;
		case 6:
			mBeanPool.setNomTeam6(nomDuTeam);
			mBeanPool.setLogoTeam6(urlLogoTeam);
			break;
		case 7:
			mBeanPool.setNomTeam7(nomDuTeam);
			mBeanPool.setLogoTeam7(urlLogoTeam);
			break;
		case 8:
			mBeanPool.setNomTeam8(nomDuTeam);
			mBeanPool.setLogoTeam8(urlLogoTeam);
			break;
		case 9:
			mBeanPool.setNomTeam9(nomDuTeam);
			mBeanPool.setLogoTeam9(urlLogoTeam);
			break;
		case 10:
			mBeanPool.setNomTeam10(nomDuTeam);
			mBeanPool.setLogoTeam10(urlLogoTeam);
			break;
		case 11:
			mBeanPool.setNomTeam11(nomDuTeam);
			mBeanPool.setLogoTeam11(urlLogoTeam);
			break;
		case 12:
			mBeanPool.setNomTeam12(nomDuTeam);
			mBeanPool.setLogoTeam12(urlLogoTeam);
			break;

		}

		// on recreer les sessions
		req.getSession().setAttribute("Utilisateur", mBeanUser);
		req.getSession().setAttribute("Pool", mBeanPool);

		// on persiste dans Datastore

		Entity mEntity = mBeanUser.mapBeanToEntityForDatastore(mBeanUser, mBeanUser.getNomUtilisateur());

		datastore.put(mEntity);


		mEntity = mBeanPool.mapBeanToEntityForDatastore(mBeanPool, mBeanPool.getPoolID());

		datastore.put(mEntity);

	}

	

}
