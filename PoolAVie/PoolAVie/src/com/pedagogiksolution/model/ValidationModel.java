package com.pedagogiksolution.model;

import static com.pedagogiksolution.constants.MessageErreurConstants.VALIDATION_CODE_ERREUR_PAS_BON;


import javax.servlet.http.HttpServletRequest;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.pedagogiksolution.beans.MessageErreurBeans;
import com.pedagogiksolution.datastorebeans.Utilisateur;

public class ValidationModel {

    public Boolean validerCode(String mCode, HttpServletRequest req) {
	// recuperation des informations du Bean utilisateur
	Utilisateur mBean = (Utilisateur) req.getSession().getAttribute("Utilisateur");
	
	// verification de la valeur du Code entrer par le client (si null, mettre 1 pour generer un message d'erreur code pas Bon
	if(mCode==null||mCode==""){
	    mCode="1";
	}
	

	if (mBean != null) {
	    String codeValide = mBean.getCodeValidation();

	    if (codeValide.equalsIgnoreCase(mCode)) {
		// inscrire dans la session les modifications au Bean Utilisateur
		mBean.setValidationReussi(1);
		mBean.setLoginReussi(1);
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

	    // TODO check dans memCache et dans Datastore au cas ou la session serait flusher par le client
	    return true;
	}

    }

    private void modificationStorageUtilisateur(Utilisateur mBean) {
	

    	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    	
    	Entity mEntity = mBean.mapBeanToEntityForDatastore(mBean,mBean.getNomUtilisateur());
    	
    	datastore.put(mEntity);
    }

    public void envoyerNouveauCode() {
	// TODO envoyer un nouveau Code, le persister dans memcache et datastore

    }

    public String changementDeCourriel() {
	// TODO Auto-generated method stub
	return null;
    }

}
