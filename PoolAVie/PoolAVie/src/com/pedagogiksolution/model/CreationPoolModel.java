package com.pedagogiksolution.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpServletRequest;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.pedagogiksolution.datastorebeans.Equipe;
import com.pedagogiksolution.datastorebeans.Pool;
import com.pedagogiksolution.datastorebeans.Utilisateur;
import com.pedagogiksolution.utils.EMF;

public class CreationPoolModel {
    String nomDuPool, nombreEquipe, typeTrade, typeDraft,nomDuTeam;
    String email1, email2, email3, email4, email5, email6, email7, email8, email9, email10, email11;
    int team_id, max_salaire_begin, total_salaire_now, budget_restant, moy_sal_restant_draft, nb_attaquant;
    int nb_defenseur, nb_gardien, nb_rookie, nb_contrat, nb_equipe, manquant_equipe, manquant_att, manquant_def;
    int manquant_gardien, manquant_recrue, bonus_5m, argent_recu, bonus_penalite;

    public Boolean validationFormulaireCreation(HttpServletRequest req) {

	// TODO vérifier si les courriels sont REGEX validate et retourner message d'erreur mais avec informations
	// encore bonne re-afficher

	return true;
    }

    public void createPoolBean(HttpServletRequest req) {

	// initialisation des parametres recu du formulaire
	initParamFromFormulaire(req);

	// on initialise les variables non recu via le formulaire ou pas encore
	int typePool = 1;
	int numTeamCreate = 1;
	int poolYear = 0;

	// on appel le service
	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

	// on recupere le dernier pool_id du datastore of Kind Utilisateur
	int poolID = recuperationPoolId(req, datastore);

	// on recupere la date et place dans un format Date

	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	Date date = new Date();
	String dateCreation = dateFormat.format(date);

	// on crée le beans avec le processus JPA qui va créer le datastore en même temps
	EntityManagerFactory emf = EMF.get();
	EntityManager em = null;
	try {
	    em = emf.createEntityManager();

	    // instanciation du bean Utilisateur
	    Pool mBean = new Pool();
	    mBean.setPoolID(poolID);
	    mBean.setPoolName(nomDuPool);
	    mBean.setCreationDate(dateCreation);
	    mBean.setDraftType(Integer.parseInt(typeDraft));
	    mBean.setTradeType(Integer.parseInt(typeTrade));
	    mBean.setPoolType(typePool);
	    mBean.setNumberTeam(Integer.parseInt(nombreEquipe));
	    mBean.setNumTeamCreate(numTeamCreate);
	    mBean.setPoolYear(poolYear);
	    mBean.setNomTeam1(nomDuTeam);

	    // on place le bean dans un attribut de session
	    req.getSession().setAttribute("Pool", mBean);
	    // on persiste dans le datastore via notre EntityManager
	    em.persist(mBean);
	    // on persist le datastore/bean dans la MemCache
	    MemcacheService memcache = MemcacheServiceFactory.getMemcacheService();
	    Key userPrefsKey = KeyFactory.createKey("Pool", poolID);
	    memcache.put(userPrefsKey, mBean);

	} finally {
	    // on ferme le manager pour libérer la mémoire
	    if (em != null)
		em.close();
	}

    }

    public void createEquipeBean(HttpServletRequest req) {

	// on appel le service
	DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	//
	initEquipeStorage(req);

	// on recupere le dernier pool_id du datastore of Kind Utilisateur
	int poolID = recuperationPoolId(req, datastore);

	nombreEquipe = req.getParameter("nombreEquipe");

	for (int i = 1; i < (Integer.parseInt(nombreEquipe) + 1); i++) {

	    // on crée le beans avec le processus JPA qui va créer le datastore en même temps
	    EntityManagerFactory emf = EMF.get();
	    EntityManager em = null;
	    try {
		em = emf.createEntityManager();

		// instanciation du bean Utilisateur
		Equipe mBean = new Equipe();

		String jspSessionName = "Equipe" + i;
		String datastoreId = poolID + "_" + i;

		mBean.setPoolTeamId(datastoreId);
		mBean.setPoolId(poolID);
		mBean.setTeamId(i);
		mBean.setMax_salaire_begin(max_salaire_begin);
		mBean.setTotal_salaire_now(total_salaire_now);
		mBean.setBudget_restant(budget_restant);
		mBean.setMoy_sal_restant_draft(moy_sal_restant_draft);
		mBean.setNb_attaquant(nb_attaquant);
		mBean.setNb_defenseur(nb_defenseur);
		mBean.setNb_gardien(nb_gardien);
		mBean.setNb_rookie(nb_rookie);
		mBean.setNb_contrat(nb_contrat);
		mBean.setNb_equipe(nb_equipe);
		mBean.setManquant_att(manquant_att);
		mBean.setManquant_def(manquant_def);
		mBean.setManquant_gardien(manquant_gardien);
		mBean.setManquant_recrue(manquant_recrue);
		mBean.setManquant_equipe(manquant_equipe);
		mBean.setArgent_recu(argent_recu);
		mBean.setBonus_5m(bonus_5m);
		mBean.setBonus_penalite(bonus_penalite);

		// on place le bean dans un attribut de session
		req.getSession().setAttribute(jspSessionName, mBean);
		// on persiste dans le datastore via notre EntityManager
		em.persist(mBean);
		// on persist le datastore/bean dans la MemCache
		MemcacheService memcache = MemcacheServiceFactory.getMemcacheService();
		Key userPrefsKey = KeyFactory.createKey("Equipe", datastoreId);
		memcache.put(userPrefsKey, mBean);

	    } finally {

		// on ferme le manager pour libérer la mémoire
		if (em != null) {
		    em.close();

		}
	    }

	}

    }

