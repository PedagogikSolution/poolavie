package com.pedagogiksolution.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class BetaServlet extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 6893458482713032916L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	Key datastoreKey = KeyFactory.createKey("Visiteur", "nbVisite");
	Long nbVisiteur = null;
	Entity entity = null;
	try {
	    entity = datastore.get(datastoreKey);
	    nbVisiteur = (Long) entity.getProperty("nbVisite");
	    nbVisiteur = nbVisiteur + 1;
	    entity.setProperty("nbVisite", nbVisiteur);
	    datastore.put(entity);
	} catch (EntityNotFoundException e) {
	 // Crée l'entité de type (kind) "Personnage"
	    Entity visiteur = new Entity("Visiteur","nbVisite");

	    // Assigne des propriétés à l'entité
	    visiteur.setProperty("nbVisite", 1);
	    

	    // Enregistre l'entité dans le Datastore
	    datastore.put(visiteur);
	}

	req.getRequestDispatcher("/jsp/beta/beta_info.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

}