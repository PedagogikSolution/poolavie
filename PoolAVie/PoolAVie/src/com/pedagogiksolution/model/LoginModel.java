package com.pedagogiksolution.model;

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
import com.pedagogiksolution.datastorebeans.Classement;
import com.pedagogiksolution.datastorebeans.Pool;
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

    // Methode qui valide les identifiants (username et password) et retourne message d'erreur si pas bon
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
		// si existe, aucun EntityNotFoundException, donc on recupère l'info pour tester password
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
	    // si le bean existe on verifie si le mot de passe est le bon, si bon on place dans session et retourne true
	} else {
	    String mPasswordEncrypt = mBean.getMotDePasse();
	    PasswordEncryption mEncrypt = new PasswordEncryption();
	    try {
		boolean passwordMatch = mEncrypt.validatePassword(password, mPasswordEncrypt);
		if (passwordMatch) {
		    // on place les informations du beans utilisateurs dans un objet de session en ayant placer la
// valeur loginReussi a 1.
		    mBean.setLoginReussi(1);
		    requestObject.getSession().setAttribute("Utilisateur", mBean);
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

    // Méthode qui vérifie si le processus de validation du compte est terminé, si pas fini, on envoie à la page de
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
		// si existe, aucun EntityNotFoundException, donc on recupère l'info pour tester password
		Entity mEntity = datastore.get(clefDatastore);
		
		// on met dans SessionBean
		mBeanPool = mapPoolFromDatastore(mEntity, mBeanPool);
		requestObject.getSession().setAttribute("Pool", mBeanPool);
		
		// on met dans MemCache 
		memcache.put(clefMemCache, mBeanPool);
		
		return true;

	    } catch (EntityNotFoundException e) {
		MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
		mBeanMessageErreur.setErreurFormulaireLogin(LOGIN_USERNAME_DONT_EXIST);
		requestObject.setAttribute("MessageErreurBeans", mBeanMessageErreur);
		return false;
	    }
	    // si le bean existe on verifie si le mot de passe est le bon, si bon on place dans session et retourne true
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

		// on verifie si le bean Pool existe dans le memCache
		MemcacheService memcache = MemcacheServiceFactory.getMemcacheService();
		Key clefMemCache = KeyFactory.createKey("Classement", poolId);
		Classement mBeanClassement = (Classement) memcache.get(clefMemCache);

		// si l'objet n'existe pas, on verifie si il existe dans le Datastore
		if (mBeanClassement == null) {

		    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		    Key clefDatastore = KeyFactory.createKey("Classement", Integer.toString(poolId));
		    try {
			// si existe, aucun EntityNotFoundException, donc on recupère l'info pour tester password
			Entity mEntity = datastore.get(clefDatastore);
			
			// on met dans SessionBean
			mBeanClassement = mapClassementFromDatastore(mEntity, mBeanClassement);
			requestObject.getSession().setAttribute("Classement", mBeanClassement);
			
			// on met dans memcache
			memcache.put(clefMemCache, mBeanClassement);
			
			return true;

		    } catch (EntityNotFoundException e) {
			MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
			mBeanMessageErreur.setErreurFormulaireLogin(LOGIN_USERNAME_DONT_EXIST);
			requestObject.setAttribute("MessageErreurBeans", mBeanMessageErreur);
			return false;
		    }
		    // si le bean existe on verifie si le mot de passe est le bon, si bon on place dans session et retourne true
		} else {
		    requestObject.getSession().setAttribute("Classement", mBeanClassement);
		    return true;

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


}
