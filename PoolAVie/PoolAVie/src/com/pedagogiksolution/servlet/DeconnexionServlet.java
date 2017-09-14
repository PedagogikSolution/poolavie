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
import com.pedagogiksolution.datastorebeans.Pool;
import com.pedagogiksolution.datastorebeans.Utilisateur;


public class DeconnexionServlet extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = -6182949997161125660L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	
    
	String nomProperty = null;
	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	Utilisateur mBeanUser = (Utilisateur) req.getSession().getAttribute("Utilisateur");
	
	Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");
	
	if(mBeanPool==null) {
		return;
	}
	String poolID = mBeanPool.getPoolID();
	int teamId = mBeanUser.getTeamId();
	String teamID = String.valueOf(teamId);
	Key mDraftProcessKey = KeyFactory.createKey("DraftProcess", poolID);
	switch(teamID){
	case "1":
	    nomProperty="team1IsOpen";
	    break;
	case "2":
	    nomProperty="team2IsOpen";
	    break;
	case "3":
	    nomProperty="team3IsOpen";
	    break;
	case "4":
	    nomProperty="team4IsOpen";
	    break;
	case "5":
	    nomProperty="team5IsOpen";
	    break;
	case "6":
	    nomProperty="team6IsOpen";
	    break;
	case "7":
	    nomProperty="team7IsOpen";
	    break;
	case "8":
	    nomProperty="team8IsOpen";
	    break;
	case "9":
	    nomProperty="team9IsOpen";
	    break;
	case "10":
	    nomProperty="team10IsOpen";
	    break;
	case "11":
	    nomProperty="team11IsOpen";
	    break;
	case "12":
	    nomProperty="team12IsOpen";
	    break;
	}
	try {
	    Entity entity = datastore.get(mDraftProcessKey);
	    entity.setProperty(nomProperty, 0);
	    datastore.put(entity);
	} catch (EntityNotFoundException e) {
	   
	}
	
	req.getSession().invalidate();
	req.getSession().removeAttribute("Pool");
	resp.sendRedirect("/");
	
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
    }
    
    

}
