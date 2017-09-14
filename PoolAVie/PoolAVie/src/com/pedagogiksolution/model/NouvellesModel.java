package com.pedagogiksolution.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.pedagogiksolution.beans.Article;
import com.pedagogiksolution.datastorebeans.Pool;
import com.pedagogiksolution.datastorebeans.Utilisateur;

public class NouvellesModel {

    public void createMessageForNewsByUser(String titre, String body, HttpServletRequest req) {

	Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");
	Utilisateur mBeanUser = (Utilisateur) req.getSession().getAttribute("Utilisateur");
	String writerName = mBeanUser.getTeamName();
	String writerLogo = mBeanUser.getUrlTeamLogo();
	String poolID = mBeanPool.getPoolID();
	
	// en attendant le processus d'upload des images et avatars
	if(writerLogo==null){
	    writerLogo = "https://storage.googleapis.com/poolavie-bucket/QUE.png";
	}

	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

	// Stocke le message poste
	Entity article = new Entity("Articles", KeyFactory.createKey("Nouvelles", poolID));

	article.setProperty("titre", titre);
	article.setProperty("body", body);
	article.setProperty("date", new Date());
	// writer a 0 quand c'est le systeme qui ecrit le message automatise, sinon teamId du writer
	article.setProperty("writerName", writerName);
	article.setProperty("writerLogo", writerLogo);

	datastore.put(article);

    }

    public void createMessageForNewsBySystem(String titre, String body, HttpServletRequest req) {

	Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");
	String writerName = "L'équipe de Pool à vie";
	String writerLogo = "https://storage.googleapis.com/poolavie-bucket/QUE.png";
	String poolID = mBeanPool.getPoolID();

	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

	// Stocke le message poste
	Entity article = new Entity("Articles", KeyFactory.createKey("Nouvelles", poolID));

	article.setProperty("titre", titre);
	article.setProperty("body", body);
	article.setProperty("date", new Date());
	// writer a 0 quand c'est le systeme qui ecrit le message automatise, sinon teamId du writer
	article.setProperty("writerName", writerName);
	article.setProperty("writerLogo", writerLogo);

	datastore.put(article);

    }

    @SuppressWarnings({ })
    public void putNewsInBean(HttpServletRequest req) {

	Article mBeanArticle = new Article();

	Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");
	String poolID = mBeanPool.getPoolID();

	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	// on recupere les nouvelles du pool, on les place dans le bean nouvelle et dans un objet session
	Query q = new Query("Articles").setAncestor(KeyFactory.createKey("Nouvelles", poolID)).addSort("date", SortDirection.DESCENDING);
	List<Entity> results = datastore.prepare(q).asList(FetchOptions.Builder.withLimit(10));

	List<String> titre = new ArrayList<String>();
	List<String> body = new ArrayList<String>();
	List<String> dateCreation = new ArrayList<String>();
	List<String> writerName = new ArrayList<String>();
	List<String> writerLogo = new ArrayList<String>();
	for (Entity result : results) {
	    String titre2= (String) result.getProperty("titre");
	    titre.add(titre2);
	    String body2= (String) result.getProperty("body");
	    body.add(body2);
	    Date dateCreation2= (Date) result.getProperty("date");
	    String dateCreation3 = dateCreation2.toString();
	    dateCreation.add(dateCreation3);
	    String writerName2= (String) result.getProperty("writerName");
	    writerName.add(writerName2);
	    String writerLogo2= (String) result.getProperty("writerLogo");
	    writerLogo.add(writerLogo2);
	}
	mBeanArticle.setTitre(titre);
	mBeanArticle.setBody(body);
	mBeanArticle.setDateCreation(dateCreation);
	mBeanArticle.setWriterName(writerName);
	mBeanArticle.setWriterLogo(writerLogo);
	
	

	req.getSession().setAttribute("Articles", mBeanArticle);

    }

}
