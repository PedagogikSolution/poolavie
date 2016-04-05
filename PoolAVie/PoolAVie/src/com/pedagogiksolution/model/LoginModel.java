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
import com.pedagogiksolution.datastorebeans.Utilisateur;
import com.pedagogiksolution.utils.EMF;
import com.pedagogiksolution.utils.PasswordEncryption;

public class LoginModel {

    public Boolean validateCredential(String nomUtilisateur, String motDePasse, HttpServletRequest req) {

	// on verifie si un objet existe dans le memCache
	MemcacheService memcache = MemcacheServiceFactory.getMemcacheService();
	Key clefMemCache = KeyFactory.createKey("Utilisateur", nomUtilisateur);
	Utilisateur mBean = (Utilisateur) memcache.get(clefMemCache);

	// si l'objet existe pas, on verifie si existe dans Datastore
	if (mBean == null) {

	    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	    Key clefDatastore = KeyFactory.createKey("Utilisateur", nomUtilisateur);
	    try {
		Entity mEntity = datastore.get(clefDatastore);
		String mEncryptPassword = (String) mEntity.getProperty("motDePasse");
		PasswordEncryption mEncrypt = new PasswordEncryption();
		try {
		    boolean passwordMatch = mEncrypt.validatePassword(motDePasse, mEncryptPassword);
		    if (passwordMatch) {
			// on place dans bean et puis dans session
			mBean = mapUtilisateurFromDatastore(mEntity, mBean);
			req.getSession().setAttribute("utilisateur", mBean);
			return true;
		    } else {
			MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
			mBeanMessageErreur.setErreurFormulaireLogin(LOGIN_PASSWORD_NOT_GOOD);
			req.setAttribute("MessageErreurBeans", mBeanMessageErreur);
			return false;
		    }

		} catch (NoSuchAlgorithmException e) {
		    MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
		    mBeanMessageErreur.setErreurFormulaireLogin(REGISTRATION_ERREUR_PASSWORD_ENCRYPTION);
		    req.setAttribute("MessageErreurBeans", mBeanMessageErreur);
		    return false;
		} catch (InvalidKeySpecException e) {
		    MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
		    mBeanMessageErreur.setErreurFormulaireLogin(REGISTRATION_ERREUR_PASSWORD_ENCRYPTION);
		    req.setAttribute("MessageErreurBeans", mBeanMessageErreur);
		    return false;
		}

	    } catch (EntityNotFoundException e) {
		MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
		mBeanMessageErreur.setErreurFormulaireLogin(LOGIN_USERNAME_DONT_EXIST);
		req.setAttribute("MessageErreurBeans", mBeanMessageErreur);
		return false;
	    }
	    // si le bean existe on verifie si le mot de passe est le bon, si bon on place dans session et retourne true
	} else {
	    String mPasswordEncrypt = mBean.getMotDePasse();
	    PasswordEncryption mEncrypt = new PasswordEncryption();
	    try {
		boolean passwordMatch = mEncrypt.validatePassword(motDePasse, mPasswordEncrypt);
		if (passwordMatch) {
		    // on place les informations du beans utilisateurs dans un objet de session
		    req.getSession().setAttribute("Utilisateur", mBean);
		    return true;
		} else {
		    MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
		    mBeanMessageErreur.setErreurFormulaireLogin(LOGIN_PASSWORD_NOT_GOOD);
		    req.setAttribute("MessageErreurBeans", mBeanMessageErreur);
		    return false;
		}
	    } catch (NoSuchAlgorithmException e) {
		MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
		mBeanMessageErreur.setErreurFormulaireLogin(REGISTRATION_ERREUR_PASSWORD_ENCRYPTION);
		req.setAttribute("MessageErreurBeans", mBeanMessageErreur);
		return false;
	    } catch (InvalidKeySpecException e) {
		MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
		mBeanMessageErreur.setErreurFormulaireLogin(REGISTRATION_ERREUR_PASSWORD_ENCRYPTION);
		req.setAttribute("MessageErreurBeans", mBeanMessageErreur);
		return false;
	    }

	}

    }

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

    public Boolean checkIfValidateAccount(HttpServletRequest req) {
	Utilisateur mBean = (Utilisateur) req.getSession().getAttribute("Utilisateur");
	int validationReussi = mBean.getValidationReussi();
	if (validationReussi == 1) {
	    return true;
	} else {
	    MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
	    mBeanMessageErreur.setErreurCodeValidationType2(VALIDATION_CODE_ERREUR_PAS_ENCORE_FAIT);
	    req.setAttribute("MessageErreurBeans", mBeanMessageErreur);
	    return false;
	}

    }

}
