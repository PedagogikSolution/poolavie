package com.pedagogiksolution.model;

import static com.pedagogiksolution.constants.MessageErreurConstants.CREATE_POOL_PAS_FINI;
import static com.pedagogiksolution.constants.MessageErreurConstants.LOGIN_PASSWORD_NOT_GOOD;
import static com.pedagogiksolution.constants.MessageErreurConstants.LOGIN_USERNAME_DONT_EXIST;
import static com.pedagogiksolution.constants.MessageErreurConstants.REGISTRATION_ERREUR_PASSWORD_ENCRYPTION;
import static com.pedagogiksolution.constants.MessageErreurConstants.VALIDATION_CODE_ERREUR_PAS_ENCORE_FAIT;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

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
import com.pedagogiksolution.datastorebeans.Attaquant;
import com.pedagogiksolution.datastorebeans.Classement;
import com.pedagogiksolution.datastorebeans.Defenseur;
import com.pedagogiksolution.datastorebeans.DraftPick;
import com.pedagogiksolution.datastorebeans.DraftProcess;
import com.pedagogiksolution.datastorebeans.DraftRound;
import com.pedagogiksolution.datastorebeans.Equipe;
import com.pedagogiksolution.datastorebeans.Gardien;
import com.pedagogiksolution.datastorebeans.Pool;
import com.pedagogiksolution.datastorebeans.Recrue;
import com.pedagogiksolution.datastorebeans.Utilisateur;
import com.pedagogiksolution.utils.EMF;
import com.pedagogiksolution.utils.PasswordEncryption;

public class LoginModel {

	// variable d'instance
	private String username;
	private String password;
	private HttpServletRequest requestObject;

	// contructeur pour le Servlet Login
	public LoginModel(String nomUtilisateur, String motDePasse, HttpServletRequest req) {
		username = nomUtilisateur;
		password = motDePasse;
		requestObject = req;
	}

	public LoginModel(HttpServletRequest req) {
		requestObject = req;
	}

	// Methode qui valide les identifiants (username et password) et retourne
	// message d'erreur si pas bon
	// Création du Session Bean Utilisateur, avec valeur de login à 1
	public Boolean validateCredential() {

		// on verifie si le bean Utilisateur existe dans le memCache
		MemcacheService memcache = MemcacheServiceFactory.getMemcacheService();
		Key clefMemCache = KeyFactory.createKey("Utilisateur", username);
		Utilisateur mBean = (Utilisateur) memcache.get(clefMemCache);

		// si l'objet n'existe pas, on verifie si il existe dans le Datastore
		if (mBean == null) {

			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			Key clefDatastore = KeyFactory.createKey("Utilisateur", username);
			try {
				// si existe, aucun EntityNotFoundException, donc on recupère
				// l'info pour tester password
				Entity mEntity = datastore.get(clefDatastore);
				String mEncryptPassword = (String) mEntity.getProperty("motDePasse");

				PasswordEncryption mEncrypt = new PasswordEncryption();
				try {
					boolean passwordMatch = mEncrypt.validatePassword(password, mEncryptPassword);
					if (passwordMatch) {
						// si match on place dans bean Utilisateur
						mBean = mapUtilisateurFromDatastore(mEntity, mBean);
						// on met la valeur de LoginReussi a 1
						mBean.setLoginReussi(1);
						// on place dans la session le Bean Utilisateur
						requestObject.getSession().setAttribute("Utilisateur", mBean);
						// on place dans la Memcache pour connexion futur
						memcache.put(clefMemCache, mBean);
						return true;
					} else {
						MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
						mBeanMessageErreur.setErreurFormulaireLogin(LOGIN_PASSWORD_NOT_GOOD);
						requestObject.setAttribute("MessageErreurBeans", mBeanMessageErreur);
						return false;
					}

				} catch (NoSuchAlgorithmException e) {
					MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
					mBeanMessageErreur.setErreurFormulaireLogin(REGISTRATION_ERREUR_PASSWORD_ENCRYPTION);
					requestObject.setAttribute("MessageErreurBeans", mBeanMessageErreur);
					return false;
				} catch (InvalidKeySpecException e) {
					MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
					mBeanMessageErreur.setErreurFormulaireLogin(REGISTRATION_ERREUR_PASSWORD_ENCRYPTION);
					requestObject.setAttribute("MessageErreurBeans", mBeanMessageErreur);
					return false;
				}

			} catch (EntityNotFoundException e) {
				MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
				mBeanMessageErreur.setErreurFormulaireLogin(LOGIN_USERNAME_DONT_EXIST);
				requestObject.setAttribute("MessageErreurBeans", mBeanMessageErreur);
				return false;
			}
			// si le bean existe on verifie si le mot de passe est le bon, si
			// bon on place dans session et retourne true
		} else {
			String mPasswordEncrypt = mBean.getMotDePasse();
			PasswordEncryption mEncrypt = new PasswordEncryption();
			try {
				boolean passwordMatch = mEncrypt.validatePassword(password, mPasswordEncrypt);
				if (passwordMatch) {
					// on place les informations du beans utilisateurs dans un
					// objet de session en ayant placer la
					// valeur loginReussi a 1.
					mBean.setLoginReussi(1);
					requestObject.getSession().setAttribute("Utilisateur", mBean);
					// on place dans MemCache pour connexion futur
					memcache.put(clefMemCache, mBean);
					return true;
				} else {
					MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
					mBeanMessageErreur.setErreurFormulaireLogin(LOGIN_PASSWORD_NOT_GOOD);
					requestObject.setAttribute("MessageErreurBeans", mBeanMessageErreur);
					return false;
				}
			} catch (NoSuchAlgorithmException e) {
				MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
				mBeanMessageErreur.setErreurFormulaireLogin(REGISTRATION_ERREUR_PASSWORD_ENCRYPTION);
				requestObject.setAttribute("MessageErreurBeans", mBeanMessageErreur);
				return false;
			} catch (InvalidKeySpecException e) {
				MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
				mBeanMessageErreur.setErreurFormulaireLogin(REGISTRATION_ERREUR_PASSWORD_ENCRYPTION);
				requestObject.setAttribute("MessageErreurBeans", mBeanMessageErreur);
				return false;
			}

		}

	}