    public void sendEmail(HttpServletRequest req) {
	initEmailFromFormulaire(req);

    }

    public void createDatabase(HttpServletRequest req) {
	// TODO Auto-generated method stub

    }

    public void createSucceed(HttpServletRequest req) {
	// TODO on ajuste la valeur du Bean Pool firstConnexionFinish pour la valeur 1 dans tous les storages

	// check if session exist and parse,modify and persist if yes
	Utilisateur mBean = new Utilisateur();
	mBean = (Utilisateur) req.getSession().getAttribute("Utilisateur");

	if (mBean != null) {
	    // bean
	    mBean.setFirstConnexionFinish(1);
	    // session
	    req.getSession().setAttribute("Utilisateur", mBean);

	    // MEmCache
	    String username = mBean.getNomUtilisateur();
	    MemcacheService memcache = MemcacheServiceFactory.getMemcacheService();
	    Key userPrefsKey = KeyFactory.createKey("Utilisateur", username);
	    memcache.put(userPrefsKey, mBean);
	    // Datastore
	    EntityManagerFactory emf = EMF.get();
	    EntityManager em = null;

	    try {
		em = emf.createEntityManager();
		em.merge(mBean);
	    } finally {
		if (em != null)
		    em.close();
	    }

	} else {
	    // TODO need to be connect, return erreur
	}

    }

    private int recuperationPoolId(HttpServletRequest req, DatastoreService datastore) {
	Utilisateur mBean = (Utilisateur) req.getSession().getAttribute("Utilisateur");
	int poolID = mBean.getPoolId();

	// TODO recuperer du Memcache ou Datastore si la session est vide

	return poolID;
    }

    private void initParamFromFormulaire(HttpServletRequest req) {
	nomDuPool = req.getParameter("nomDuPool");
	nombreEquipe = req.getParameter("nombreEquipe");
	typeTrade = req.getParameter("typeTrade");
	typeDraft = req.getParameter("typeDraft");
	nomDuTeam = req.getParameter("nomDuTeam");

    }

    private void initEmailFromFormulaire(HttpServletRequest req) {

	nombreEquipe = req.getParameter("nombreEquipe");
	email1 = req.getParameter("email1");
	email2 = req.getParameter("email2");
	email3 = req.getParameter("email3");
	email4 = req.getParameter("email4");
	email5 = req.getParameter("email5");
	email6 = req.getParameter("email6");
	email7 = req.getParameter("email7");
	if (nombreEquipe.equals("9")) {
	    email8 = req.getParameter("email8");
	}
	if (nombreEquipe.equals("10")) {
	    email9 = req.getParameter("email9");
	}
	if (nombreEquipe.equals("11")) {
	    email10 = req.getParameter("email10");
	}
	if (nombreEquipe.equals("12")) {
	    email11 = req.getParameter("email11");
	}

    }

    private void initEquipeStorage(HttpServletRequest req) {

	max_salaire_begin = 52000000;
	total_salaire_now = 0;
	budget_restant = 52000000;
	moy_sal_restant_draft = 0;
	nb_attaquant = 0;
	nb_defenseur = 0;
	nb_gardien = 0;
	nb_rookie = 0;
	nb_contrat = 0;
	nb_equipe = 0;
	manquant_equipe = 23;
	manquant_att = 8;
	manquant_def = 5;
	manquant_gardien = 2;
	manquant_recrue = 8;
	bonus_5m = 0;
	argent_recu = 0;
	bonus_penalite = 0;

    }

}
