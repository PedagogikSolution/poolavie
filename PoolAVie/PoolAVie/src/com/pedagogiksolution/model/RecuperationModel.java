package com.pedagogiksolution.model;

import static com.pedagogiksolution.constants.MessageErreurConstants.RECUPERATION_COURRIEL_INEXISTANT;

import javax.servlet.http.HttpServletRequest;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.pedagogiksolution.beans.MessageErreurBeans;

public class RecuperationModel {

    public Boolean checkIfEmailExist(String courriel, HttpServletRequest req) {

	// on appel le service
	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

	// Use class Query to assemble a query
	Query q = new Query("Utilisateur");
	q.setFilter(new Query.FilterPredicate("courriel", Query.FilterOperator.EQUAL, courriel));

	PreparedQuery pq = datastore.prepare(q);
	Entity mBean = pq.asSingleEntity();
	if (mBean != null) {
	    
	    return true;

	} else {

	    MessageErreurBeans mBeanMessageErreur = new MessageErreurBeans();
	    mBeanMessageErreur.setErreurFormulaireRecuperation(RECUPERATION_COURRIEL_INEXISTANT);
	    req.setAttribute("MessageErreurBeans", mBeanMessageErreur);
	    return false;
	}

    }

    public void sendInfoForRecuperation(HttpServletRequest req) {
	// TODO envoyer un courriel avec le username et le un nouveau mot de passe generer aleatoirement
	// apres avoir inscrit celui-ci dans la Memcache et le Datastore associé a ce compte
	
	

    }

}