	// Méthode qui vérifie si le processus de validation du compte est terminé,
	// si pas fini, on envoie à la page de
	// validation
	public Boolean checkIfValidateAccount() {
		Utilisateur mBean = (Utilisateur) requestObject.getSession().getAttribute("Utilisateur");
		int validationReussi = mBean.getValidationReussi();
		if (validationReussi == 1) {
			return true;
		} else {
			MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
			mBeanMessageErreur.setErreurCodeValidationType2(VALIDATION_CODE_ERREUR_PAS_ENCORE_FAIT);
			requestObject.setAttribute("MessageErreurBeans", mBeanMessageErreur);
			return false;
		}

	}

	// Méthode pour initialiser le Session Bean Pool et Equipe
	public Boolean createSessionPoolBean() {

		// on recupere le poolID et le teamId du Session Bean Utilisateur
		Utilisateur mBean = (Utilisateur) requestObject.getSession().getAttribute("Utilisateur");

		// int teamId = mBean.getTeamId();
		int poolId = mBean.getPoolId();

		// on verifie si le bean Pool existe dans le memCache
		MemcacheService memcache = MemcacheServiceFactory.getMemcacheService();
		Key clefMemCache = KeyFactory.createKey("Pool", poolId);
		Pool mBeanPool = (Pool) memcache.get(clefMemCache);

		// si l'objet n'existe pas, on verifie si il existe dans le Datastore
		if (mBeanPool == null) {

			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			Key clefDatastore = KeyFactory.createKey("Pool", Integer.toString(poolId));
			try {
				// si existe, aucun EntityNotFoundException, donc on recupère
				// l'info pour tester password
				Entity mEntity = datastore.get(clefDatastore);

				// on met dans SessionBean
				mBeanPool = mapPoolFromDatastore(mEntity, mBeanPool);
				requestObject.getSession().setAttribute("Pool", mBeanPool);

				// on met dans MemCache
				memcache.put(clefMemCache, mBeanPool);

				return true;

			} catch (EntityNotFoundException e) {
				MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
				mBeanMessageErreur.setErreurFormulaireLogin(CREATE_POOL_PAS_FINI);
				requestObject.setAttribute("MessageErreurBeans", mBeanMessageErreur);
				return false;
			}
			// si le bean existe on verifie si le mot de passe est le bon, si
			// bon on place dans session et retourne true
		} else {
			requestObject.getSession().setAttribute("Pool", mBeanPool);
			return true;

		}

	}

