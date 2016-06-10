package com.pedagogiksolution.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pedagogiksolution.dao.DAOFactory;
import com.pedagogiksolution.dao.DraftDao;
import com.pedagogiksolution.model.AdminModel;
import com.pedagogiksolution.model.NouvellesModel;

public class AdminDraftServlet extends HttpServlet {
    /**
     * 
     */
    private static final long serialVersionUID = 54388717965389157L;
    
    public static final String CONF_DAO_FACTORY = "daofactory";
    private DraftDao draftDao;

    public void init() throws ServletException {
	/* Récupération d'une instance de notre DAO Utilisateur */
	this.draftDao = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY)).getDraftDao();

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	req.getRequestDispatcher("jsp/admin/adminDraft.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	AdminModel mAdminModel = new AdminModel();

	int numeroFormulaire = Integer.parseInt(req.getParameter("numeroFormulaire"));

	// on set la date du draft
	String dateDraft = req.getParameter("dateDraft");
	String heureDraft = req.getParameter("heureDraft");
	switch (numeroFormulaire) {

	case 1:
	    AdminModel mAdminModel2 = new AdminModel(draftDao);
	    mAdminModel2.setDraftTime(dateDraft, heureDraft, req);

	    // on écrit une news de la part du system pour annoncer la date
	    NouvellesModel mNewsModel = new NouvellesModel();
	    String titre = "Date du Draft";
	    String body = "La date de notre draft annuel aura lieu le " + dateDraft + " à partir de " + heureDraft + ". La section Draft est maintenant accessible avec l'ensemble des informations utiles";
	    mNewsModel.createMessageForNewsBySystem(titre, body, req);

	    // on détermine l'ordre de draft au hasard si Pool de premiere année
	    mAdminModel2.determineOrderOfDraft(req);
	    // on envoie un courriel pour avertir les joueur avec top 10 pick

	    req.getRequestDispatcher("jsp/admin/adminDraft.jsp").forward(req, resp);

	    break;

	case 2:
	   
	    mAdminModel.setDraftTime(dateDraft, heureDraft, req);

	    // on écrit une news de la part du system pour annoncer la date
	    NouvellesModel mNewsModel2 = new NouvellesModel();
	    String titre2 = "Date du Draft";
	    String body2 = "La date de notre draft annuel aura lieu le " + dateDraft + " à partir de " + heureDraft + ". La section Draft est maintenant accessible avec l'ensemble des informations utiles";
	    mNewsModel2.createMessageForNewsBySystem(titre2, body2, req);
	    
	 
	    
	 // on envoie un courriel pour avertir les joueur avec top 10 pick

	    req.getRequestDispatcher("jsp/admin/adminDraft.jsp").forward(req, resp);

	    break;
	    
	case 3:
	    mAdminModel.setDraftTime(dateDraft, heureDraft, req);

	    // on écrit une news de la part du system pour annoncer la date
	    NouvellesModel mNewsModel3 = new NouvellesModel();
	    String titre3 = "Changement de la Date du Draft";
	    String body3 = "La date de notre draft annuel aura finalement lieu le " + dateDraft + " à partir de " + heureDraft + ". La section Draft est maintenant accessible avec l'ensemble des informations utiles";
	    mNewsModel3.createMessageForNewsBySystem(titre3, body3, req);
	    
	    
	
	 // on envoie un courriel pour avertir les joueur avec top 10 pick

	    req.getRequestDispatcher("jsp/admin/adminDraft.jsp").forward(req, resp);
	    break;
	default: //
	    break;

	}

    }

}
