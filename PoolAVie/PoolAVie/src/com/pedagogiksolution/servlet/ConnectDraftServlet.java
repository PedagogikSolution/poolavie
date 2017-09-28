package com.pedagogiksolution.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.pedagogiksolution.datastorebeans.Utilisateur;

public class ConnectDraftServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 54388717965389157L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		Utilisateur mBean = (Utilisateur) req.getSession().getAttribute("Utilisateur");
		int poolID = mBean.getPoolId();
		int team_id = mBean.getTeamId();
		 String name = poolID+"_"+team_id;
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		Key mKey = KeyFactory.createKey("ConnectDraftOnline", name);
		
		Entity mEntity = new Entity(mKey);
		
		mEntity.setProperty("connect", 1);
		
		datastore.put(mEntity);
		
	}

}