	public Boolean createSessionClassementBean() {
		// on recupere le poolID et le teamId du Session Bean Utilisateur
		Utilisateur mBean = (Utilisateur) requestObject.getSession().getAttribute("Utilisateur");

		// int teamId = mBean.getTeamId();
		int poolId = mBean.getPoolId();
		String poolID = String.valueOf(poolId);

		// on verifie si le bean Pool existe dans le memCache
		MemcacheService memcache = MemcacheServiceFactory.getMemcacheService();
		Key clefMemCache = KeyFactory.createKey("Classement", poolId);
		Classement mBeanClassement = (Classement) memcache.get(clefMemCache);

		// si l'objet n'existe pas, on verifie si il existe dans le Datastore
		if (mBeanClassement == null) {

			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			Key clefDatastore = KeyFactory.createKey("Classement", poolID);
			try {
				// si existe, aucun EntityNotFoundException, donc on recupère
				// l'info pour tester password
				Entity mEntity = datastore.get(clefDatastore);

				// on met dans SessionBean
				mBeanClassement = mapClassementFromDatastore(mEntity, mBeanClassement);
				requestObject.getSession().setAttribute("Classement", mBeanClassement);

				// on met dans memcache
				memcache.put(clefMemCache, mBeanClassement);

				return true;

			} catch (EntityNotFoundException e) {
				MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
				mBeanMessageErreur.setErreurFormulaireLogin(CREATE_POOL_PAS_FINI);
				requestObject.setAttribute("MessageErreurBeans", mBeanMessageErreur);
				return false;
			}
			// si le bean existe on verifie si le mot de passe est le bon, si
			// bon on place dans session et retourne true
		} else {
			requestObject.getSession().setAttribute("Classement", mBeanClassement);
			return true;

		}
	}

	public Boolean createSessionEquipeBean() {

		Utilisateur mBeanUser = (Utilisateur) requestObject.getSession().getAttribute("Utilisateur");
		Pool mBeanPool = (Pool) requestObject.getSession().getAttribute("Pool");
		// on reparse en int pour le stockage
		int poolId = mBeanUser.getPoolId();
		int nombreEquipe = mBeanPool.getNumberTeam();

		for (int i = 1; i < (nombreEquipe + 1); i++) {

			String jspSessionName = "Equipe" + i;
			String datastoreId = poolId + "_" + i;

			MemcacheService memcache = MemcacheServiceFactory.getMemcacheService();
			Key clefMemCache = KeyFactory.createKey("Equipe", datastoreId);
			Equipe mBeanEquipe = (Equipe) memcache.get(clefMemCache);

			if (mBeanEquipe == null) {
				DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
				Key clefDatastore = KeyFactory.createKey("Equipe", datastoreId);
				try {
					// si existe, aucun EntityNotFoundException, donc on
					// recupère l'info pour tester password
					Entity mEntity = datastore.get(clefDatastore);

					// on met dans SessionBean
					mBeanEquipe = mapEquipeFromDatastore(mEntity, mBeanEquipe);
					requestObject.getSession().setAttribute(jspSessionName, mBeanEquipe);

					// on met dans memcache
					memcache.put(clefMemCache, mBeanEquipe);

				} catch (EntityNotFoundException e) {
					MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
					mBeanMessageErreur.setErreurFormulaireLogin(CREATE_POOL_PAS_FINI);
					requestObject.setAttribute("MessageErreurBeans", mBeanMessageErreur);
					return false;
				}

			} else {
				requestObject.getSession().setAttribute(jspSessionName, mBeanEquipe);
			}

		}
		return true;

	}

