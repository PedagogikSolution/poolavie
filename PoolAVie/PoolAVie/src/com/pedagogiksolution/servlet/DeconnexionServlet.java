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
import com.google.appengine.api.utils.SystemProperty;
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
	
	Utilisateur mBeanUser = (Utilisateur) req.getSession().getAttribute("Utilisateur");
	
	if(mBeanUser==null) {
		req.getRequestDispatcher("jsp/accueil/home.jsp").forward(req, resp);	
	}
	
	
	
	
	Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");
	
	if(mBeanPool==null) {
		req.getRequestDispatcher("jsp/accueil/home.jsp").forward(req, resp);
	}
	
	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
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
	if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
	    resp.sendRedirect("/");
	} else {
		req.getRequestDispatcher("jsp/accueil/home.jsp").forward(req, resp);
	}
	
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
    }
    
    

}
