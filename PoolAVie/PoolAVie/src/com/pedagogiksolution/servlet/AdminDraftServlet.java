package com.pedagogiksolution.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pedagogiksolution.dao.DAOFactory;
import com.pedagogiksolution.dao.DraftDao;
import com.pedagogiksolution.dao.PlayersDao;
import com.pedagogiksolution.datastorebeans.Pool;
import com.pedagogiksolution.model.AdminModel;
import com.pedagogiksolution.model.DraftPlayersModel;
import com.pedagogiksolution.model.NouvellesModel;

public class AdminDraftServlet extends HttpServlet {
    /**
     * 
     */
    private static final long serialVersionUID = 54388717965389157L;
    
    public static final String CONF_DAO_FACTORY = "daofactory";
    private DraftDao draftDao;
    private PlayersDao playersDao;

    @Override
    public void init() throws ServletException {
	/* Recuperation d'une instance de notre DAO Utilisateur */
	this.draftDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getDraftDao();
	this.playersDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getPlayersDao();

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");
	int cycleAnnuel = mBeanPool.getCycleAnnuel();
	if (cycleAnnuel == 3) {
	   	    
	    DraftPlayersModel mModelDraft = new DraftPlayersModel();	    	   
	    mModelDraft.putDatastoreIntoBean(mBeanPool,req);
	}
	req.getRequestDispatcher("jsp/admin/adminDraft.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	AdminModel mAdminModel = new AdminModel(draftDao);

	int numeroFormulaire = Integer.parseInt(req.getParameter("numeroFormulaire"));

	// on set la date du draft
	String dateDraft = req.getParameter("dateDraft");
	String heureDraft = req.getParameter("heureDraft");
	switch (numeroFormulaire) {

	case 1:
	    
	    mAdminModel.setDraftTime(dateDraft, heureDraft, req);

	    // on ecrit une news de la part du system pour annoncer la date
	    NouvellesModel mNewsModel = new NouvellesModel();
	    String titre = "Date du Draft";
	    String body = "La date de notre draft annuel aura lieu le " + dateDraft + " à partir de " + heureDraft + ". La section Draft est maintenant accessible avec l'ensemble des informations utiles";
	    mNewsModel.createMessageForNewsBySystem(titre, body, req);

	    // on determine l'ordre de draft au hasard si Pool de premiere annee
	    mAdminModel.determineOrderOfDraft(req);
	    // on envoie un courriel pour avertir les joueur avec top 10 pick
	    mAdminModel.envoieCourrielDateEtOrdreDeDraft(req,dateDraft,heureDraft);

	    req.getRequestDispatcher("jsp/main/nouvelles.jsp").forward(req, resp);

	    break;

	case 2:
	   
	    mAdminModel.setDraftTime(dateDraft, heureDraft, req);

	    // on ecrit une news de la part du system pour annoncer la date
	    NouvellesModel mNewsModel2 = new NouvellesModel();
	    String titre2 = "Date du Draft";
	    String body2 = "La date de notre draft annuel aura lieu le " + dateDraft + " à partir de " + heureDraft + ". La section Draft est maintenant accessible avec l'ensemble des informations utiles";
	    mNewsModel2.createMessageForNewsBySystem(titre2, body2, req);
	    
	    
	    
	 // on envoie un courriel pour avertir les joueur avec top 10 pick
	    mAdminModel.envoieCourrielDateEtOrdreDeDraft(req,dateDraft,heureDraft);
	    req.getRequestDispatcher("jsp/main/nouvelles.jsp").forward(req, resp);

	    break;
	    
	case 3:
	    mAdminModel.setDraftTime(dateDraft, heureDraft, req);
	    mAdminModel.annulationStartDraft(req);

	    // on ecrit une news de la part du system pour annoncer la date
	    NouvellesModel mNewsModel3 = new NouvellesModel();
	    String titre3 = "Changement de la Date du Draft";
	    String body3 = "La date de notre draft annuel aura finalement lieu le " + dateDraft + " à partir de " + heureDraft + ". La section Draft est maintenant accessible avec l'ensemble des informations utiles";
	    mNewsModel3.createMessageForNewsBySystem(titre3, body3, req);
	    
	 
	
	 // on envoie un courriel pour avertir les joueur avec top 10 pick
	    mAdminModel.envoieCourrielDateEtOrdreDeDraft(req,dateDraft,heureDraft);
	    req.getRequestDispatcher("jsp/main/nouvelles.jsp").forward(req, resp);
	    break;
	    
	case 4:
	    mAdminModel.setDraftTime(dateDraft, heureDraft, req);

	    // on ecrit une news de la part du system pour annoncer la date
	    NouvellesModel mNewsModel4 = new NouvellesModel();
	    String titre4 = "Changement de la Date du Draft";
	    String body4 = "La date de notre draft annuel aura finalement lieu le " + dateDraft + " à partir de " + heureDraft + ". La section Draft est maintenant accessible avec l'ensemble des informations utiles";
	    mNewsModel4.createMessageForNewsBySystem(titre4, body4, req);
	    
	 // on remet le cycle a 2
	    mAdminModel.annulationStartDraft(req);
	
	 // on envoie un courriel pour avertir les joueur avec top 10 pick
	    mAdminModel.envoieCourrielDateEtOrdreDeDraft(req,dateDraft,heureDraft);
	    req.getRequestDispatcher("jsp/main/nouvelles.jsp").forward(req, resp);
	    break;
	    
	case 5:
		
		Pool mBeanPool = (Pool) req.getSession().getAttribute("Pool");
		String poolID = mBeanPool.getPoolID();
	    String nom = req.getParameter("nom");
	    String team = req.getParameter("team");
	    String position = req.getParameter("position");
	    String birthday = req.getParameter("birthday");
	    
	    mAdminModel.addPlayerDuringDraft(poolID,nom,team,position,birthday,playersDao,req);
	    
	    req.getRequestDispatcher("jsp/main/nouvelles.jsp").forward(req, resp);
	    break;
	default: //
	    break;

	}

    }

}