	public Boolean createSessionDraftRoundBean() {
		// on recupere le poolID et le teamId du Session Bean Utilisateur
		Utilisateur mBean = (Utilisateur) requestObject.getSession().getAttribute("Utilisateur");

		// int teamId = mBean.getTeamId();
		int poolId = mBean.getPoolId();
		String poolID = String.valueOf(poolId);

		// on verifie si le bean DraftRound existe dans le memCache
		MemcacheService memcache = MemcacheServiceFactory.getMemcacheService();
		Key clefMemCache = KeyFactory.createKey("DraftRound", poolId);
		DraftRound mBeanDraftRound = (DraftRound) memcache.get(clefMemCache);

		// si l'objet n'existe pas, on verifie si il existe dans le Datastore
		if (mBeanDraftRound == null) {

			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			Key clefDatastore = KeyFactory.createKey("DraftRound", poolID);
			try {
				// si existe, aucun EntityNotFoundException, donc on recupère
				// l'info pour tester password
				Entity mEntity = datastore.get(clefDatastore);

				// on met dans SessionBean
				mBeanDraftRound = mapDraftRoundFromDatastore(mEntity, mBeanDraftRound);
				requestObject.getSession().setAttribute("DraftRound", mBeanDraftRound);

				// on met dans memcache
				memcache.put(clefMemCache, mBeanDraftRound);

				return true;

			} catch (EntityNotFoundException e) {
				MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
				mBeanMessageErreur.setErreurFormulaireLogin(CREATE_POOL_PAS_FINI);
				requestObject.setAttribute("MessageErreurBeans", mBeanMessageErreur);
				return false;
			}
			// si le bean existe on verifie si le mot de passe est le bon, si
			// bon on place dans session et retourne true
		} else {
			requestObject.getSession().setAttribute("DraftRound", mBeanDraftRound);
			return true;

		}
	}

	public Boolean createSessionDraftPickBean() {

		Utilisateur mBeanUser = (Utilisateur) requestObject.getSession().getAttribute("Utilisateur");
		Pool mBeanPool = (Pool) requestObject.getSession().getAttribute("Pool");
		// on reparse en int pour le stockage
		int poolId = mBeanUser.getPoolId();
		int nombreEquipe = mBeanPool.getNumberTeam();

		for (int i = 1; i < (nombreEquipe + 1); i++) {

			String jspSessionName = "DraftPick" + i;
			String datastoreId = poolId + "_" + i;

			MemcacheService memcache = MemcacheServiceFactory.getMemcacheService();
			Key clefMemCache = KeyFactory.createKey("DraftPick", datastoreId);
			DraftPick mBeanDraftPick = (DraftPick) memcache.get(clefMemCache);

			if (mBeanDraftPick == null) {
				DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
				Key clefDatastore = KeyFactory.createKey("DraftPick", datastoreId);
				try {
					// si existe, aucun EntityNotFoundException, donc on
					// recupère l'info pour tester password
					Entity mEntity = datastore.get(clefDatastore);

					// on met dans SessionBean
					mBeanDraftPick = mapDraftPickFromDatastore(mEntity, mBeanDraftPick);
					requestObject.getSession().setAttribute(jspSessionName, mBeanDraftPick);

					// on met dans memcache
					memcache.put(clefMemCache, mBeanDraftPick);

				} catch (EntityNotFoundException e) {
					MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
					mBeanMessageErreur.setErreurFormulaireLogin(CREATE_POOL_PAS_FINI);
					requestObject.setAttribute("MessageErreurBeans", mBeanMessageErreur);
					return false;
				}

			} else {
				requestObject.getSession().setAttribute(jspSessionName, mBeanDraftPick);
			}

		}
		return true;
	}

