package com.pedagogiksolution.model;

import static com.pedagogiksolution.constants.MessageErreurConstants.VALIDATION_CODE_ERREUR_PAS_BON;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpServletRequest;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.pedagogiksolution.beans.MessageErreurBeans;
import com.pedagogiksolution.datastorebeans.Utilisateur;
import com.pedagogiksolution.utils.EMF;

public class ValidationModel {

    public Boolean validerCode(String mCode, HttpServletRequest req) {

	Utilisateur mBean = (Utilisateur) req.getSession().getAttribute("Utilisateur");
	
	if(mBean!=null){
	String codeValide = mBean.getCodeValidation();

	if (codeValide.equalsIgnoreCase(mCode)) {
	    // inscrire dans la session les modifications au Bean Utilisateur
	    mBean.setValidationReussi(1);
	    req.setAttribute("Utilisateur", mBean);

	    // modifier la memcache et le datastore
	    modificationStorageUtilisateur(mBean);

	    return true;
	} else {
	    MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
	    mBeanMessageErreur.setErreurCodeValidationType1(VALIDATION_CODE_ERREUR_PAS_BON);
	    req.setAttribute("MessageErreurBeans", mBeanMessageErreur);
	    return false;
	}
	} else {
	    
	    //TODO check dans memCache et dans Datastore au cas ou la session serait flusher par le client
	    return true;	}

    }

    private void modificationStorageUtilisateur(Utilisateur mBean) {
	MemcacheService memcache = MemcacheServiceFactory.getMemcacheService();
	Key clefMemCache = KeyFactory.createKey("Utilisateur", mBean.getNomUtilisateur());
	memcache.put(clefMemCache, mBean);

	EntityManagerFactory emf = EMF.get();
	EntityManager em = null;

	try {
	    em = emf.createEntityManager();
	    em.merge(mBean);
	} finally {
	    if (em != null)
		em.close();
	}

    }

    public void envoyerNouveauCode() {
	// TODO envoyer un nouveau Code, le persister dans memcache et datastore

    }

}