	public Boolean createSessionAttaquantBean() {
		Utilisateur mBeanUser = (Utilisateur) requestObject.getSession().getAttribute("Utilisateur");
		Pool mBeanPool = (Pool) requestObject.getSession().getAttribute("Pool");
		// on reparse en int pour le stockage
		int poolId = mBeanUser.getPoolId();
		int nombreEquipe = mBeanPool.getNumberTeam();

		for (int i = 1; i < (nombreEquipe + 1); i++) {

			String jspSessionName = "Attaquant" + i;
			String datastoreId = poolId + "_" + i;

			MemcacheService memcache = MemcacheServiceFactory.getMemcacheService();
			Key clefMemCache = KeyFactory.createKey("Attaquant", datastoreId);
			Attaquant mBeanAttaquant = (Attaquant) memcache.get(clefMemCache);

			if (mBeanAttaquant == null) {
				DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
				Key clefDatastore = KeyFactory.createKey("Attaquant", datastoreId);
				try {
					// si existe, aucun EntityNotFoundException, donc on
					// recupère l'info pour tester password
					Entity mEntity = datastore.get(clefDatastore);

					// on met dans SessionBean
					mBeanAttaquant = mapAttaquantFromDatastore(mEntity, mBeanAttaquant);
					requestObject.getSession().setAttribute(jspSessionName, mBeanAttaquant);

					// on met dans memcache
					memcache.put(clefMemCache, mBeanAttaquant);

				} catch (EntityNotFoundException e) {
					MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
					mBeanMessageErreur.setErreurFormulaireLogin(CREATE_POOL_PAS_FINI);
					requestObject.setAttribute("MessageErreurBeans", mBeanMessageErreur);
					return false;
				}

			} else {
				requestObject.getSession().setAttribute(jspSessionName, mBeanAttaquant);
			}

		}
		return true;
	}

	public Boolean createSessionDefenseurBean() {
		Utilisateur mBeanUser = (Utilisateur) requestObject.getSession().getAttribute("Utilisateur");
		Pool mBeanPool = (Pool) requestObject.getSession().getAttribute("Pool");
		// on reparse en int pour le stockage
		int poolId = mBeanUser.getPoolId();
		int nombreEquipe = mBeanPool.getNumberTeam();

		for (int i = 1; i < (nombreEquipe + 1); i++) {

			String jspSessionName = "Defenseur" + i;
			String datastoreId = poolId + "_" + i;

			MemcacheService memcache = MemcacheServiceFactory.getMemcacheService();
			Key clefMemCache = KeyFactory.createKey("Defenseur", datastoreId);
			Defenseur mBeanDefenseur = (Defenseur) memcache.get(clefMemCache);

			if (mBeanDefenseur == null) {
				DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
				Key clefDatastore = KeyFactory.createKey("Defenseur", datastoreId);
				try {
					// si existe, aucun EntityNotFoundException, donc on
					// recupère l'info pour tester password
					Entity mEntity = datastore.get(clefDatastore);

					// on met dans SessionBean
					mBeanDefenseur = mapDefenseurFromDatastore(mEntity, mBeanDefenseur);
					requestObject.getSession().setAttribute(jspSessionName, mBeanDefenseur);

					// on met dans memcache
					memcache.put(clefMemCache, mBeanDefenseur);

				} catch (EntityNotFoundException e) {
					MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
					mBeanMessageErreur.setErreurFormulaireLogin(CREATE_POOL_PAS_FINI);
					requestObject.setAttribute("MessageErreurBeans", mBeanMessageErreur);
					return false;
				}

			} else {
				requestObject.getSession().setAttribute(jspSessionName, mBeanDefenseur);
			}

		}
		return true;
	}

	public Boolean createSessionGardienBean() {
		Utilisateur mBeanUser = (Utilisateur) requestObject.getSession().getAttribute("Utilisateur");
		Pool mBeanPool = (Pool) requestObject.getSession().getAttribute("Pool");
		// on reparse en int pour le stockage
		int poolId = mBeanUser.getPoolId();
		int nombreEquipe = mBeanPool.getNumberTeam();

		for (int i = 1; i < (nombreEquipe + 1); i++) {

			String jspSessionName = "Gardien" + i;
			String datastoreId = poolId + "_" + i;

			MemcacheService memcache = MemcacheServiceFactory.getMemcacheService();
			Key clefMemCache = KeyFactory.createKey("Gardien", datastoreId);
			Gardien mBeanGardien = (Gardien) memcache.get(clefMemCache);

			if (mBeanGardien == null) {
				DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
				Key clefDatastore = KeyFactory.createKey("Gardien", datastoreId);
				try {
					// si existe, aucun EntityNotFoundException, donc on
					// recupère l'info pour tester password
					Entity mEntity = datastore.get(clefDatastore);

					// on met dans SessionBean
					mBeanGardien = mapGardienFromDatastore(mEntity, mBeanGardien);
					requestObject.getSession().setAttribute(jspSessionName, mBeanGardien);

					// on met dans memcache
					memcache.put(clefMemCache, mBeanGardien);

				} catch (EntityNotFoundException e) {
					MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
					mBeanMessageErreur.setErreurFormulaireLogin(CREATE_POOL_PAS_FINI);
					requestObject.setAttribute("MessageErreurBeans", mBeanMessageErreur);
					return false;
				}

			} else {
				requestObject.getSession().setAttribute(jspSessionName, mBeanGardien);
			}

		}
		return true;
	}

	public Boolean createSessionRecrueBean() {
		Utilisateur mBeanUser = (Utilisateur) requestObject.getSession().getAttribute("Utilisateur");
		Pool mBeanPool = (Pool) requestObject.getSession().getAttribute("Pool");
		// on reparse en int pour le stockage
		int poolId = mBeanUser.getPoolId();
		int nombreEquipe = mBeanPool.getNumberTeam();

		for (int i = 1; i < (nombreEquipe + 1); i++) {

			String jspSessionName = "Recrue" + i;
			String datastoreId = poolId + "_" + i;

			MemcacheService memcache = MemcacheServiceFactory.getMemcacheService();
			Key clefMemCache = KeyFactory.createKey("Recrue", datastoreId);
			Recrue mBeanRecrue = (Recrue) memcache.get(clefMemCache);

			if (mBeanRecrue == null) {
				DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
				Key clefDatastore = KeyFactory.createKey("Recrue", datastoreId);
				try {
					// si existe, aucun EntityNotFoundException, donc on
					// recupère l'info pour tester password
					Entity mEntity = datastore.get(clefDatastore);

					// on met dans SessionBean
					mBeanRecrue = mapRecrueFromDatastore(mEntity, mBeanRecrue);
					requestObject.getSession().setAttribute(jspSessionName, mBeanRecrue);

					// on met dans memcache
					memcache.put(clefMemCache, mBeanRecrue);

				} catch (EntityNotFoundException e) {
					MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
					mBeanMessageErreur.setErreurFormulaireLogin(CREATE_POOL_PAS_FINI);
					requestObject.setAttribute("MessageErreurBeans", mBeanMessageErreur);
					return false;
				}

			} else {
				requestObject.getSession().setAttribute(jspSessionName, mBeanRecrue);
			}

		}
		return true;
	}

public void resetConnexionOpen() {
		Utilisateur mUserBean = new Utilisateur();
		Pool mBeanPool = new Pool();
		mUserBean = (Utilisateur) requestObject.getSession().getAttribute("Utilisateur");
		int teamId= mUserBean.getTeamId();
		mBeanPool = (Pool) requestObject.getSession().getAttribute("Pool");
		String poolID = mBeanPool.getPoolID();
		
		DraftProcess mBeanDraftProcess = new DraftProcess();

		Key clefDatastore = KeyFactory.createKey("DraftProcess", poolID);

		EntityManagerFactory emf = EMF.get();
		EntityManager em = null;
		try {

		    em = emf.createEntityManager();
		    mBeanDraftProcess = em.find(DraftProcess.class, clefDatastore);
		    switch(teamId){
		    case 1:
		    	 mBeanDraftProcess.setTeam1IsOpen(0);
		    	break;
		    case 2:
		    	mBeanDraftProcess.setTeam2IsOpen(0);
		    	break;
		    case 3:
		    	mBeanDraftProcess.setTeam3IsOpen(0);
		    	break;
		    case 4:
		    	mBeanDraftProcess.setTeam4IsOpen(0);
		    	break;
		    case 5:
		    	mBeanDraftProcess.setTeam5IsOpen(0);
		    	break;
		    case 6:
		    	mBeanDraftProcess.setTeam6IsOpen(0);
		    	break;
		    case 7:
		    	mBeanDraftProcess.setTeam7IsOpen(0);
		    	break;
		    case 8:
		    	mBeanDraftProcess.setTeam8IsOpen(0);
		    	break;
		    case 9:
		    	mBeanDraftProcess.setTeam9IsOpen(0);
		    	break;
		    case 10:
		    	mBeanDraftProcess.setTeam10IsOpen(0);
		    	break;
		    case 11:
		    	mBeanDraftProcess.setTeam11IsOpen(0);
		    	break;
		    case 12:
		    	mBeanDraftProcess.setTeam12IsOpen(0);
		    	break;
		    
		    	
		    }
		   
		    mBeanDraftProcess = em.merge(mBeanDraftProcess);
		} finally {
		    if (em != null)
			em.close();
		}
		
		
		
	}

	/********** methode privée à la classe métier *************/

	private Utilisateur mapUtilisateurFromDatastore(Entity mEntity, Utilisateur mBean) {

		EntityManagerFactory emf = EMF.get();
		EntityManager em = null;

		try {
			em = emf.createEntityManager();
			mBean = em.find(Utilisateur.class, mEntity.getKey());
		} finally {
			if (em != null)
				em.close();
		}

		return mBean;
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

	private Classement mapClassementFromDatastore(Entity mEntity, Classement mBeanClassement) {
		EntityManagerFactory emf = EMF.get();
		EntityManager em = null;

		try {
			em = emf.createEntityManager();
			mBeanClassement = em.find(Classement.class, mEntity.getKey());
		} finally {
			if (em != null)
				em.close();
		}

		return mBeanClassement;
	}

	private DraftRound mapDraftRoundFromDatastore(Entity mEntity, DraftRound mBeanDraftRound) {
		EntityManagerFactory emf = EMF.get();
		EntityManager em = null;

		try {
			em = emf.createEntityManager();
			mBeanDraftRound = em.find(DraftRound.class, mEntity.getKey());
		} finally {
			if (em != null)
				em.close();
		}

		return mBeanDraftRound;
	}

	private Equipe mapEquipeFromDatastore(Entity mEntity, Equipe mBeanEquipe) {
		EntityManagerFactory emf = EMF.get();
		EntityManager em = null;

		try {
			em = emf.createEntityManager();
			mBeanEquipe = em.find(Equipe.class, mEntity.getKey());
		} finally {
			if (em != null)
				em.close();
		}

		return mBeanEquipe;
	}

	private DraftPick mapDraftPickFromDatastore(Entity mEntity, DraftPick mBeanDraftPick) {
		EntityManagerFactory emf = EMF.get();
		EntityManager em = null;

		try {
			em = emf.createEntityManager();
			mBeanDraftPick = em.find(DraftPick.class, mEntity.getKey());
		} finally {
			if (em != null)
				em.close();
		}

		return mBeanDraftPick;
	}

	private Attaquant mapAttaquantFromDatastore(Entity mEntity, Attaquant mBeanAttaquant) {
		EntityManagerFactory emf = EMF.get();
		EntityManager em = null;

		try {
			em = emf.createEntityManager();
			mBeanAttaquant = em.find(Attaquant.class, mEntity.getKey());
		} finally {
			if (em != null)
				em.close();
		}

		return mBeanAttaquant;
	}

	private Defenseur mapDefenseurFromDatastore(Entity mEntity, Defenseur mBeanDefenseur) {
		EntityManagerFactory emf = EMF.get();
		EntityManager em = null;

		try {
			em = emf.createEntityManager();
			mBeanDefenseur = em.find(Defenseur.class, mEntity.getKey());
		} finally {
			if (em != null)
				em.close();
		}

		return mBeanDefenseur;
	}

	private Gardien mapGardienFromDatastore(Entity mEntity, Gardien mBeanGardien) {
		EntityManagerFactory emf = EMF.get();
		EntityManager em = null;

		try {
			em = emf.createEntityManager();
			mBeanGardien = em.find(Gardien.class, mEntity.getKey());
		} finally {
			if (em != null)
				em.close();
		}

		return mBeanGardien;
	}

	private Recrue mapRecrueFromDatastore(Entity mEntity, Recrue mBeanRecrue) {
		EntityManagerFactory emf = EMF.get();
		EntityManager em = null;

		try {
			em = emf.createEntityManager();
			mBeanRecrue = em.find(Recrue.class, mEntity.getKey());
		} finally {
			if (em != null)
				em.close();
		}

		return mBeanRecrue;
	}

}
